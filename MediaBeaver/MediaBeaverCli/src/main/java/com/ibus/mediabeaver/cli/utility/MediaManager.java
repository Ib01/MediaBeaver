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

import com.ibus.mediabeaver.core.entity.MediaConfig2;
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

public class MediaManager extends MediaManagerBase2
{	
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private static String tvdbScheme =  "http";
	private static String tvdbHost =  "www.thetvdb.com";
	private static String tvdbLanguage =  "en";
	
	TvdbClient tvdbClient;
	TmdbApi tmdbApi;
	ServiceFieldParser fieldParser; //TODO: keep?
	RegExHelper regExHelper;//TODO: keep?
	List<PathToken> episodePathTokens;
	
	public MediaManager(MediaConfig2 config)
	{
		this.config = config;
		episodePathTokens = PathParser.getTokens(config.getEpisodePath());
		tvdbClient = new TvdbClient(tvdbScheme, tvdbHost, tvdbLanguage);
		tmdbApi = new TmdbApi(tmdbApiKey);
	}

	
	
	@Override
	protected void processFile(File file) 
	{
		try
		{
			Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
			if(ostTitle == null)
				return;
			
			if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode"))
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
				if(seriesDto == null)
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
				
				String episodePath = parseEpisodePath(seriesDto, tvdbEpisode);
				
				fileSys.moveFile(file.getAbsolutePath(), config.getTvRootDirectory(), episodePath, FilenameUtils.getExtension(file.getAbsolutePath()));
				
				return;
				
			}
			else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
			{
				//I am guessing that these are stored as number in the open subtitles db. gop figure.
				String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
				FindResults result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
				
			}
			
	
		} catch (URISyntaxException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of %s."), e);
		} catch (PathParseException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of %s."), e);
		} catch (IOException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of %s."), e);
		} catch (FileNotExistException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of %s."), e);
		} catch (FileExistsException e)
		{
			log.error(String.format("An unexpected error occured while attempting to move of %s."), e);
		}

	}

	
	
	
	public String parseEpisodePath(TvdbSeriesResponseDto seriesDto, TvdbEpisodeDto tvdbEpisode) throws PathParseException
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




















