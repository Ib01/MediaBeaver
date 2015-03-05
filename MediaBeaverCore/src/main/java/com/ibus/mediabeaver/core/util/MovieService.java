package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.exception.ServiceDataException;
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
	
	public String getMoviePath(Map<String,String> ostTitle, File file) throws ServiceDataException 
	{
		String fullDestinationPath = "";
		try
		{
			String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
			if(imdbId == null)
				throw new ServiceDataException("The Open Subtitles service did not return a valid IMDB Id");
			
			FindResults result = Factory.getTmdbClient().getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
			//TODO: assume that since we search by id we can only get one result?? 
			if(result.getMovieResults().size() != 1)
				throw new ServiceDataException("The TMDB Service returned <> 1 result when searching for a movie with imdbId: " + imdbId);
			
			String destinationPathEnd = pathParser.parseMoviePath(result.getMovieResults().get(0).getTitle(), result.getMovieResults().get(0).getReleaseDate());
			
			destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
			
			return destinationPathEnd;
		
		} 
		catch (PathParseException e)
		{
			log.error(String.format("An error occured parsing the path format string: %s; while attempting to move the file %s", 
					userConfiguration.getMovieFormatPath(), file.getAbsolutePath()), e);	
			eventLogger.logEvent(EventType.Move, file.getAbsolutePath(), fullDestinationPath, ResultType.Failed, "Could not parse the path format string to a valid path");
			return null;
		}
	}
	
}
