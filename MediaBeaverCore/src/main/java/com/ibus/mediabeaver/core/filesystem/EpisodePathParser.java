package com.ibus.mediabeaver.core.filesystem;

import java.util.List;

import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;

public class EpisodePathParser 
{
	List<PathToken> episodePathTokens;
	String episodePath;
	
	public EpisodePathParser(String episodePath)
	{
		this.episodePath = episodePath;
		episodePathTokens = PathParser.getTokens(episodePath);
	}
	

	/**
	 * parse tokenised or templated episode path for an episode. for eg will  
	 * parse: 
	 *  \{SeriesName}\Season {SeasonNumber}\{SeriesName} S{SeasonNumber}.leftPad("2","0")E{EpisodeNumber}.leftPad("2","0")"
	 *  to: 
	 *  Game of Thrones\Season 1\Game of Thrones S01E01.avi
	 * @param seriesDto
	 * @param tvdbEpisode
	 * @return
	 * @throws PathParseException
	 */
	public String parseEpisodePath(String seriesName, String seasonNumber, String episodeNumber, String episodeName) throws PathParseException
	{
		String rawEpisodePath =  episodePath; //path with tokens in it
		
		for(PathToken token : episodePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("SeriesName"))
			{
				parsedToken = PathParser.parseToken(token, seriesName);
			}
			else if(token.getName().equals("SeasonNumber"))
			{
				parsedToken = PathParser.parseToken(token, seasonNumber);
			}
			else if(token.getName().equals("EpisodeNumber"))
			{
				parsedToken = PathParser.parseToken(token, episodeNumber);
			}
			else if(token.getName().equals("EpisodeName"))
			{
				parsedToken = PathParser.parseToken(token, episodeName);
			}
			
			
			rawEpisodePath = PathParser.parsePath(parsedToken, rawEpisodePath);
		}
		
		
		if(PathParser.containsTokens(rawEpisodePath))
			throw new PathParseException(String.format("Episode path is malformed. Path contains tokens after being parsed: %s", rawEpisodePath));
		
		return rawEpisodePath;
	}
	
}
