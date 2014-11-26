package com.ibus.mediabeaver.core.util;

import com.ibus.tvdb.client.TvdbClient;;

/*A static container for our services so we only have to initialise once*/
public abstract class Services 
{
	public static TvdbClient tvdbClient = null;
	
	public static TvdbClient getTvdbClient()
	{
		if(tvdbClient == null)
			tvdbClient = new TvdbClient(AppConfig.TVDBScheme, AppConfig.TVDBhost, AppConfig.TVDBlanguage, AppConfig.TVDBApiKey);
		return tvdbClient;
	}
	
	
}
