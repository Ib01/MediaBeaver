package com.ibus.mediabeaver.server.viewmodel;

import com.ibus.mediabeaver.core.entity.ConfigurationBehaviour;
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
	private String episodeFormatPath;
	
	/*@EnvironmentPath(message="This path contains invalid path seperators")*/
	@PathExists(message="This directory does not exist on the file system")
	private String movieRootDirectory;
	private String movieFormatPath;
	
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

	public String getEpisodeFormatPath()
	{
		return episodeFormatPath;
	}

	public void setEpisodeFormatPath(String tvPath)
	{
		this.episodeFormatPath = tvPath;
	}

	public String getMovieRootDirectory()
	{
		return movieRootDirectory;
	}

	public void setMovieRootDirectory(String movieRootDirectory)
	{
		this.movieRootDirectory = movieRootDirectory;
	}

	public String getMovieFormatPath()
	{
		return movieFormatPath;
	}

	public void setMovieFormatPath(String moviePath)
	{
		this.movieFormatPath = moviePath;
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
	
	public boolean isVideoExtension(String extension)
	{
		return ConfigurationBehaviour.isVideoExtension(extension, videoExtensionFilter);
	}

	
}



















