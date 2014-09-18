package com.ibus.mediabeaver.cli.utility;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.core.entity.Event;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.util.PathParser;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

public class MediaMover extends FileProcessorBase
{	
	TvdbClient tvdbClient;
	TmdbApi tmdbApi;
	List<PathToken> episodePathTokens;
	List<PathToken> moviePathTokens;
	OpenSubtitlesClient openSubtitlesClient;
	private static String ostUserName = "";
	private static String ostPassword = "";
	private static String ostUseragent = "OS Test User Agent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private int mediaFound = 0;
	private int mediaMoved = 0;
	
	public MediaMover()
	{
		openSubtitlesClient = new OpenSubtitlesClient(ostHost,ostUseragent,ostUserName, ostPassword,ostSublanguageid);
		tvdbClient = new TvdbClient();
		tmdbApi = new TmdbApi(tmdbApiKey);
	}
	
	@Override
	protected void beforeProcess() throws MalformedURLException, XmlRpcException
	{
		mediaFound = 0;
		mediaMoved = 0;
		
		log.debug("******************************************************");
		log.debug("Commencing Movement of files");
		
		log.debug("Logging into the Open Subtitles web serivce.");
		if(!openSubtitlesClient.login())
		{
			log.debug("Aborting movement of files. Could not login to the Open Subtitles web serivce.");
			return;
		}
		
		episodePathTokens = PathParser.getTokens(config.getEpisodePath());
		moviePathTokens = PathParser.getTokens(config.getMoviePath());
	}

	@Override
	protected void afterProcess() throws MalformedURLException, IOException, XmlRpcException
	{
		log.debug("");
		log.debug("File movement is complete");
		log.debug(String.format("Media found= %d", mediaFound));
		log.debug(String.format("Media moved= %d", mediaMoved));
		log.debug("******************************************************");
		openSubtitlesClient.logOut();
	}
	
	
	@Override
	protected void processFile(File file) 
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(config.isVideoExtension(extension))
		{
			++mediaFound;
			moveVideo(file);
		}
	}
	
	protected void moveVideo(File file) 
	{
		try
		{
			//get title for file hash from the open subtitles service
			Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
			if(ostTitle == null)
				return;
			
			String destination = null;
			
			//let the OST Service identify what kind of file we have 
			if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series"))
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
					logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get valid title from service");
					log.debug(
							String.format("Aborting movement of %s. Failed to get a valid season and episode number from the open subtitles service.", 
							file.getAbsolutePath()));
					return;
				}
				
				//TvdbSeriesResponseDto only contains very basic info about the series itself.
				TvdbSeriesResponseDto seriesDto = tvdbClient.getSeries(seriesImdbid);
				if(seriesDto == null || seriesDto.getSeries() == null || seriesDto.getSeries().getId() == null)
				{
					logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get valid title from service");
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
				
				String destinationPathEnd = parseEpisodePath(seriesDto, tvdbEpisode);
				
				log.debug(String.format("Destination path end generated %s.%s.", 
						destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath())));
				
				destination = FilenameUtils.concat(config.getTvRootDirectory(), destinationPathEnd + "." + FilenameUtils.getExtension(file.getAbsolutePath()));
				//destination = FilenameUtils.concat("C:\\Users\\Ib\\Desktop\\MediabeaverTests", "File" + mediaMoved + "." + FilenameUtils.getExtension(file.getAbsolutePath()));
			}
			else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
			{
				//I am guessing that these are stored as number in the open subtitles db. gop figure.
				String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
				FindResults result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
				
				String destinationPathEnd = parseMoviePath(result.getMovieResults().get(0));
				
				log.debug(String.format("Destination path end generated %s.%s.", 
						destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath())));
		
				destination = FilenameUtils.concat(config.getMovieRootDirectory(), destinationPathEnd + "." + FilenameUtils.getExtension(file.getAbsolutePath()));
				//destination = FilenameUtils.concat("C:\\Users\\Ib\\Desktop\\MediabeaverTests", "File" + mediaMoved + "." + FilenameUtils.getExtension(file.getAbsolutePath()));
			}
			
			fileSys.moveFile(file.getAbsolutePath(), destination, false);
			//fileSys.moveFile(file.getAbsolutePath(), destination);
			++mediaMoved;
			logEvent(file.getAbsolutePath(), destination, ResultType.Succeeded, null);
			
			return;
			
		} catch (URISyntaxException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move a file"), e);
		} catch (PathParseException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move a file"), e);
		} catch (IOException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move a file"), e);
		}

		logEvent(file.getAbsolutePath(), null, ResultType.Failed, "An unexpected error occured while attempting to move file");
	}
	
	
	private void logEvent(String source, String destination, ResultType result, String errorDescription)
	{
		Event event = new Event();
		
		event.setEventTime(new Date());
		event.setEventType(EventType.Move);
		event.setDestinationPath(destination);
		event.setResult(result);
		event.setSourcePath(source);
		event.setErrorDescription(errorDescription);
		
		logEvent(event);
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
			logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Could not find title for thumbprint");
			log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
					file.getAbsolutePath()));
			return null;
		}
		
	}

	
	
	
}




















