package com.ibus.mediabeaver.core.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.util.EventLogger;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.MovieService;
import com.ibus.mediabeaver.core.util.TvService;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesLoginException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;

public class MediaMover 
{	
	Logger log = Logger.getLogger(MediaMover.class.getName());
	MoviePathParser movieParser; 
	int foundMedia = 0;
	int processedMedia = 0;
	EventLogger eventLogger;
	TvService tvService;
	MovieService movieService;
	Configuration config;
	
	public MediaMover(EventLogger eventLogger, Configuration config, TvService tvService, MovieService movieService)
	{
		this.tvService = tvService;
		this.movieService = movieService;
		this.eventLogger =eventLogger;
		this.config = config;
	}	
	
	/**
	 * processes all media files in source directory and in all its sub directories. returns true if all found 
	 * media files were moved
	 * @param config
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	public boolean moveFiles(String directory)  //config.getSourceDirectory()
	{	
		beforeProcess();
		boolean allMoved = processFileTree(new File(directory));
		afterProcess();
		
		return allMoved;
	}
	
	
	private boolean processFileTree(File directory)  
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
				if(processFile(fso))
					++processedMedia;
			}
		}
		
		return processedMedia == foundMedia;
	}
	/**
	 * process a flat list of files.  Directories, files with unrecognised media extensions, and files not recognised by the media 
	 * services used will not be moved. returns true if all the media files found were moved.
	 * @param paths
	 * @throws XmlRpcException 
	 * @throws IOException 
	 */
	public boolean moveFiles(List<String> paths) 
	{ 		
		beforeProcess();
				
		for(String path : paths)
		{
			File file = new File(path);
			if(!file.isDirectory())
			{
				if(processFile(file))
					++processedMedia;
			}
		}
		
		afterProcess();
		return processedMedia == foundMedia;
	}
	
	
	/**
	 * Move a file.  Directories, files with unrecognised media extensions, and files not recognised by the media 
	 * services used will not be moved. returns true if the file was moved.
	 * @param paths
	 */
	public boolean moveFile(String path) 
	{ 	
		beforeProcess();
		
		File file = new File(path);
		if(!file.isDirectory())
		{
			if(processFile(file))
				++processedMedia;
		}
	
		afterProcess();
		return processedMedia == 1;
	}
	
	
	//------------------------------------------------------------------------------------------------------------//

	protected void beforeProcess() 
	{
		foundMedia = 0;
		processedMedia = 0;
		log.debug("Commencing Movement of files");
	}

	
	protected void afterProcess() 
	{
		log.debug("");
		log.debug("******************************************************");
		log.debug("File movement is complete");
		log.debug(String.format("Media found= %d", foundMedia));
		log.debug(String.format("Media moved= %d", processedMedia));
		log.debug("******************************************************");
	}
	
	protected boolean processFile(File file)  
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(config.isVideoExtension(extension))
		{
			++foundMedia;
			return moveVideo(file);
		}
		
		eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
				"Could not move file.  The file is not a valid media type");
		log.debug(String.format(
				"Could not move %s.  The file is not a valid media type", 
				file.getAbsolutePath()));
		
		return false;
	}
	
	protected boolean moveVideo(File file) 
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
		String fullDestinationPath = null;
		try
		{
			String pathEnd = movieService.getMoviePath(ostTitle, file);
			if(pathEnd != null)
			{
				fullDestinationPath = Paths.get(config.getMovieRootDirectory(), pathEnd).toString();
				log.debug(String.format("Destination path generated %s.", fullDestinationPath));
							
				moveFile(file.getAbsolutePath(), config.getMovieRootDirectory(), pathEnd);
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
		String fullDestinationPath = null;
		try
		{
			String pathEnd = tvService.getEpisodePath(ostTitle, file);
			if(pathEnd != null)
			{
				fullDestinationPath = Paths.get(config.getTvRootDirectory(), pathEnd).toString();
				log.debug(String.format("Destination path generated %s", fullDestinationPath));
				
				moveFile(file.getAbsolutePath(), config.getTvRootDirectory(), pathEnd);
				
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
	private Map<String,String> getOpenSubtitlesTitle(File file) 
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
		catch (OpenSubtitlesLoginException e) 
		{
			eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not login to the Opensubtitles service");
			log.debug(String.format("Open Subtitles thumbprint search on file %s failed. Could not login to the Opensubtitles service.", 
					file.getAbsolutePath()));
			return null;
		}
		
	}
	

	private void moveFile(String source, String destinationRoot, String destinationEnd) throws IOException, DuplicateFileException
	{
		if(config.isCopyAsDefault())
			FileSystem.copyFile(source, destinationRoot, destinationEnd);
		else
			FileSystem.moveFile(source, destinationRoot, destinationEnd);
	}
	
	
}




















