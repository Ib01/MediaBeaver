package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.TvdbSeriesDto;


public class SearchSeriesViewModel 
{
	private String searchText;
	private List<TvdbSeriesDto> searchResults = new ArrayList<TvdbSeriesDto>();
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String series) {
		this.searchText = series;
	}
	
	public List<TvdbSeriesDto> getSearchResults() {
		return searchResults;
	}
	public void setSearchResults(List<TvdbSeriesDto> searchResults) {
		this.searchResults = searchResults;
	}

	
/*	public String getBannerUrlPrefix() {
		return "/HotlinkedImage?imgUri=http://www.thetvdb.com/banners";
	}*/
	/*public void setSearchText(String series) {
		this.searchText = series;
	}*/
	
	
	


}
