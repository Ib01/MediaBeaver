package com.ibus.mediabeaver.core.filesystem;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
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
import com.ibus.mediabeaver.core.filesystem.FileProcessorBase.Platform;
import com.ibus.mediabeaver.core.util.PathParser;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

public class MediaMover 
{	
	Logger log = Logger.getLogger(MediaMover.class.getName());
	TvdbClient tvdbClient;
	TmdbApi tmdbApi;
	OpenSubtitlesClient openSubtitlesClient;
	Configuration config;	
	FileSystem fileSys = new FileSystem();
	Platform platform;
	
	List<PathToken> episodePathTokens;
	List<PathToken> moviePathTokens;
	private static String ostUserName = "";
	private static String ostPassword = "";
	//private static String ostUseragent = "OS Test User Agent";
	private static String ostUseragent = "OSTestUserAgent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private List<Activity> unmovedMedia = new ArrayList<Activity>();
	private List<Activity> movedMedia = new ArrayList<Activity>();
	
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
	
	public MediaMover(Platform platform)
	{
		this.platform = platform;
		openSubtitlesClient = new OpenSubtitlesClient(ostHost,ostUseragent,ostUserName, ostPassword,ostSublanguageid);
		tvdbClient = new TvdbClient();
		tmdbApi = new TmdbApi(tmdbApiKey);
	}
	
	
	/**
	 * processes all files in directory and in all its sub directories
	 * @param config
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	public void processFiles(Configuration config) throws IOException, XmlRpcException 
	{	
		this.config = config;
		if(!beforeProcess())
			return;
		
		processFileTree(new File(config.getSourceDirectory()));
		afterProcess();
	}
	
	private void processFileTree(File directory) throws IOException 
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
	public void processFiles(Configuration config, List<String> paths) throws XmlRpcException, IOException 
	{ 
		this.config = config;
		if(!beforeProcess())
			return;
		
		for(String path : paths)
		{
			File file = new File(path);
			
			if(!file.isDirectory())
				processFile(file);
		}
		
		afterProcess();
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
		unmovedMedia = new ArrayList<Activity>();
		movedMedia = new ArrayList<Activity>();
		
		log.debug("******************************************************");
		log.debug("Commencing Movement of files");
		
		log.debug("Logging into the Open Subtitles web serivce.");
		Exception thrownException = null;
		try 
		{
			openSubtitlesClient.login();
			/*{
				log.debug("Aborting movement of files. Could not login to the Open Subtitles web serivce.");
				logEvent(null, null, ResultType.Failed, "Could not login to the Open Subtitles web serivce");
				return false;
			}*/
		
			episodePathTokens = PathParser.getTokens(config.getEpisodePath());
			moviePathTokens = PathParser.getTokens(config.getMoviePath());
			return true;
		} 
		catch (MalformedURLException e) {thrownException = e;} 
		catch (XmlRpcException e) {thrownException = e;}
		catch (OpenSubtitlesResponseException e) {thrownException = e;}
		
		log.error("Aborting movement of files. An exception was thrown while attempting to login to the Open Subtitles web serivce.", thrownException);
		logEvent(null, null, ResultType.Failed, "Could not login to the Open Subtitles web serivce");
		
		return true;
	}

	
	protected void afterProcess() throws MalformedURLException, IOException, XmlRpcException
	{
		log.debug("");
		log.debug("File movement is complete");
		log.debug(String.format("Media found= %d", movedMedia.size() + unmovedMedia.size()));
		log.debug(String.format("Media moved= %d", movedMedia.size()));
		log.debug("******************************************************");
		openSubtitlesClient.logOut();
	}
	
	
	
	protected void processFile(File file) 
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(config.isVideoExtension(extension))
		{
			moveVideo(file);
		}
	}
	
	protected void moveVideo(File file) 
	{
		//get title for file hash from the open subtitles service
		Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
		if(ostTitle == null)
			return;
		
		//let the OST Service identify what kind of file we have 
		if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series"))
		{
			moveTvEpisode(ostTitle, file);
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
			FindResults result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
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
	
	
	
	protected void moveTvEpisode(Map<String,String> ostTitle, File file) 
	{
		
		String fullDestinationPath = "";
		try
		{
			//Get Series imdb from the OST title. we will use this id to get data from other services 
			String seriesImdbid = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()));
			if(seriesImdbid == null)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get valid title from service");
				log.debug(String.format("Aborting movement of %s. Failed to get a valid series imdbId from open subtitles service.", file.getAbsolutePath()));
				return;
			}
			
			//we need the season and episode number from the OST Service to identify the episode we are after once we have gotten series info from TVDB below.
			//Note: we could stop here and just use data from the OST Service. However the data does not appear to be as accurate or as detailed as other 
			//services offer
			String seasonNumber = ostTitle.get(OpenSubtitlesField.SeriesSeason.toString());
			String episodeNumber = ostTitle.get(OpenSubtitlesField.SeriesEpisode.toString());
			if(seasonNumber == null || seasonNumber.length() == 0 || episodeNumber == null || episodeNumber.length() == 0)
			{
				logEvent(file.getAbsolutePath(), null,ResultType.Failed, "Failed to get valid title from service");
				log.debug(
						String.format("Aborting movement of %s. Failed to get a valid season and episode number from the open subtitles service.", 
						file.getAbsolutePath()));
				return;
			}
			
			//TvdbSeriesResponseDto only contains very basic info about the series itself.
			TvdbSeriesResponseDto seriesDto = tvdbClient.getSeriesForImdbId(seriesImdbid);
			if(seriesDto == null || seriesDto.getSeries() == null || seriesDto.getSeries().getId() == null)
			{
				logEvent(file.getAbsolutePath(), null,ResultType.Failed, "Failed to get valid title from service");
				log.debug(
						String.format("Aborting movement of %s. Failed to get a valid series data from the TMDB service.", 
						file.getAbsolutePath()));
				return;
			}
			
			long tvdbSeriesId = seriesDto.getSeries().getId();
			//TvdbEpisodesResponseDto contains detailed info about not only the series but all its seasons and episodes. pity there isn't a way to 
			//get single episode info for an episode imdb.
			TvdbEpisodesResponseDto tvdbEpisodes = tvdbClient.getEpisodes(Long.toString(tvdbSeriesId));
			if(tvdbEpisodes == null)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get valid title from service");
				log.debug(
						String.format("Aborting movement of %s. Failed to get a valid episode data from the TMDB service.", 
						file.getAbsolutePath()));
				return;
			}
			
			//we now have the series.  i.e series info, seasons info and info on every episode. but we don't know which episode to get info on 
			//so we need to use season and episode numbers from OST Service 
			TvdbEpisodeDto tvdbEpisode = tvdbEpisodes.getEpisode(seasonNumber, episodeNumber);
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
		catch (URISyntaxException e)
		{
			log.error(String.format("An unspecified error occured while moving the file %s", file.getAbsolutePath()), e);	
			logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "An unspecified error occured while moving file");
		}
		
	}
	
	
	private void moveFile(String source, String destinationRoot, String destinationEnd) throws IOException, DuplicateFileException
	{
		if(config.isCopyAsDefault())
			fileSys.copyFile(source, destinationRoot, destinationEnd);
		else
			fileSys.moveFile(source, destinationRoot, destinationEnd);
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
	private String parseEpisodePath(TvdbSeriesResponseDto seriesDto, TvdbEpisodeDto tvdbEpisode) throws PathParseException
	{
		String rawEpisodePath =  config.getEpisodePath(); //path with tokens in it
		
		for(PathToken token : episodePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("SeriesName"))
			{
				parsedToken = PathParser.parseToken(token, seriesDto.getSeries().getSeriesName());
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
			dto = openSubtitlesClient.getTitleForHash(fileThumbprint);
			
			if(dto == null || dto.getPossibleTitles().size() == 0)
			{
				logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not find title for thumbprint");
				log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
						file.getAbsolutePath()));
				return null;
			}
			
			return dto.getFirstMovieOrEpisodeTitleWithImdb();
		} 
		catch (Exception e)
		{
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, "An error occured while performing a thumbprint search against the Open Subtitles service");
			log.debug(String.format("Open Subtitles thumbprint search on file %s failed. An error occured while performing a thumbprint search against the service", 
					file.getAbsolutePath()));
			return null;
		}
		
	}

	
	
	
}




















