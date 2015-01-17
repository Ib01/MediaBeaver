package com.ibus.mediabeaver.server.viewmodel;

import info.movito.themoviedbapi.model.MovieDb;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Series;


public class SearchMoviesViewModel 
{
	private String movieName;
	private String movieYear;
	private List<MovieDb> searchResults = new ArrayList<MovieDb>();
	private String baseImageUrl;
	
	
	public List<MovieDb> getSearchResults() {
		return searchResults;
	}
	public void setSearchResults(List<MovieDb> searchResults) {
		this.searchResults = searchResults;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieYear() {
		return movieYear;
	}
	public void setMovieYear(String movieYear) {
		this.movieYear = movieYear;
	}
	public String getBaseImageUrl() {
		return baseImageUrl;
	}
	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}
	

	/*public Series getSelectedSeries()
	{
		Series selected = null;
		for(Series s : searchResults)
		{
			if(s.getId().equals(selectedSeriesId))
				selected = s;
		}
		
		return selected;
	}*/

}
