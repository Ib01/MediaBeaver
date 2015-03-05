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
import com.ibus.mediabeaver.core.exception.MetadataException;
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
	
	public String getMoviePath(Map<String,String> ostTitle, File file) throws MetadataException, PathParseException 
	{
		String imdbId = OstTitleDto.parseImdbId(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()));
		if(imdbId == null)
			throw new MetadataException("The Open Subtitles service did not return a valid IMDB Id");
		
		FindResults result = Factory.getTmdbClient().getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
		
		//TODO: assume that since we search by id we can only get one result?? 
		if(result == null || result.getMovieResults().size() != 1)
			throw new MetadataException("The TMDB Service returned <> 1 result when searching for a movie with imdbId: " + imdbId);
		
		String destinationPathEnd = pathParser.parseMoviePath(result.getMovieResults().get(0).getTitle(), result.getMovieResults().get(0).getReleaseDate());
		
		destinationPathEnd += "." + FilenameUtils.getExtension(file.getAbsolutePath());
		return destinationPathEnd;
	}
	
}
