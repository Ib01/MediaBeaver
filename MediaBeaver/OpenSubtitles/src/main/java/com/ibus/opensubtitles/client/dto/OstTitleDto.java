package com.ibus.opensubtitles.client.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		if(titles == null)
			return;
		
		possibleTitles.clear();
		for(int i = 0; i < titles.length; i++)
        {
			possibleTitles.add((Map<String, String>) titles[i]);
        }
	}
	
	public Map<String,String> getFirstMovieOrEpisodeTitleWithImdb()
	{
		for(Map<String, String> title : getPossibleTitles())
		{
			if((title.get(OpenSubtitlesField.MovieKind.toString()).equals("episode") || title.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
					&& title.get(OpenSubtitlesField.IDMovieImdb.toString()) != null 
					&& title.get(OpenSubtitlesField.IDMovieImdb.toString()).length() > 0)
			{
				return title;
			}
		}
		
		return null;
	}
	
	
	
}
