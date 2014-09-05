package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.FindResults;

import com.ibus.mediabeaver.core.entity.MediaConfig2;
import com.ibus.mediabeaver.core.entity.ServiceFieldMap;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

import info.movito.themoviedbapi.TmdbFind;

import com.ibus.mediabeaver.core.util.RegExHelper;

public class MediaManager extends MediaManagerBase2
{	
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private static String tvdbScheme =  "http";
	private static String tvdbHost =  "www.thetvdb.com";
	private static String tvdbLanguage =  "en";
	
	
	TvdbClient tvdbClient;
	TmdbApi tmdbApi;
	List<ServiceFieldMap> serviceFieldMaps;
	ServiceFieldParser fieldParser; //TODO: keep?
	RegExHelper regExHelper;//TODO: keep?
	
	
	public MediaManager(List<ServiceFieldMap> serviceFieldMaps, MediaConfig2 config)
	{
		this.config = config;
		this.serviceFieldMaps = serviceFieldMaps;
		tvdbClient = new TvdbClient(tvdbScheme, tvdbHost, tvdbLanguage);
		tmdbApi = new TmdbApi(tmdbApiKey);
		/*fieldParser = new ServiceFieldParser();
		regExHelper = new RegExHelper();*/
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
				
				//return tvdbEpisode;
				
			}
			else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
			{
				//I am guessing that these are stored as number in the open subtitles db. gop figure.
				String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
				FindResults result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
				
			}
			
	
			
		
		} catch (URISyntaxException e)
		{
			log.error(String.format("An unexpected error occured while attempting to search fo the file using a web service.  aborting movement of %s."), e);
			
			
			
			config.getMoviePath();
		}
		

	}

	
	
	
	public void parseEpisodePath(TvdbEpisodesResponseDto tvdbEpisodes)
	{
		/*
		 * get each token from path 
		 * split token into variable and its methods
		 * 
		 * itterate over each variable and get its value from the dto(s)
		 * parse value using methods
		 * replace each token in path with parsed value 
		 * */
		
		Pattern pattern = Pattern.compile("\\{([a-zA-Z])\\.(.+?)\\}");
		Matcher matcher = pattern.matcher(config.getEpisodePath());
		
		while (matcher.find()) 
		{
			for(int i = 0; i <= matcher.groupCount(); i++)
			{
				if(matcher.group(i)!= null )
				{
					String group = matcher.group(i).trim();
					if(group.length() > 0)
						captures.add(group);
				}
			}
		}			
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
	
	
	
	
	
	
	
	
	
	/*private void MoveFile(File file, MediaConfig config, String path)
	{
		try
		{
			fileSys.moveFile(file.getAbsolutePath(), config.getDestinationRoot(), path, FilenameUtils.getExtension(file.getAbsolutePath()));
			return;
		} 
		catch (IOException e)
		{
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		} 
		catch (FileNotExistException e){
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		} 
		catch (FileExistsException e)
		{
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		}
		
	}*/
	
	//tt0090605
			//tt1480055
			//tt1480055
			//tt1480055
			//tt0944947
			
			/*//fill path tokens from the first title that has data for all our fields
			HashMap<String, String> tokens = new HashMap<String, String>(); 
			for(Map<String, String> title : dto.getPossibleTitles())
			{
				tokens = new HashMap<String, String>();
				
				//using our selectors get the values that can be used for file token paths    
				for(OpenSubtitlesSelector selector : config.getOpenSubtitlesSelectors())
				{
					//get token identifier from getOpenSubititleField. i.e get MovieYear in {MovieYear}.replaceAll(regex, replacement).
					//then get the value for that field from our service object
					String field = fieldParser.getFieldIdentifier(selector.getOpenSubititleField());
					String fieldVal = title.get(field);
					
					if(fieldVal != null && fieldVal.length() > 0)
					{
						log.debug(String.format("A raw value of %s was found for token %s ", fieldVal, selector.getPathTokenName()));
						
						//Parse the value of our service field using our parser and the methods specified in the 
						//getOpenSubititleField
						fieldVal = fieldParser.parseField(fieldVal, selector.getOpenSubititleField());	
						tokens.put(selector.getPathTokenName(), fieldVal);
						
						log.debug(String.format("The value for token %s was parsed and set to %s", selector.getPathTokenName(), fieldVal));
					}
				}
				
				//we have a value for all of our tokens.  time to format our path using the tokens and then move the file 
				if(tokens.size() == config.getOpenSubtitlesSelectors().size())
				{
					String finalPath = regExHelper.assembleFileName(tokens, config.getRelativeDestinationPath());
					MoveFile(file, config, finalPath);
					break;
				}
			}*/
		   
	
	
	
	
	

}




















