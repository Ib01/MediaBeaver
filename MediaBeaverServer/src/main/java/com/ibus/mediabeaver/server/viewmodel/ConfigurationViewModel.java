package com.ibus.mediabeaver.server.viewmodel;

import com.ibus.mediabeaver.server.validation.EnvironmentPath;
import com.ibus.mediabeaver.server.validation.PathExists;
import com.ibus.mediabeaver.server.validation.PathsExists;

/*@PathExists(message="This path does not exist under the Movie root directory", rootPathField = "movieRootDirectory", pathField = "moviePath")*/
//@PathExists(message="This path does not exist under the TV root directory", rootPathField = "tvRootDirectory", pathField = "episodePath")
//@PathExists(message="This path does not exist under the TV root directory", pathComponents={"tvRootDirectory"}, ownerField = "tvRootDirectory")
/*@PathsExists(paths ={ 
		@PathExists(message="This path does not exist under the TV root directory", pathComponents={"tvRootDirectory"}, ownerField = "tvRootDirectory") 
})*/
@PathExists(message="This path does not exist under the TV root directory", pathComponents={"tvRootDirectory"}, ownerField = "tvRootDirectory")
public class ConfigurationViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;

	@EnvironmentPath(message="This path contains invalid path seperators")
	private String sourceDirectory;
	
	@EnvironmentPath(message="This path contains invalid path seperators")
	private String tvRootDirectory;
	private String episodePath;
	
	@EnvironmentPath(message="This path contains invalid path seperators")
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



















