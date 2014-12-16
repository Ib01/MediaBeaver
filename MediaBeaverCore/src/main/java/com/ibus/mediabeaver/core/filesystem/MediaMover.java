package com.ibus.mediabeaver.core.filesystem;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.util.PathParser;
import com.ibus.mediabeaver.core.util.Services;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesLoginException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.domain.wrapper.EpisodesXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesXmlWrapper;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

public class MediaMover 
{	
	Logger log = Logger.getLogger(MediaMover.class.getName());
	Configuration config;	
	Platform platform;
	List<PathToken> episodePathTokens;
	List<PathToken> moviePathTokens;
	List<Activity> unmovedMedia = new ArrayList<Activity>();
	List<Activity> movedMedia = new ArrayList<Activity>();
	boolean processTvShows = true;
	
	public enum Platform{
		Web,
		CLI
	}
	
	public List<Activity> getMovedMedia()
	{
		return movedMedia;
	}

	public List<Activity> getUnmovedMedia()
	{
		return unmovedMedia;
	}
	
	public MediaMover(Platform platform, Configuration config)
	{
		this.config = config;
		this.platform = platform;
	}
	
	
	/**
	 * processes all files in source directory and in all its sub directories
	 * @param config
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	public void moveFiles()  
	{	
		if(!beforeProcess())
			return;
		
		try 
		{
			processFileTree(new File(config.getSourceDirectory()));
		} 
		catch (OpenSubtitlesLoginException e) 
		{
			logEvent(null, null, ResultType.Failed, 
					"Failed to login to the Open Subtitles web service while attempting to move a file. Further file movements aborted");
			log.error("Failed to login to the Open Subtitles web service while attempting to move a file. Further file movements aborted", e);
			afterProcess();
			return;
		}
		
		afterProcess();
	}
	
	private void processFileTree(File directory) throws OpenSubtitlesLoginException 
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
		if(!beforeProcess())
			return;
		
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
	 * Move a file.  directories will not be processed
	 * @param paths
	 * @throws XmlRpcException 
	 * @throws IOException 
	 */
	public boolean moveFile(String path) 
	{ 		
		if(!beforeProcess())
			return false;
		
		File file = new File(path);
		
		if(file.isDirectory())
			return false;
		
		try 
		{
			processFile(file);
			afterProcess();
			return unmovedMedia.size() == 0;
		} 
		catch (OpenSubtitlesLoginException e) 
		{
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
					"Failed to login to the Open Subtitles web service while attempting to move a file. Further file movements aborted");
			log.error(String.format("Failed to login to the Open Subtitles web service while attempting to move %s. Further file movements aborted", 
					file.getAbsolutePath()), e);
			afterProcess();
		}
	
		return false;
	}
	
	
	protected void logEvent(final Activity event)
	{

		if(platform == Platform.Web)
		{
			Repository.saveEntity(event);
			return;
		}
		
		//otherwise we are calling from the cli
		Repository.doInTransaction(
			new UpdateTransactable(){
				public void run()
				{
					Repository.saveEntity(event);
				}
			});
	}
	
	
	protected boolean beforeProcess() 
	{
		processTvShows = true;
		unmovedMedia = new ArrayList<Activity>();
		movedMedia = new ArrayList<Activity>();
		
		log.debug("******************************************************");
		log.debug("Commencing Movement of files");
		
		episodePathTokens = PathParser.getTokens(config.getEpisodePath());
		moviePathTokens = PathParser.getTokens(config.getMoviePath());
		return true;
	}

	
	protected void afterProcess() 
	{
		log.debug("");
		log.debug("File movement is complete");
		log.debug(String.format("Media found= %d", movedMedia.size() + unmovedMedia.size()));
		log.debug(String.format("Media moved= %d", movedMedia.size()));
		log.debug("******************************************************");
	}
	
	
	
	protected void processFile(File file) throws OpenSubtitlesLoginException 
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(config.isVideoExtension(extension))
		{
			moveVideo(file);
		}
	}
	
	protected void moveVideo(File file) throws OpenSubtitlesLoginException 
	{
		//get title for file hash from the open subtitles service
		Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
		if(ostTitle == null)
			return;
		
		//let the OST Service identify what kind of file we have 
		if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series"))
		{
			try 
			{
				if(processTvShows)
					moveTvEpisode(ostTitle, file);
			} 
			catch (TvdbException | TvdbConnectionException e) 
			{
				logEvent(null, null, ResultType.Failed, "Processing of Tv shows disscontinued. the TVDB Service appears to be unavailable.");
				log.debug(
						String.format("Aborting movement of %s. Disscontinuing further processing of Tv shows the TVDB Service appears to be unavailable.", 
						file.getAbsolutePath()));
				
				//A fatal communication error occurred communicating with the TVDB Service. disable further processing of TV shows.
				processTvShows = false;
			}
		}
		else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
		{
			moveMovie(ostTitle, file);
		}
		
	}
	
	
	protected void moveMovie(Map<String,String> ostTitle, File file) 
	{
		String fullDestinationPath = "";
		try
		{
			//I am guessing that these are stored as number in the open subtitles db. gop figure.
			String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
			FindResults result = Services.getTmdbClient().getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
			String destinationPathEnd = parseMoviePath(result.getMovieResults().get(0));
			
			destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
			fullDestinationPath = Paths.get(config.getMovieRootDirectory(), destinationPathEnd).toString();
			
			log.debug(String.format("Destination path generated %s.", fullDestinationPath));
						
			moveFile(file.getAbsolutePath(), config.getMovieRootDirectory(), destinationPathEnd);
			
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
			return;
		
		} catch (IOException e)
		{
			log.error(String.format("An unspecified error occured while moving the file %s", file.getAbsolutePath()), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
		} 
		catch (DuplicateFileException e)
		{
			log.error(String.format("An error occured moving file: %s. Destination file %s already exists or part of the path exists with different casing", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Destination file already exists or part of the path exists with different casing");
		} 
		catch (PathParseException e)
		{
			log.error(String.format("An error occured parsing the path format string: %s; while attempting to move the file %s", 
					config.getMoviePath(), file.getAbsolutePath()), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "Could not parse the path format string to a valid path");
		}
	}
	
	
	
	protected void moveTvEpisode(Map<String,String> ostTitle, File file) throws TvdbException, TvdbConnectionException 
	{
		
		String fullDestinationPath = "";
		try
		{
			//Get Series imdb from the OST title. we will use this id to get data from other services 
			String seriesImdbid = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()));
			if(seriesImdbid == null || seriesImdbid.trim().length() == 0)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid series IMDB Id from the Open Subtitles Service.");
				log.debug(String.format("Aborting movement of %s. Failed to get a valid series IMDB Id from open subtitles service.", file.getAbsolutePath()));
				return;
			}
			
			//get season number and episode number
			int seasonNumber;
			int episodeNumber;
			try
			{
				//we need the season and episode number from the OST Service to identify the episode we are after once we have gotten series info from TVDB below.
				//Note: we could stop here and just use data from the OST Service. However the data does not appear to be as accurate or as detailed as other 
				//services offer
				seasonNumber = Integer.parseInt(ostTitle.get(OpenSubtitlesField.SeriesSeason.toString()));
			}
			catch(NumberFormatException ex)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid season number from the Open Subtitles Service.");
				log.debug(String.format("Aborting movement of %s. Failed to get a valid season number from the Open Subtitles Service.", file.getAbsolutePath()));
				return;
			}
			
			try
			{
				episodeNumber = Integer.parseInt(ostTitle.get(OpenSubtitlesField.SeriesEpisode.toString()));
			}
			catch(NumberFormatException ex)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid episode number from the Open Subtitles Service.");
				log.debug(String.format("Aborting movement of %s. Failed to get a valid episode number from the Open Subtitles Service.", file.getAbsolutePath()));
				return;
			}
			
			
			//TvdbSeriesResponseDto only contains very basic info about the series itself.
			Series seriesDto = Services.getTvdbClient().getSeriesForImdbId(seriesImdbid);
			if(seriesDto == null || seriesDto.getId() == null)
			{
				logEvent(file.getAbsolutePath(), null,ResultType.Failed, "Failed to get valid series data from the TVDB service.");
				log.debug(
						String.format("Aborting movement of %s. Failed to get valid series data from the TVDB service.", 
						file.getAbsolutePath()));
				return;
			}
			
			long tvdbSeriesId = seriesDto.getId();
			//TvdbEpisodesResponseDto contains detailed info about not only the series but all its seasons and episodes. pity there isn't a way to 
			//get single episode info for an episode imdb.
			Episode tvdbEpisode = Services.getTvdbClient().getEpisode(tvdbSeriesId, seasonNumber, episodeNumber);
			if(tvdbEpisode == null)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get episode data from the TVDB service.");
				log.debug(
						String.format("Aborting movement of %s. Failed to get episode data from the TVDB service.", 
						file.getAbsolutePath()));
				return;
			}
			
			//we now have the series.  i.e series info, seasons info and info on every episode. but we don't know which episode to get info on 
			//so we need to use season and episode numbers from OST Service 
			log.debug(String.format("Successfully acquired episode data for %s.", file.getAbsolutePath()));
			
			
			String  destinationPathEnd = parseEpisodePath(seriesDto, tvdbEpisode);
			destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
			
			fullDestinationPath = Paths.get(config.getTvRootDirectory(), destinationPathEnd).toString();
			
			log.debug(String.format("Destination path generated %s", fullDestinationPath));
			
			moveFile(file.getAbsolutePath(), config.getTvRootDirectory(), destinationPathEnd);
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
			
			return;
			
		} catch (IOException e)
		{
			log.error(String.format("An unspecified error occured while moving the file %s", file.getAbsolutePath()), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
			
		} 
		catch (DuplicateFileException e)
		{
			log.error(String.format("An error occured moving file: %s. Destination file %s already exists or part of the path exists with different casing", 
					file.getAbsolutePath(), fullDestinationPath), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"Destination file already exists or part of the path exists with different casing");
		} 
		catch (PathParseException e)
		{
			log.error(String.format("An error occured parsing the path format string: %s; while attempting to move the file %s", 
					config.getEpisodePath(), file.getAbsolutePath()), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "Could not parse the path format string to a valid path");
		} 
		
		
	}
	
	
	private void moveFile(String source, String destinationRoot, String destinationEnd) throws IOException, DuplicateFileException
	{
		if(config.isCopyAsDefault())
			FileSystem.copyFile(source, destinationRoot, destinationEnd);
		else
			FileSystem.moveFile(source, destinationRoot, destinationEnd);
	}
	
	
	
	private Activity logEvent(String source, String destination, ResultType result, String errorDescription)
	{
		Activity event = new Activity();
		
		event.setEventTime(new Date());
		event.setEventType(EventType.Move);
		event.setDestinationPath(destination);
		event.setResult(result);
		event.setSourcePath(source);
		event.setErrorDescription(errorDescription);
		
		logEvent(event);
		
		if(result == ResultType.Succeeded)
			movedMedia.add(event);
		else
			unmovedMedia.add(event);
		
		return event;
	}
	
	
	/**
	 * parse tokenised or templated movie path for a Movie. for eg will  
	 * parse: 
	 *  {MovieName}({ReleaseDate})\\{MovieName}({ReleaseDate})
	 *  to: 
	 *  Iron Man (2010)\Iron Man (2010)
	 * @param seriesDto
	 * @param tvdbEpisode
	 * @return
	 * @throws PathParseException
	 */
	private String parseMoviePath(MovieDb movie) throws PathParseException
	{
		String rawMoviePath =  config.getMoviePath(); //path with tokens in it
		
		for(PathToken token : moviePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("MovieName"))
			{
				parsedToken = PathParser.parseToken(token, movie.getTitle());
			}
			else if(token.getName().equals("ReleaseDate"))
			{
				parsedToken = PathParser.parseToken(token, movie.getReleaseDate());
			}
			
			rawMoviePath = PathParser.parsePath(parsedToken, rawMoviePath);
		}
		
		if(PathParser.containsTokens(rawMoviePath))
			throw new PathParseException(String.format("Episode path is malformed. Path contains tokens after being parsed: %s", rawMoviePath));
		
		return rawMoviePath;
	}

	
	/**
	 * parse tokenised or templated episode path for an episode. for eg will  
	 * parse: 
	 *  \{SeriesName}\Season {SeasonNumber}\{SeriesName} S{SeasonNumber}.leftPad("2","0")E{EpisodeNumber}.leftPad("2","0")"
	 *  to: 
	 *  Game of Thrones\Season 1\Game of Thrones S01E01.avi
	 * @param seriesDto
	 * @param tvdbEpisode
	 * @return
	 * @throws PathParseException
	 */
	private String parseEpisodePath(Series seriesDto, Episode tvdbEpisode) throws PathParseException
	{
		String rawEpisodePath =  config.getEpisodePath(); //path with tokens in it
		
		for(PathToken token : episodePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("SeriesName"))
			{
				parsedToken = PathParser.parseToken(token, seriesDto.getSeriesName());
			}
			else if(token.getName().equals("SeasonNumber"))
			{
				parsedToken = PathParser.parseToken(token, tvdbEpisode.getSeasonNumber());
			}
			else if(token.getName().equals("EpisodeNumber"))
			{
				parsedToken = PathParser.parseToken(token, tvdbEpisode.getEpisodeNumber());
			}
			else if(token.getName().equals("EpisodeName"))
			{
				parsedToken = PathParser.parseToken(token, tvdbEpisode.getEpisodeName());
			}
			
			rawEpisodePath = PathParser.parsePath(parsedToken, rawEpisodePath);
		}
		
		
		if(PathParser.containsTokens(rawEpisodePath))
			throw new PathParseException(String.format("Episode path is malformed. Path contains tokens after being parsed: %s", rawEpisodePath));
		
		return rawEpisodePath;
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
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not generate thumbprint for file");
				log.debug(String.format("File %s could not be moved. thumbprint for file is invalid", file.getAbsolutePath()));
				return null;
			}
		} 
		catch (IOException e)
		{
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not generate thumbprint for file");
			log.error(String.format("File %s could not be moved. could not acquire a thumbprint for the file", file.getAbsolutePath()), e);
			return null;
		}
		
		
		OstTitleDto dto = null;
		try
		{
			dto = Services.getOpenSubtitlesClient().getTitleForHash(fileThumbprint);
			
			if(dto == null || dto.getPossibleTitles().size() == 0)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not find title for thumbprint");
				log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
						file.getAbsolutePath()));
				return null;
			}
			
			return dto.getFirstMovieOrEpisodeTitleWithImdb();
		} 
		catch (OpenSubtitlesResponseException e) 
		{
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
					"While attempting to perform a thumbprint search against the file the Open Subtitles service returned an error in its response");
			
			log.error(String.format("While attempting to perform a thumbprint search against %s the Open Subtitles service returned an error in its response", 
					file.getAbsolutePath()), e);
			
			
			return null;
		}
		catch (OpenSubtitlesException e) 
		{
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, "An unknown error occured while performing a thumbprint search against the Open Subtitles web service. See system logs for details");
			log.error(String.format("Open Subtitles thumbprint search on file %s failed. An unknown error occured while performing a thumbprint search against the service", 
					file.getAbsolutePath()), e);
			return null;
		}
		
	}

	
	
	
}




















