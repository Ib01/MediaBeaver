package com.ibus.mediabeaver.core.filesystem;

import java.util.List;

import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.MetadataException;
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
	 * @throws MetadataException 
	 */
	public String parseEpisodePath(String seriesName, String seasonNumber, String episodeNumber, String episodeName) throws PathParseException, MetadataException
	{
		String rawEpisodePath =  episodePath; //path with tokens in it
		
		for(PathToken token : episodePathTokens)
		{
			PathToken parsedToken = null;
			if(token.getName().equals("SeriesName"))
			{
				if(seriesName == null || seriesName.length() == 0)
					throw new MetadataException("Series name is null or empty");
				
				parsedToken = PathParser.parseToken(token, seriesName);
			}
			else if(token.getName().equals("SeasonNumber"))
			{
				if(seasonNumber == null || seasonNumber.length() == 0)
					throw new MetadataException("Season number is null or empty");
				
				parsedToken = PathParser.parseToken(token, seasonNumber);
			}
			else if(token.getName().equals("EpisodeNumber"))
			{
				if(episodeNumber == null || episodeNumber.length() == 0)
					throw new MetadataException("Episode Number is null or empty");
				
				parsedToken = PathParser.parseToken(token, episodeNumber);
			}
			else if(token.getName().equals("EpisodeName"))
			{
				if(episodeName == null || episodeName.length() == 0)
					throw new MetadataException("Episode name is null or empty");
				
				parsedToken = PathParser.parseToken(token, episodeName);
			}
			
			rawEpisodePath = PathParser.parsePath(parsedToken, rawEpisodePath);
		}
		
		
		/*if(PathParser.containsTokens(rawEpisodePath))
			throw new PathParseException(String.format("Episode path is malformed. Path contains tokens after being parsed: %s", rawEpisodePath));*/
		
		return rawEpisodePath;
	}
	
}
