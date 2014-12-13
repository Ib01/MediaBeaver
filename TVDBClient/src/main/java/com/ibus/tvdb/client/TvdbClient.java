package com.ibus.tvdb.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibus.tvdb.client.domain.BannerSubtype;
import com.ibus.tvdb.client.domain.BannerType;
import com.ibus.tvdb.client.domain.TvdbBannerDto;
import com.ibus.tvdb.client.domain.TvdbBannersResponseDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesListResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TvdbClient
{
	protected static Client client = Client.create(); 
	private String scheme;
	private String host;
	private String language;
	private String apiKey;
	private HashMap<Long, TvdbBannersResponseDto> cachedBanners = new HashMap<Long, TvdbBannersResponseDto>();
	private HashMap<String, TvdbSeriesResponseDto> cachedSeries = new HashMap<String, TvdbSeriesResponseDto>();
	private HashMap<String, TvdbEpisodesResponseDto> cachedEpisodes = new HashMap<String, TvdbEpisodesResponseDto>();
	
	public TvdbClient(String scheme,String host,String language, String apiKey)
	{
		this.scheme = scheme;
		this.host = host;
		this.language = language;
		this.apiKey = apiKey;
	}

	//http://www.thetvdb.com/api/GetSeries.php?seriesname=Game Of Thrones
	public TvdbSeriesListResponseDto getSeries(String seriesName) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeries.php", String.format("seriesname=%s&language=%s", seriesName,language));
		//URI uri = getURI("/api/GetSeries.php", "seriesname=" + seriesName + "&language=" + language);
		return doGet(TvdbSeriesListResponseDto.class, uri);
	}
		
	
	//http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/all/en.xml
	public TvdbEpisodesResponseDto getEpisodes(Long seriesId) throws URISyntaxException
	{	
		URI uri = getURI(String.format("/api/%s/series/%d/all/%s.xml", apiKey, seriesId, language), null);
		//URI uri = getURI("/api/" + apiKey + "/series/" + Long.toString(seriesId) + "/all/" + language + ".xml", null);
		return doGet(TvdbEpisodesResponseDto.class, uri);
	}
	
	public TvdbSeriesResponseDto getSeriesForImdbId(String imdbid) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeriesByRemoteID.php", String.format("imdbid=%s&language=%s", imdbid,language));
		//URI uri = getURI("/api/GetSeriesByRemoteID.php", "imdbid=" + imdbid + "&language=" + language);
		return doGet(TvdbSeriesResponseDto.class, uri);
	}
	
	//http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml
	public TvdbBannersResponseDto getBanners(Long seriesId) throws URISyntaxException
	{		
		if(!cachedBanners.containsKey(seriesId))
		{
			URI uri = getURI(String.format("/api/%s/series/%s/banners.xml", apiKey, seriesId), null);
			//URI uri = getURI("/api/" + apiKey + "/series/" + seriesId + "/banners.xml", null);
			TvdbBannersResponseDto response =  doGet(TvdbBannersResponseDto.class, uri);
			cachedBanners.put(seriesId, response);
		}

		return cachedBanners.get(seriesId);
	}
	
	
	public TvdbBannerDto getTopSeasonBanner(Long seriesId, int seasonNumber) throws URISyntaxException
	{
		TvdbBannersResponseDto response = getBanners(seriesId);
		TvdbBannerDto highestRatedBanner = null;
		
		for(TvdbBannerDto banner : response.getBanners())
		{
			if(banner.getBannerType().equals(BannerType.Season.toString()))
			{
				if(banner.getBannerType2().equals(BannerSubtype.Seasonwide))
				{
					if(highestRatedBanner == null){
						highestRatedBanner = banner;
						continue;
					}
					
					Float lastRating;
					try
					{
						lastRating = Float.parseFloat(highestRatedBanner.getRating());
					}
					catch(NullPointerException | NumberFormatException ex)
					{
						highestRatedBanner = banner;
						continue;
					}
					
					try
					{
						Float rating = Float.parseFloat(banner.getRating());
						
						if(rating > lastRating)
						{
							highestRatedBanner = banner;
						}
					}
					catch(NullPointerException | NumberFormatException ex)
					{/*do nothing.last banner was a higher rated banner*/}
				}
			}
		}
		
		return highestRatedBanner;
	}
	
	
	
	
	
	
	
	
	
	
	/*TODO:
	 * 
	 * get banners
	 * 
	 * PROCESS.
	 * 
	 * call http://www.thetvdb.com/api/FA86CE5B6769E616/series/{series Id}/banners.xml to get all banners for series
	 * 
	 * search for the right banner according to http://thetvdb.com/wiki/index.php?title=API:banners.xml
	 * 
	 * append BannerPath returned in banners.xml to <mirrorpath>/banners/ to get url for image
	 * 
	 * 
	 * http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml
	 * http://www.thetvdb.com/banners/seasons/121361-4.jpg
	 * 
	 * http://www.thetvdb.com/banners/seasonswide/121361-3-3.jpg
	 * */
	
	
	private static <R, T> R doGet(Class<R> returnType, URI uri)
	{
		WebResource webResource = client.resource(uri);
		R result = webResource.get(returnType);
		
		return result;
	}
	
	private URI getURI(String path, String query) throws URISyntaxException 
	{
		URI uri = new URI(scheme, null, host, -1, path, query, null);
		return uri;
	
	}
	

}
