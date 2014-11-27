package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbApi;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.tvdb.client.TvdbClient;;

/*A static container for our services so we only have to initialise once*/
public abstract class Services 
{
	static TvdbClient tvdbClient = null;
	static TmdbApi tmdbApi = null;
	static OpenSubtitlesClient openSubtitlesClient;
	
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
}
