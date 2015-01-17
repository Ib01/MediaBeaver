package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Series;


public class SearchSeriesViewModel 
{
	private String searchText;
	private List<Series> searchResults = new ArrayList<Series>();
	private long selectedSeriesId;
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String series) {
		this.searchText = series;
	}
	
	public List<Series> getSearchResults() {
		return searchResults;
	}
	public void setSearchResults(List<Series> searchResults) {
		this.searchResults = searchResults;
	}
	public long getSelectedSeriesId() {
		return selectedSeriesId;
	}
	public void setSelectedSeriesId(long selectedSeriesId) {
		this.selectedSeriesId = selectedSeriesId;
	}


	public Series getSelectedSeries()
	{
		Series selected = null;
		for(Series s : searchResults)
		{
			if(s.getId().equals(selectedSeriesId))
				selected = s;
		}
		
		return selected;
	}

}
