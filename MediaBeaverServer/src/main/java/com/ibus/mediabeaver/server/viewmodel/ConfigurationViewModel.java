package com.ibus.mediabeaver.server.viewmodel;

import com.ibus.mediabeaver.server.validation.EnvironmentPath;
import com.ibus.mediabeaver.server.validation.MultipartPathExists;
import com.ibus.mediabeaver.server.validation.PathExists;

/*@MultipartPathExists.List({
		@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"tvRootDirectory"}, ownerField = "tvRootDirectory"),
		@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"movieRootDirectory"}, ownerField = "movieRootDirectory"),
		@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"sourceDirectory"}, ownerField = "sourceDirectory")
})*/
public class ConfigurationViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;

	/*@EnvironmentPath(message="This path contains invalid path seperators")*/
	@PathExists(message="This directory does not exist on the file system")
	private String sourceDirectory;

	/*@EnvironmentPath(message="This path contains invalid path seperators")*/
	@PathExists(message="This directory does not exist on the file system")
	private String tvRootDirectory;
	private String episodePath;
	
	/*@EnvironmentPath(message="This path contains invalid path seperators")*/
	@PathExists(message="This directory does not exist on the file system")
	private String movieRootDirectory;
	private String moviePath;
	
	private String videoExtensionFilter;
	private boolean copyAsDefault;
	
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

	public boolean isCopyAsDefault()
	{
		return copyAsDefault;
	}

	public void setCopyAsDefault(boolean copyAsDefault)
	{
		this.copyAsDefault = copyAsDefault;
	}

	
}



















