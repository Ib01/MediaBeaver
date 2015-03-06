package com.ibus.opensubtitles.client.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;

public class OstTitleDto
{
	private List<Map<String,String>> possibleTitles = new ArrayList<Map<String,String>>();

	public List<Map<String,String>> getPossibleTitles()
	{
		return possibleTitles;
	}

	public void setPossibleTitles(List<Map<String,String>> possibleTitles)
	{
		this.possibleTitles = possibleTitles;
	}
	
	@SuppressWarnings("unchecked")
	public void setPossibleTitles(Object[] titles)
	{
		possibleTitles.clear();
		if(titles == null)
			return;
		
		for(int i = 0; i < titles.length; i++)
        {
			if(titles[i] instanceof Map)
			{
				//TODO: how can i know that titles[i] is instanceof Map<String, String> instead of just map
				possibleTitles.add((Map<String, String>) titles[i]);	 
			}
        }
	}
	
	public Map<String,String> getFirstMovieOrEpisodeTitleWithImdb()
	{
		for(Map<String, String> title : getPossibleTitles())
		{
			if((	title.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") 
					|| title.get(OpenSubtitlesField.MovieKind.toString()).equals("tv series") 
					||title.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
					&& (title.get(OpenSubtitlesField.IDMovieImdb.toString()) != null && title.get(OpenSubtitlesField.IDMovieImdb.toString()).length() > 0))
			{
				return title;
			}
		}
		
		return null;
	}
	
	public static String parseImdbId(String imdbId)
	{
		if(imdbId == null || imdbId.trim().length() == 0)
			return null;
		return "tt" + StringUtils.leftPad(imdbId, 7, "0");	
	}
	
	
	
	
}











