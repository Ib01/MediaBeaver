package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "Configuration")
public class Configuration extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String sourceDirectory;
	
	@Column
	private String tvRootDirectory;
	
	@Column
	private String episodePath;
	
	@Column
	private String movieRootDirectory;
	
	@Column
	private String moviePath;
	
	@Column
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
	
	
	
	public boolean isVideoExtension(String extension)
	{
		String[] extensions = videoExtensionFilter.split("\\s*,\\s*");
		
		for(String allowedExtension : extensions)
		{
			extension = extension.trim().replace(".", "");
			allowedExtension = allowedExtension.trim().replace(".", "");
			
			if(extension.equals(allowedExtension))
				return true;
		}
		
		return false;
	}

	
}



















