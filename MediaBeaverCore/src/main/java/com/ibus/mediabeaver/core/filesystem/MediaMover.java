package com.ibus.mediabeaver.core.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.MetadataException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.util.EventLogger;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.FileSysUtil;
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
		boolean moveSuccess = false;
		log.debug("<<< Commencing attempt to move file >>>");
		
		String extension = FileSysUtil.getExtension(file.getAbsolutePath());
		if(FileSysUtil.isVideoExtension(extension, config.getVideoExtensionFilter()))
		{
			++foundMedia;
			moveSuccess = moveVideo(file);
		}
		else
		{
			//TODO: dont report attempts on non media files???
			/*eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), null, ResultType.Failed, 
					"Could not move file.  The file is not a valid media type");*/
			log.debug(String.format("Could not move %s.  The file is not a valid media type", file.getAbsolutePath()));
		}
		
		log.debug("<<< Attempt to move file complete >>>");
		return moveSuccess;
	}
	
	protected boolean moveVideo(File file) 
	{
		//get title for file hash from the open subtitles service
		Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
		if(ostTitle != null)
		{
			String mediaType = ostTitle.get(OpenSubtitlesField.MovieKind.toString());
			if(mediaType != null)
			{
				//let the OST Service identify what kind of file we have 
				if(mediaType.equals("episode") || mediaType.equals("tv series"))
				{
					return moveTvEpisode(ostTitle, file);
				}
				else if(mediaType.equals("movie"))
				{
					return moveMovie(ostTitle, file);
				}
				else
				{
					log.debug(String.format("Could not move %s. The Open Subtitles service returned an unknown media type in its MovieKind field", file.getAbsolutePath()));
				}
			}
			else
			{
				log.debug(String.format("Could not move %s. The Open Subtitles service returned null in its MovieKind field", file.getAbsolutePath()));
			}
			
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), null, ResultType.Failed, "Could not determine media type for file");
		}
		
		//errors logged in getOpenSubtitlesTitle
		return false;
	}
	
	
	protected boolean moveMovie(Map<String,String> ostTitle, File file) 
	{
		String fullDestinationPath = null;
		try
		{
			String pathEnd = movieService.getMoviePath(ostTitle, file);
		
			fullDestinationPath = Paths.get(config.getMovieRootDirectory(), pathEnd).toString();
			log.debug(String.format("Destination path generated %s.", fullDestinationPath));
						
			moveFile(file.getAbsolutePath(), config.getMovieRootDirectory(), pathEnd);
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
			log.debug(String.format("File %s was successfully moved to %s", file.getAbsolutePath(), fullDestinationPath));
			
			return true;
		
		} catch (IOException e)
		{
			log.error(String.format("Could not move %s. An unspecified error occured while moving the file", file.getAbsolutePath()), e);	
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
		} 
		catch (DuplicateFileException e)
		{
			log.error(String.format("Could not move %s. Destination file already exists or part of the path exists with different casing", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Destination file already exists");
		} catch (MetadataException e) 
		{
			log.error(String.format("Could not move %s. Metadata required to name and move the file could not be acquired from the media services used", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Media services responded with missing metadata");
		} catch (PathParseException e) 
		{
			log.error(String.format("Could not move %s. Could not parse service metadata to a valid path.  It is likely that the media format path used is malfomred", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Unable to parse media format path");
		} 
		
		return false;
	}
	
	
	
	protected boolean moveTvEpisode(Map<String,String> ostTitle, File file)
	{
		String fullDestinationPath = null;
		try
		{
			String pathEnd = tvService.getEpisodePath(ostTitle, file);
			
			fullDestinationPath = Paths.get(config.getTvRootDirectory(), pathEnd).toString();
			log.debug(String.format("Destination path generated %s", fullDestinationPath));
			
			moveFile(file.getAbsolutePath(), config.getTvRootDirectory(), pathEnd);
			
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
			log.debug(String.format("File %s was successfully moved to %s", file.getAbsolutePath(), fullDestinationPath));
			
			return true;
			
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
		OpenSubtitlesHashData fileThumbprint = getOpenSubtitlesHash(file);
		return getOpenSubtitlesDto(file, fileThumbprint);
	}
	
	private Map<String,String> getOpenSubtitlesDto(File file, OpenSubtitlesHashData fileThumbprint)
	{
		if(fileThumbprint == null)
			return null;
		
		try
		{
			OstTitleDto dto = Factory.getOpenSubtitlesClient().getTitleForHash(fileThumbprint);
			
			if(dto != null || dto.getPossibleTitles().size() != 0)
			{
				//TODO: Can we do better than this. eg could we get the highest rated items
				return dto.getFirstMovieOrEpisodeTitleWithImdb();
			}
			
			log.debug(String.format("Could not move %s. A thumbprint search was performed against the Open Subtitles service and the service did not return any results.", 
					file.getAbsolutePath()));

		} 
		catch (OpenSubtitlesResponseException e) 
		{
			log.error(String.format("Could not move %s. A thumbprint search was performed against the Open Subtitles service and an error was returned in the response", 
					file.getAbsolutePath()), e);
		}
		catch (OpenSubtitlesException e) 
		{
			log.error(String.format("Could not move %s. An unknown error occured while attempting to perform a thumbprint search against the Open Subtitles service", 
					file.getAbsolutePath()), e);
		} 
		catch (OpenSubtitlesLoginException e) 
		{
			log.error(String.format("Could not move %s. Could not login to the Opensubtitles service.", 
					file.getAbsolutePath()), e);
		}
		
		eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), null, ResultType.Failed, "Could not find metadata for file");
		return null;
	}
	
	private OpenSubtitlesHashData getOpenSubtitlesHash(File file)
	{
		try
		{
			OpenSubtitlesHashData fileThumbprint = OpenSubtitlesHashGenerator.computeHash(file);
			if(fileThumbprint.isValid())
				return fileThumbprint;
			
			log.error(String.format("Could not move %s. The thumbprint generated from the file is not valid", file.getAbsolutePath()));	
		} 
		catch (IOException e)
		{
			log.error(String.format("Could not move %s. An IO Exception occured while generating a thumbprint for the file", file.getAbsolutePath()), e);
		}
		
		eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), ResultType.Failed, "Could not generate thumbprint for file");
		return null;
	}
	

	private void moveFile(String source, String destinationRoot, String destinationEnd) throws IOException, DuplicateFileException
	{
		if(config.isCopyAsDefault())
			FileSystem.copyFile(source, destinationRoot, destinationEnd);
		else
			FileSystem.moveFile(source, destinationRoot, destinationEnd);
	}
	
	
}




















