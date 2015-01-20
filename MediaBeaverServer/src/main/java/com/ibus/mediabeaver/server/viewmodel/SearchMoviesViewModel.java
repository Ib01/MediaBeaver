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
	private int selectedMovieId;
	private int currentPage;
	private int totalPages;
	
	
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getSelectedMovieId() {
		return selectedMovieId;
	}
	public void setSelectedMovieId(int selectedMovieId) {
		this.selectedMovieId = selectedMovieId;
	}
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

	public MovieDb getSelectedMovie()
	{
		MovieDb selected = null;
		for(MovieDb s : searchResults)
		{
			if(s.getId() == getSelectedMovieId())
				selected = s;
		}
		
		return selected;
	}
	

}
