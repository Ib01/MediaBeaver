package com.ibus.mediabeaver.core.filesystem;

import info.movito.themoviedbapi.model.MovieDb;

import java.util.List;

import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.PathParseException;

public class MoviePathParser 
{
	List<PathToken> moviePathTokens;
	String moviePath;
	
	public MoviePathParser(String moviePath)
	{
		this.moviePath = moviePath;
		moviePathTokens = PathParser.getTokens(moviePath);
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
	public String parseMoviePath(MovieDb movie) throws PathParseException
	{
		String rawMoviePath =  moviePath; //path with tokens in it
		
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
}
