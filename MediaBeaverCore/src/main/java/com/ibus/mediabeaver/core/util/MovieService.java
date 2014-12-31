package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.entity.UserConfiguration;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MoviePathParser;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;

public class MovieService 
{
	Logger log = Logger.getLogger(MediaMover.class.getName());
	EventLogger eventLogger;
	UserConfiguration userConfiguration;
	MoviePathParser movieParser; 
	
	public MovieService(EventLogger eventLogger, UserConfiguration configuration, MoviePathParser movieParser)
	{
		this.eventLogger = eventLogger;
		this.userConfiguration = configuration;
		this.movieParser = movieParser;
	}
	
	public String getMoviePath(Map<String,String> ostTitle, File file) 
	{
		String fullDestinationPath = "";
		try
		{
			//I am guessing that these are stored as number in the open subtitles db. gop figure.
			String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
			FindResults result = Factory.getTmdbClient().getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
			String destinationPathEnd = movieParser.parseMoviePath(result.getMovieResults().get(0));
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
