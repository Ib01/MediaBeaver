package com.ibus.mediabeaver.core.filesystem;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.entity.UserConfiguration;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.util.EventLogger;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.MovieService;
import com.ibus.mediabeaver.core.util.Platform;
import com.ibus.mediabeaver.core.util.Services;
import com.ibus.mediabeaver.core.util.TvService;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesLoginException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

public class MediaMover 
{	
	Logger log = Logger.getLogger(MediaMover.class.getName());
	MoviePathParser movieParser; 
	int foundMedia = 0;
	int processedMedia = 0;
	EventLogger eventLogger = Factory.getEventLogger();
	TvService tvService;
	MovieService movieService;
	
	public MediaMover(TvService tvService, MovieService movieService)
	{
		this.tvService = tvService;
		this.movieService = movieService;
	}	
	
	/**
	 * processes all files in source directory and in all its sub directories
	 * @param config
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	public void moveFiles(String directory)  //config.getSourceDirectory()
	{	
		beforeProcess();
		processFileTree(new File(directory));
		afterProcess();
	}
	
	
	private void processFileTree(File directory)  
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());
		
		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				processFileTree(fso); 
			} 
			else
			{
				processFile(fso);
			}
		}
	}
	/**
	 * process a flat list of files.  directories will not be processed
	 * @param paths
	 * @throws XmlRpcException 
	 * @throws IOException 
	 */
	public void moveFiles(List<String> paths) 
	{ 		
		beforeProcess();
				
		for(String path : paths)
		{
			File file = new File(path);
			
			if(!file.isDirectory())
			{
				try 
				{
					processFile(file);
				} 
				catch (OpenSubtitlesLoginException e) 
				{
					logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
							"Failed to login to the Open Subtitles web service while attempting to move a file. Further file movements aborted");
					log.error(String.format("Failed to login to the Open Subtitles web service while attempting to move %s. Further file movements aborted", 
							file.getAbsolutePath()), e);
					afterProcess();
					break;
				}
			}
		}
		
		afterProcess();
	}
	
	
	/**
	 * Move a file.  Directories, files with unrecognised media extensions, and files not recognised by the media 
	 * services used will not be moved
	 * @param paths
	 * @throws XmlRpcException 
	 * @throws IOException 
	 */
	public boolean moveFile(String path) 
	{ 	
		*************here ********************
		
		beforeProcess();
		boolean moved = false;
		
		File file = new File(path);
		if(!file.isDirectory())
		{
			moved = processFile(file);
			afterProcess(1,1);
			return true;
		}
	
		afterProcess(1,1);
		return moved;
	}
	
	
	//------------------------------------------------------------------------------------------------------------//

	protected void beforeProcess() 
	{
		log.debug("******************************************************");
		log.debug("Commencing Movement of files");
	}

	
	protected void afterProcess(int found, int moved) 
	{
		log.debug("");
		log.debug("File movement is complete");
		log.debug(String.format("Media found= %d", found));
		log.debug(String.format("Media moved= %d", moved));
		log.debug("******************************************************");
	}
	
	protected boolean processFile(File file)  
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(Factory.getUserConfiguration().isVideoExtension(extension))
		{
			return moveVideo(file);
		}
		
		eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
				"Could not move file.  The file is not a valid media type");
		log.debug(String.format(
				"Could not move %s.  The file is not a valid media type", 
				file.getAbsolutePath()));
		
		return false;
	}
	
	protected boolean moveVideo(File file) throws OpenSubtitlesLoginException 
	{
		//get title for file hash from the open subtitles service
		Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
		if(ostTitle != null)
		{
			//let the OST Service identify what kind of file we have 
			if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series"))
			{
				return moveTvEpisode(ostTitle, file);
			}
			else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
			{
				return moveMovie(ostTitle, file);
			}
		}
		
		eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
				"Could not move file.  could not identify the type of video the file represents using the open subtitles service");
		log.debug(String.format(
				"Could not move file %s. Could not identify the type of video the file represents using the open subtitles service", 
				file.getAbsolutePath()));
		
		return false;
	}
	
	
	protected boolean moveMovie(Map<String,String> ostTitle, File file) 
	{
		String fullDestinationPath ;
		try
		{
			String pathEnd = movieService.getMoviePath(ostTitle, file);
			if(pathEnd != null)
			{
				fullDestinationPath = Paths.get(Factory.getUserConfiguration().getMovieRootDirectory(), pathEnd).toString();
				log.debug(String.format("Destination path generated %s.", fullDestinationPath));
							
				moveFile(file.getAbsolutePath(), Factory.getUserConfiguration().getMovieRootDirectory(), pathEnd);
				eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
				log.debug(String.format("File %s was successfully moved to %s", file.getAbsolutePath(), fullDestinationPath));
				
				return true;
			}
		
		} catch (IOException e)
		{
			log.error(String.format("An unspecified error occured while moving the file %s", file.getAbsolutePath()), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
		} 
		catch (DuplicateFileException e)
		{
			log.error(String.format("An error occured moving file: %s. Destination file %s already exists or part of the path exists with different casing", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Destination file already exists or part of the path exists with different casing");
		} 
		
		return false;
	}
	
	
	
	protected boolean moveTvEpisode(Map<String,String> ostTitle, File file)
	{
		String fullDestinationPath;
		try
		{
			String pathEnd = tvService.getEpisodePath(ostTitle, file);
			if(pathEnd != null)
			{
				fullDestinationPath = Paths.get(Factory.getUserConfiguration().getTvRootDirectory(), pathEnd).toString();
				log.debug(String.format("Destination path generated %s", fullDestinationPath));
				
				moveFile(file.getAbsolutePath(), Factory.getUserConfiguration().getTvRootDirectory(), pathEnd);
				
				eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
				log.debug(String.format("File %s was successfully moved to %s", file.getAbsolutePath(), fullDestinationPath));
				
				return true;
			}
		} 
		catch (IOException e)
		{
			log.error(String.format("An unspecified error occured while moving the file %s", file.getAbsolutePath()), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
			
		} 
		catch (DuplicateFileException e)
		{
			log.error(String.format("An error occured moving file: %s. Destination file %s already exists or part of the path exists with different casing", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Destination file already exists or part of the path exists with different casing");
		} 
		
		return false;
	}
	
	
	/**
	 * get title from open subtitles using a thumbprint from the file
	 * @param file
	 * @return
	 * @throws OpenSubtitlesException 
	 * @throws OpenSubtitlesLoginException 
	 */
	private Map<String,String> getOpenSubtitlesTitle(File file) throws OpenSubtitlesLoginException 
	{
		//get media information from Open Subtitles Service using file thumbprint 
		OpenSubtitlesHashData fileThumbprint;
		try
		{
			fileThumbprint = OpenSubtitlesHashGenerator.computeHash(file);
			
			if(!fileThumbprint.isValid())
			{
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not generate thumbprint for file");
				log.debug(String.format("File %s could not be moved. thumbprint for file is invalid", file.getAbsolutePath()));
				return null;
			}
		} 
		catch (IOException e)
		{
			eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not generate thumbprint for file");
			log.error(String.format("File %s could not be moved. could not acquire a thumbprint for the file", file.getAbsolutePath()), e);
			return null;
		}
		
		
		OstTitleDto dto = null;
		try
		{
			dto = Factory.getOpenSubtitlesClient().getTitleForHash(fileThumbprint);
			
			if(dto == null || dto.getPossibleTitles().size() == 0)
			{
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not find title for thumbprint");
				log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
						file.getAbsolutePath()));
				return null;
			}
			
			return dto.getFirstMovieOrEpisodeTitleWithImdb();
		} 
		catch (OpenSubtitlesResponseException e) 
		{
			eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
					"While attempting to perform a thumbprint search against the file the Open Subtitles service returned an error in its response");
			
			log.error(String.format("While attempting to perform a thumbprint search against %s the Open Subtitles service returned an error in its response", 
					file.getAbsolutePath()), e);
			return null;
		}
		catch (OpenSubtitlesException e) 
		{
			eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "An unknown error occured while performing a thumbprint search against the Open Subtitles web service. See system logs for details");
			log.error(String.format("Open Subtitles thumbprint search on file %s failed. An unknown error occured while performing a thumbprint search against the service", 
					file.getAbsolutePath()), e);
			return null;
		}
		
	}
	

	private void moveFile(String source, String destinationRoot, String destinationEnd) throws IOException, DuplicateFileException
	{
		if(Factory.getUserConfiguration().isCopyAsDefault())
			FileSystem.copyFile(source, destinationRoot, destinationEnd);
		else
			FileSystem.moveFile(source, destinationRoot, destinationEnd);
	}
	
	
}




















