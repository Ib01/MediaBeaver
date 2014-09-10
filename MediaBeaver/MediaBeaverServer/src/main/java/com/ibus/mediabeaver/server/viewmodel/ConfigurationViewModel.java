package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

public class ConfigurationViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;

	private String sourceDirectory;
	private String tvRootDirectory;
	private String episodePath;
	private String movieRootDirectory;
	private String moviePath;
	private String videoExtensionFilter;
	
	
	public String getSourceDirectory()
	{
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory)
	{
		this.sourceDirectory = sourceDirectory;
	}

	public String getTvRootDirectory()
	{
		return tvRootDirectory;
	}

	public void setTvRootDirectory(String tvRootDirectory)
	{
		this.tvRootDirectory = tvRootDirectory;
	}

	public String getEpisodePath()
	{
		return episodePath;
	}

	public void setEpisodePath(String tvPath)
	{
		this.episodePath = tvPath;
	}

	public String getMovieRootDirectory()
	{
		return movieRootDirectory;
	}

	public void setMovieRootDirectory(String movieRootDirectory)
	{
		this.movieRootDirectory = movieRootDirectory;
	}

	public String getMoviePath()
	{
		return moviePath;
	}

	public void setMoviePath(String moviePath)
	{
		this.moviePath = moviePath;
	}

	public String getVideoExtensionFilter()
	{
		return videoExtensionFilter;
	}

	public void setVideoExtensionFilter(String videoExtensionFilter)
	{
		this.videoExtensionFilter = videoExtensionFilter;
	}
	
}



















