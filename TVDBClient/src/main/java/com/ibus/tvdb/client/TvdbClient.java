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
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesDto;
import com.ibus.tvdb.client.domain.TvdbSeriesListResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class TvdbClient
{
	protected static Client client = Client.create(); 
	private String scheme;
	private String host;
	private String language;
	private String apiKey;
	private HashMap<Long, List<TvdbBannerDto>> bannersBySeriesId = new HashMap<Long, List<TvdbBannerDto>>(); 
	private HashMap<String, TvdbSeriesDto> seriesByImdb = new HashMap<String, TvdbSeriesDto>();
	private HashMap<String, List<TvdbSeriesDto>> seriesByName = new HashMap<String, List<TvdbSeriesDto>>();  
	private HashMap<Long, List<TvdbEpisodeDto>> episodesBySeriesId = new HashMap<Long, List<TvdbEpisodeDto>>();
	
	public TvdbClient(String scheme,String host,String language, String apiKey)
	{
		this.scheme = scheme;
		this.host = host;
		this.language = language;
		this.apiKey = apiKey;
	}

	/**
	 * By default this object caches all responses from the server since the server can serve alot of data with a single call.  
	 * for eg you can got info about a single episode without getting info on the entire series. Call this to clear the cache
	 */
	public void clearCache()
	{
		bannersBySeriesId.clear(); 
		seriesByImdb.clear();
		seriesByName.clear();  
		episodesBySeriesId.clear();
	}
	
	/**
	 * Get a list of possible series objects for a series name.
	 * 
	 * Example service call: http://www.thetvdb.com/api/GetSeries.php?seriesname=Game Of Thrones
	 * @param seriesName
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public List<TvdbSeriesDto> getSeries(String seriesName) throws TvdbException, TvdbConnectionException 
	{
		if(!seriesByName.containsKey(seriesName))
		{
			try
			{
				URI uri = getURI("/api/GetSeries.php", String.format("seriesname=%s&language=%s", seriesName,language));
				TvdbSeriesListResponseDto response = doGet(TvdbSeriesListResponseDto.class, uri);
			
				if(response == null || response.getSeries() == null)
					return new ArrayList<TvdbSeriesDto>();
				
				seriesByName.put(seriesName, response.getSeries());
			} 
			catch (UniformInterfaceException e)
			{
				//TODO: what response do we get if title is simply not found. this should be handled by returning new ArrayList<TvdbBannerDto>().
				throw new TvdbConnectionException(e);
			}
		}
		
		return seriesByName.get(seriesName);
	}
	
	
	/**
	 * Get all episodes for a series.
	 * 
	 * Example service call: http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/all/en.xml
	 * @param seriesId
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public List<TvdbEpisodeDto> getEpisodes(Long seriesId) throws TvdbException, TvdbConnectionException
	{
		if(!episodesBySeriesId.containsKey(seriesId))
		{
			try
			{
				URI uri = getURI(String.format("/api/%s/series/%d/all/%s.xml", apiKey, seriesId, language), null);
				TvdbEpisodesResponseDto response =  doGet(TvdbEpisodesResponseDto.class, uri);
				
				if(response == null || response.getEpisodes() == null)
					return new ArrayList<TvdbEpisodeDto>();
				
				episodesBySeriesId.put(seriesId, response.getEpisodes());
			} 
			catch (UniformInterfaceException e)
			{
				//TODO: what response do we get if title is simply not found. this should be handled by returning new ArrayList<TvdbBannerDto>().
				throw new TvdbConnectionException(e);
			}
		}
		
		return episodesBySeriesId.get(seriesId);
	}
	
	/**
	 * Get a series for an IMDB Id.
	 * 
	 * @param imdbid
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public TvdbSeriesDto getSeriesForImdbId(String imdbid) throws TvdbException, TvdbConnectionException 
	{
		if(!seriesByImdb.containsKey(imdbid))
		{
			try
			{
				URI uri = getURI("/api/GetSeriesByRemoteID.php", String.format("imdbid=%s&language=%s", imdbid,language));
				TvdbSeriesResponseDto response = doGet(TvdbSeriesResponseDto.class, uri);
				
				//TODO: check actual response for a non existant title
				if(response == null || response.getSeries() == null)
					return null;
				
				seriesByImdb.put(imdbid, response.getSeries());
			} 
			catch (UniformInterfaceException e)
			{
				//TODO: what response do we get if title is simply not found. this should be handled by returning new ArrayList<TvdbBannerDto>().
				throw new TvdbConnectionException(e);
			}
		}
		
		return seriesByImdb.get(imdbid);
	}
	
	
	/**
	 * Get all banners for a series.
	 * 
	 * Example service call: http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml
	 * @param seriesId
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public  List<TvdbBannerDto> getBanners(Long seriesId) throws TvdbException, TvdbConnectionException
	{		
		if(!bannersBySeriesId.containsKey(seriesId))
		{
			try 
			{
				URI uri = getURI(String.format("/api/%s/series/%s/banners.xml", apiKey, seriesId), null);
				TvdbBannersResponseDto response =  doGet(TvdbBannersResponseDto.class, uri);
				
				//TODO: check actual response for a non existant title
				if(response == null || response.getBanners() == null)
					return new ArrayList<TvdbBannerDto>();
				
				bannersBySeriesId.put(seriesId, response.getBanners());
			} 
			catch (UniformInterfaceException e)
			{
				//TODO: what response do we get if title is simply not found. this should be handled by returning new ArrayList<TvdbBannerDto>().
				throw new TvdbConnectionException(e);
			}
		}

		return bannersBySeriesId.get(seriesId);
	}
	
	
	/*TODO:
	 * call http://www.thetvdb.com/api/FA86CE5B6769E616/series/{series Id}/banners.xml to get all banners for series
	 * search for the right banner according to http://thetvdb.com/wiki/index.php?title=API:banners.xml
	 * append BannerPath returned in banners.xml to <mirrorpath>/banners/ to get url for image
	 * 
	 * http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml
	 * http://www.thetvdb.com/banners/seasons/121361-4.jpg
	 * http://www.thetvdb.com/banners/seasonswide/121361-3-3.jpg
	 * */
	
	public TvdbBannerDto getTopSeasonBanner(Long seriesId, int seasonNumber) throws TvdbException, TvdbConnectionException 
	{
		List<TvdbBannerDto> banners = getBanners(seriesId);
		TvdbBannerDto highestRatedBanner = null;
		
		for(TvdbBannerDto banner : banners)
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
	
	
	/*
	 * webResource.get(returnType) will throw a runtime exception if there is a fundamental connection problem of some description, (eg the site is down).
	 * if the title is not found the service will return the parent wrapper object with no content. Wrap and throw fundamental connection errors 
	 * for the client to decide what to do 
	 */
	private static <R, T> R doGet(Class<R> returnType, URI uri) 
	{
		WebResource webResource = client.resource(uri);
		R result = webResource.get(returnType);		//throws UniformInterfaceException
		return result;
	}
	
	private URI getURI(String path, String query) throws TvdbException  
	{
		try
		{
			URI uri = new URI(scheme, null, host, -1, path, query, null);
			return uri;
		} 
		catch (URISyntaxException e) 
		{
			throw new TvdbException(e);
		}
	}
	

}




