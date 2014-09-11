package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ibus.mediabeaver.core.util.PathParser;
import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.entity.ServiceFieldMap;
import com.ibus.mediabeaver.core.exception.FileExistsException;
import com.ibus.mediabeaver.core.exception.FileNotExistException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

import info.movito.themoviedbapi.TmdbFind;

import org.apache.commons.io.FilenameUtils;

public class MediaManager extends MediaManagerBase
{	
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private static String tvdbScheme =  "http";
	private static String tvdbHost =  "www.thetvdb.com";
	private static String tvdbLanguage =  "en";
	
	TvdbClient tvdbClient;
	TmdbApi tmdbApi;
	List<PathToken> episodePathTokens;
	List<PathToken> moviePathTokens;
	
	public MediaManager(Configuration config)
	{
		this.config = config;
		episodePathTokens = PathParser.getTokens(config.getEpisodePath());
		moviePathTokens = PathParser.getTokens(config.getMoviePath());
		tvdbClient = new TvdbClient(tvdbScheme, tvdbHost, tvdbLanguage);
		tmdbApi = new TmdbApi(tmdbApiKey);
	}
	
	@Override
	protected void moveFile(File file) 
	{
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(config.isVideoExtension(extension))
		{
			moveVideo(file);
		}
	}
	
	
	
	protected void moveVideo(File file) 
	{
		try
		{
			Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
			if(ostTitle == null)
				return;
			
			if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series"))
			{
				String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()));
				if(imdbId == null)
				{
					log.debug(String.format("Failed to get a valid series imdbId from open subtitles service. Aborting movement of %s.", file.getAbsolutePath()));
					return;
				}
				
				String seasonNumber = ostTitle.get(OpenSubtitlesField.SeriesSeason.toString());
				String episodeNumber = ostTitle.get(OpenSubtitlesField.SeriesSeason.toString());
				if(seasonNumber == null || seasonNumber.length() == 0 || episodeNumber == null || episodeNumber.length() == 0)
				{
					log.debug(
							String.format("Failed to get a valid season and episode number from the open subtitles service. aborting movement of %s.", 
							file.getAbsolutePath()));
					return;
				}
				
				TvdbSeriesResponseDto seriesDto = tvdbClient.getSeries(imdbId);
				if(seriesDto == null || seriesDto.getSeries() == null || seriesDto.getSeries().getId() == null)
				{
					log.debug(
							String.format("Failed to get a valid series data from the TMDB service. aborting movement of %s.", 
							file.getAbsolutePath()));
					return;
				}
				
				long tvdbSeriesId = seriesDto.getSeries().getId();
				TvdbEpisodesResponseDto tvdbEpisodes = tvdbClient.getEpisodes(Long.toString(tvdbSeriesId));
				if(tvdbEpisodes == null)
				{
					log.debug(
							String.format("Failed to get a valid episode data from the TMDB service. aborting movement of %s.", 
							file.getAbsolutePath()));
					return;
				}
				
				TvdbEpisodeDto tvdbEpisode = tvdbEpisodes.getEpisode(seasonNumber, episodeNumber);
				log.debug(String.format("Successfully acquired episode data for %s.", file.getAbsolutePath()));
				
				String destinationPathEnd = parseEpisodePath(seriesDto, tvdbEpisode);
				
				log.debug(String.format("Destination path end generated %s.%s.", 
						destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath())));
		
				fileSys.moveFile(file.getAbsolutePath(), config.getTvRootDirectory(), destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath()));
			}
			else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
			{
				//I am guessing that these are stored as number in the open subtitles db. gop figure.
				String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
				FindResults result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
				
				String destinationPathEnd = parseMoviePath(result.getMovieResults().get(0));
				
				log.debug(String.format("Destination path end generated %s.%s.", 
						destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath())));
		
				fileSys.moveFile(file.getAbsolutePath(), config.getMovieRootDirectory(), destinationPathEnd, FilenameUtils.getExtension(file.getAbsolutePath()));
			}
			
			
		} catch (URISyntaxException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of"), e);
		} catch (PathParseException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of"), e);
		} catch (IOException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of"), e);
		} catch (FileNotExistException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of"), e);
		} catch (FileExistsException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of"), e);
		}

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
				log.debug(String.format("File %s could not be moved. thumbprint for file is invalid", file.getAbsolutePath()));
				return null;
			}
		} 
		catch (IOException e)
		{
			log.debug(String.format("File %s could not be moved. could not acquire a thumbprint for the file", file.getAbsolutePath()));
			return null;
		}
		
		
		OstTitleDto dto = null;
		try
		{
			dto = openSubtitlesClient.getTitleForHash(fileThumbprint);
			
			if(dto == null || dto.getPossibleTitles().size() == 0)
			{
				log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
						file.getAbsolutePath()));
				return null;
			}
			
			return dto.getFirstMovieOrEpisodeTitleWithImdb();
		} 
		catch (Exception e)
		{
			log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
					file.getAbsolutePath()));
			return null;
		}
		
	}
	
	
	
	
	
	
	//tt0090605
	//tt1480055
	//tt1480055
	//tt1480055
	//tt0944947
	

}




















