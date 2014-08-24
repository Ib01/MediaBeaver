package com.ibus.opensubtitles.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
}
