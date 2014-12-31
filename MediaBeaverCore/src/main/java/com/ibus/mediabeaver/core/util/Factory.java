package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbApi;

import com.ibus.mediabeaver.core.entity.UserConfiguration;
import com.ibus.mediabeaver.core.filesystem.EpisodePathParser;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MoviePathParser;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.tvdb.client.TvdbClient;

public abstract class Factory 
{
	static TvdbClient tvdbClient = null; 	//keep a handle on the service clients since they can be expensive to recreate / initialise 
	static TmdbApi tmdbApi = null;			
	static OpenSubtitlesClient openSubtitlesClient;
	static Platform platform;
	static UserConfiguration config;
	
	public static void initialise(Platform platform, UserConfiguration config)
	{
		Factory.platform = platform;
		Factory.config = config;
	}
	
	public static TvdbClient getTvdbClient()
	{
		throwUninitialised();
		if(tvdbClient == null)
			tvdbClient = new TvdbClient(AppConfig.TvdbScheme, AppConfig.TvdbHost, AppConfig.TvdbLanguage, AppConfig.TvdbApiKey);
		return tvdbClient;
	}
	
	public static TmdbApi getTmdbClient()
	{
		throwUninitialised();
		if(tmdbApi == null)
			tmdbApi = new TmdbApi(AppConfig.TmdbApiKey);
		return tmdbApi;
	}
	
	public static OpenSubtitlesClient getOpenSubtitlesClient()
	{
		throwUninitialised();
		if(openSubtitlesClient == null)
			openSubtitlesClient = new OpenSubtitlesClient(AppConfig.OstHost,AppConfig.OstUseragent,AppConfig.OstUserName, AppConfig.OstPassword,AppConfig.OstSublanguageid);
		return openSubtitlesClient;
	}
	
	public MediaMover getMediaMover()
	{
		throwUninitialised();
		return new MediaMover(getTvService(), getMovieService());
	}
	
	public static MovieService getMovieService()
	{
		throwUninitialised();
		return  new MovieService(getEventLogger(), config, new MoviePathParser(config.getMovieFormatPath()));
	}
	
	public static TvService getTvService()
	{
		throwUninitialised();
		return  new TvService(getEventLogger(), config, new EpisodePathParser(config.getEpisodeFormatPath()));
	}
	
	public static EventLogger getEventLogger()
	{
		throwUninitialised();
		return new EventLogger(platform);
	}
	
	public static UserConfiguration getUserConfiguration()
	{
		throwUninitialised();
		return config;
	}
	
	
	private static void throwUninitialised()
	{
		if(config == null)
			throw new IllegalArgumentException("The Factory object has not been initialised. Initialise the object before use.");
	}
}
