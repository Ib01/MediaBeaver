package com.ibus.mediabeaver.core.util;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.MetadataException;
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
	 * @throws MetadataException 
	 * @throws TvdbConnectionException 
	 * @throws TvdbException 
	 * @throws PathParseException 
	 */
	public String getEpisodePath(Map<String,String> ostTitle, File file) throws MetadataException, TvdbException, TvdbConnectionException, PathParseException
	{
		//Get Series imdb from the OST title. we will use this id to get data from other services 
		String seriesImdbid = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()));
		if(seriesImdbid == null || seriesImdbid.trim().length() == 0)
			throw new MetadataException("Could not get a valid Series IMDB Id from the Open Subtitles service");
		
		int seasonNumber; 
		try
		{
			//we need the season and episode number from the OST Service to identify the episode we are after once we have gotten series info from TVDB below.
			//Note: we could stop here and just use data from the OST Service. However the data does not appear to be as accurate or as detailed as other 
			//services offer
			seasonNumber = Integer.parseInt(ostTitle.get(OpenSubtitlesField.SeriesSeason.toString()));
		}
		catch(NumberFormatException ex)
		{
			throw new MetadataException("Could not get a valid season number from the Open Subtitles service");
		}
		
		int episodeNumber;
		try
		{
			episodeNumber = Integer.parseInt(ostTitle.get(OpenSubtitlesField.SeriesEpisode.toString()));
		}
		catch(NumberFormatException ex)
		{
			throw new MetadataException("Could not get a valid episode number from the Open Subtitles service");
		}
		
		
		//TvdbSeriesResponseDto only contains very basic info about the series itself.
		Series seriesDto = Factory.getTvdbClient().getSeriesForImdbId(seriesImdbid);
		if(seriesDto == null || seriesDto.getId() == null)
		{
			throw new MetadataException("Could not get series data from the TVDB service");
		}
		long tvdbSeriesId = seriesDto.getId();
		
		//TvdbEpisodesResponseDto contains detailed info about not only the series but all its seasons and episodes. pity there isn't a way to 
		//get single episode info for an episode imdb.
		Episode tvdbEpisode = Factory.getTvdbClient().getEpisode(tvdbSeriesId, seasonNumber, episodeNumber);
		if(tvdbEpisode == null)
		{
			throw new MetadataException("Could not get episode data from the TVDB service");
		}
		
		//we now have the series.  i.e series info, seasons info and info on every episode. but we don't know which episode to get info on 
		//so we need to use season and episode numbers from OST Service 
		log.debug(String.format("Successfully acquired episode data for %s.", file.getAbsolutePath()));
		
		String  destinationPathEnd = episodeParser.parseEpisodePath(seriesDto.getSeriesName(), 
				tvdbEpisode.getSeasonNumber(),tvdbEpisode.getEpisodeNumber(),tvdbEpisode.getEpisodeName());
		
		destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
		
		return destinationPathEnd;
		 
		
		
		/*catch (TvdbException | TvdbConnectionException e) 
		{
			eventLogger.logMoveEvent(file.getAbsolutePath(), null, ResultType.Failed, 
					"An error occured generating a path for the file.  The TVDB service appears to be unavailable.");
			log.error(
					String.format("An error occured while generating a path for %s. the TVDB Service appears to be unavailable.", 
					file.getAbsolutePath()));
			return null;
		}*/
	}
	
	
}






