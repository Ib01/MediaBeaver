package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Configuration")
public class UserConfiguration extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String sourceDirectory;
	
	@Column
	private String tvRootDirectory;
	
	@Column
	private String episodeFormatPath;
	
	@Column
	private String movieRootDirectory;
	
	@Column
	private String movieFormatPath;
	
	@Column
	private String videoExtensionFilter;
	
	//whether we copy a file or move it as part of the default file movement behavior
	@Column
	private boolean copyAsDefault;
	
	
	/**
	 * the source / download directory
	 * @return
	 */
	public String getSourceDirectory()
	{
		return sourceDirectory;
	}

	/**
	 * the source / download directory
	 * @return
	 */
	public void setSourceDirectory(String sourceDirectory)
	{
		this.sourceDirectory = sourceDirectory;
	}

	/**
	 * The root directory under which all tv shows are kept in the users library
	 * @return
	 */
	public String getTvRootDirectory()
	{
		return tvRootDirectory;
	}

	/**
	 * The root directory under which all tv shows are kept in the users library
	 * @return
	 */
	public void setTvRootDirectory(String tvRootDirectory)
	{
		this.tvRootDirectory = tvRootDirectory;
	}

	/**
	 * the format string used to generate the path to the episode. for example: 
	 * {SeriesName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()\Season {SeasonNumber}\{SeriesName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace() S{SeasonNumber}.leftPad("2","0")E{EpisodeNumber}.leftPad("2","0")	
	 * @return
	 */
	public String getEpisodeFormatPath()
	{
		return episodeFormatPath;
	}

	/**
	 * the format path string used to generate the path to the episode. for example: 
	 * {SeriesName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()\Season {SeasonNumber}\{SeriesName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace() S{SeasonNumber}.leftPad("2","0")E{EpisodeNumber}.leftPad("2","0")	
	 * @return
	 */
	public void setEpisodeFormatPath(String tvPath)
	{
		this.episodeFormatPath = tvPath;
	}


	/**
	 * the root directory where movies are placed in the users media library
	 * @param movieRootDirectory
	 */
	public String getMovieRootDirectory()
	{
		return movieRootDirectory;
	}

	/**
	 * the root directory where movies are placed in the users media library
	 * @param movieRootDirectory
	 */
	public void setMovieRootDirectory(String movieRootDirectory)
	{
		this.movieRootDirectory = movieRootDirectory;
	}

	
	/**
	 * the format path string used to generate the path to the movie. example:  
	 * {MovieName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()({ReleaseDate}.substring("0","4"))\{MovieName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()({ReleaseDate}.substring("0","4"))	
	 * @return
	 */
	public String getMovieFormatPath()
	{
		return movieFormatPath;
	}

	/**
	 * the format path string used to generate the path to the movie. example:  
	 * {MovieName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()({ReleaseDate}.substring("0","4"))\{MovieName}.replaceAll("[/\?<>\\:\*\|\"\^]", " ").normalizeSpace()({ReleaseDate}.substring("0","4"))	
	 * @return
	 */
	public void setMovieFormatPath(String moviePath)
	{
		this.movieFormatPath = moviePath;
	}

	
	/**
	 * a comma separated list of video extensions.  only files that have one of these extensions will be considered a video.
	 * @return
	 */
	public String getVideoExtensionFilter()
	{
		return videoExtensionFilter;
	}

	/**
	 * a comma separated list of video extensions.  only files that have one of these extensions will be considered a video.
	 * @return
	 */
	public void setVideoExtensionFilter(String videoExtensionFilter)
	{
		this.videoExtensionFilter = videoExtensionFilter;
	}
	
	/**
	 * sets the default move behavior.  if true then files are copied from one location to another when moved. if set to false titles will be moved 
	 * (i.e copied to a new location and then delete in the previous location)
	 * @return
	 */
	public boolean isCopyAsDefault()
	{
		return copyAsDefault;
	}

	/**
	 * sets the default move behavior.  if true then files are copied from one location to another when moved. if set to false titles will be moved 
	 * (i.e copied to a new location and then delete in the previous location)
	 * @return
	 */
	public void setCopyAsDefault(boolean copyAsDefault)
	{
		this.copyAsDefault = copyAsDefault;
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



















