package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MoviePathParser;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;

public class MovieService 
{
	Logger log = Logger.getLogger(MediaMover.class.getName());
	EventLogger eventLogger;
	Configuration userConfiguration;
	MoviePathParser pathParser; 
	
	public MovieService(EventLogger eventLogger, Configuration configuration, MoviePathParser movieParser)
	{
		this.eventLogger = eventLogger;
		this.userConfiguration = configuration;
		this.pathParser = movieParser;
	}
	
	public String getMoviePath(Map<String,String> ostTitle, File file) 
	{
		String fullDestinationPath = "";
		try
		{
			//I am guessing that these are stored as number in the open subtitles db. gop figure.
			String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
			FindResults result = Factory.getTmdbClient().getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
			String destinationPathEnd = pathParser.parseMoviePath(result.getMovieResults().get(0).getTitle(), 
					result.getMovieResults().get(0).getReleaseDate());
			destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
			
			return destinationPathEnd;
		
		} 
		catch (PathParseException e)
		{
			log.error(String.format("An error occured parsing the path format string: %s; while attempting to move the file %s", 
					userConfiguration.getMovieFormatPath(), file.getAbsolutePath()), e);	
			eventLogger.logEvent(file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "Could not parse the path format string to a valid path");
			return null;
		}
	}
	
}
