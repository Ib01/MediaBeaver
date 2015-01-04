package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbApi;

import com.ibus.mediabeaver.core.entity.Configuration;
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
	/*static Platform platform;
	static Configuration config;*/
	
	/*public static void initialise(Platform platform, Configuration config)
	{
		Factory.platform = platform;
		Factory.config = config;
	}*/
	
	public static TvdbClient getTvdbClient()
	{
		if(tvdbClient == null)
			tvdbClient = new TvdbClient(AppConfig.TvdbScheme, AppConfig.TvdbHost, AppConfig.TvdbLanguage, AppConfig.TvdbApiKey);
		return tvdbClient;
	}
	
	public static TmdbApi getTmdbClient()
	{
		if(tmdbApi == null)
			tmdbApi = new TmdbApi(AppConfig.TmdbApiKey);
		return tmdbApi;
	}
	
	public static OpenSubtitlesClient getOpenSubtitlesClient()
	{
		if(openSubtitlesClient == null)
			openSubtitlesClient = new OpenSubtitlesClient(AppConfig.OstHost,AppConfig.OstUseragent,AppConfig.OstUserName, AppConfig.OstPassword,AppConfig.OstSublanguageid);
		return openSubtitlesClient;
	}
	
	public static MediaMover getMediaMover(Platform platform, Configuration config)
	{
		return new MediaMover(getEventLogger(platform), config, getTvService(platform, config), getMovieService(platform, config));
	}
	
	public static MovieService getMovieService(Platform platform, Configuration config)
	{
		return  new MovieService(getEventLogger(platform), config, new MoviePathParser(config.getMovieFormatPath()));
	}
	
	public static TvService getTvService(Platform platform, Configuration config)
	{
		return  new TvService(getEventLogger(platform), config, new EpisodePathParser(config.getEpisodeFormatPath()));
	}
	
	public static EventLogger getEventLogger(Platform platform)
	{		
		return new EventLogger(platform);
	}
	
	/*public static Configuration getUserConfiguration()
	{	
		return config;
	}*/
	
	
	/*private static void throwUninitialised()
	{
		if(config == null)
			throw new IllegalArgumentException("The Factory object has not been initialised. Initialise the object before use.");
	}*/
}
