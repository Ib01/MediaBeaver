package com.ibus.mediabeaver.core.util;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.filesystem.EpisodePathParser;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

public class TvService 
{
	Logger log = Logger.getLogger(MediaMover.class.getName());
	EventLogger eventLogger;
	EpisodePathParser episodeParser;
	Configuration userConfiguration;
	
	public TvService(EventLogger eventLogger, Configuration configuration, EpisodePathParser episodeParser)
	{
		this.userConfiguration = configuration;  
		this.eventLogger = eventLogger;
		this.episodeParser = episodeParser;	
	}
	
	/**
	 * generate a path for the episode using the users episode path format string specified in user configuration.  
	 * excludes the TV Series root directory  
	 * @param ostTitle
	 * @param file
	 * @return
	 */
	public String getEpisodePath(Map<String,String> ostTitle, File file)
	{
		String fullDestinationPath = "";
		try
		{
			//Get Series imdb from the OST title. we will use this id to get data from other services 
			String seriesImdbid = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()));
			if(seriesImdbid == null || seriesImdbid.trim().length() == 0)
			{
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid series IMDB Id from the Open Subtitles Service.");
				log.debug(String.format("Aborting path generation for %s. Failed to get a valid series IMDB Id from open subtitles service.", file.getAbsolutePath()));
				return null;
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
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid season number from the Open Subtitles Service.");
				log.debug(String.format("Aborting path generation for %s. Failed to get a valid season number from the Open Subtitles Service.", file.getAbsolutePath()));
				return null;
			}
			
			try
			{
				episodeNumber = Integer.parseInt(ostTitle.get(OpenSubtitlesField.SeriesEpisode.toString()));
			}
			catch(NumberFormatException ex)
			{
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get a valid episode number from the Open Subtitles Service.");
				log.debug(String.format("Aborting path generation for %s. Failed to get a valid episode number from the Open Subtitles Service.", file.getAbsolutePath()));
				return null;
			}
			
			
			//TvdbSeriesResponseDto only contains very basic info about the series itself.
			Series seriesDto = Factory.getTvdbClient().getSeriesForImdbId(seriesImdbid);
			if(seriesDto == null || seriesDto.getId() == null)
			{
				eventLogger.logEvent(file.getAbsolutePath(), null,ResultType.Failed, "Failed to get valid series data from the TVDB service.");
				log.debug(
						String.format("Aborting path generation for %s. Failed to get valid series data from the TVDB service.", 
						file.getAbsolutePath()));
				return null;
			}
			
			long tvdbSeriesId = seriesDto.getId();
			//TvdbEpisodesResponseDto contains detailed info about not only the series but all its seasons and episodes. pity there isn't a way to 
			//get single episode info for an episode imdb.
			Episode tvdbEpisode = Factory.getTvdbClient().getEpisode(tvdbSeriesId, seasonNumber, episodeNumber);
			if(tvdbEpisode == null)
			{
				eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, "Failed to get episode data from the TVDB service.");
				log.debug(
						String.format("Aborting path generation for %s. Failed to get episode data from the TVDB service.", 
						file.getAbsolutePath()));
				return null;
			}
			
			//we now have the series.  i.e series info, seasons info and info on every episode. but we don't know which episode to get info on 
			//so we need to use season and episode numbers from OST Service 
			log.debug(String.format("Successfully acquired episode data for %s.", file.getAbsolutePath()));
			
			String  destinationPathEnd = episodeParser.parseEpisodePath(seriesDto, tvdbEpisode);
			destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
			
			return destinationPathEnd;
			
		}
		catch (PathParseException e)
		{
			log.error(String.format("Aborting path generation for %s. An error occured while parsing the path format string: %s", 
					file.getAbsolutePath(), userConfiguration.getEpisodeFormatPath()), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, 
					"An error occured generating a path for the file. Could not parse the path format string to a valid path");
			return null;
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			eventLogger.logEvent(file.getAbsolutePath(), null, ResultType.Failed, 
					"An error occured generating a path for the file.  The TVDB service appears to be unavailable.");
			log.error(
					String.format("An error occured while generating a path for %s. the TVDB Service appears to be unavailable.", 
					file.getAbsolutePath()));
			return null;
		}
	}
	
	
}






