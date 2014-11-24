package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class CopyOfSelectMediaViewModel 
{
	private List<String> filesToResolve = new ArrayList<String>(); 
	private String selectedMediaType;
	private String MovieName;
	private String MovieYear;
	private String Series;
	private String Season;
	private String Episode;
	private String wizardAction;
	
	public String getSelectedMediaType() {
		return selectedMediaType;
	}
	public void setSelectedMediaType(String selectedMediaType) {
		this.selectedMediaType = selectedMediaType;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	public String getMovieYear() {
		return MovieYear;
	}
	public void setMovieYear(String movieYear) {
		MovieYear = movieYear;
	}
	public String getSeries() {
		return Series;
	}
	public void setSeries(String series) {
		Series = series;
	}
	public String getSeason() {
		return Season;
	}
	public void setSeason(String season) {
		Season = season;
	}
	public String getEpisode() {
		return Episode;
	}
	public void setEpisode(String episode) {
		Episode = episode;
	}
	public List<String> getFilesToResolve() {
		return filesToResolve;
	}
	public void setFilesToResolve(List<String> filesToResolve) {
		this.filesToResolve = filesToResolve;
	}
}
