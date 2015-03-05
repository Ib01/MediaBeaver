package com.ibus.mediabeaver.core.filesystem;

import info.movito.themoviedbapi.model.MovieDb;

import java.util.List;

import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.exception.ServiceDataException;

public class MoviePathParser 
{
	List<PathToken> moviePathTokens;
	String movieFormatPath;
	
	public MoviePathParser(String movieFormatPath)
	{
		this.movieFormatPath = movieFormatPath;
		moviePathTokens = PathParser.getTokens(movieFormatPath);
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
	public String parseMoviePath(String title, String releaseDate) throws PathParseException
	{
		String rawMoviePath =  movieFormatPath; //path with tokens in it
		
		for(PathToken token : moviePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("MovieName"))
			{
				if(title == null || title.length() == 0)
					throw new ServiceDataException("The TMDB Service returned empty or null for the movie title");
				
				parsedToken = PathParser.parseToken(token, title);
			}
			else if(token.getName().equals("ReleaseDate"))
			{
				parsedToken = PathParser.parseToken(token, releaseDate); 
				if(releaseDate == null || releaseDate.length() == 0)
					throw new ServiceDataException("The TMDB Service returned empty or null for the releaseDate");
			}
			
			rawMoviePath = PathParser.parsePath(parsedToken, rawMoviePath);
		}
		
		if(PathParser.containsTokens(rawMoviePath))
			throw new PathParseException(String.format("Episode path is malformed. Path contains tokens after being parsed: %s", rawMoviePath));
		
		return rawMoviePath;
	}
}  












