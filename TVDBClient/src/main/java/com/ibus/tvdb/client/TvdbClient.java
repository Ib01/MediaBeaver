package com.ibus.tvdb.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibus.tvdb.client.domain.BannerSubtype;
import com.ibus.tvdb.client.domain.BannerType;
import com.ibus.tvdb.client.domain.Banner;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.domain.wrapper.BannersXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.EpisodesXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesListXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesXmlWrapper;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class TvdbClient
{
	private int allowedAttempts = 3;
	private Client client = Client.create(); 
	private String scheme;
	private String host;
	private String language;
	private String apiKey;
	private HashMap<Long, List<Banner>> bannersBySeriesId = new HashMap<Long, List<Banner>>(); 
	private HashMap<String, Series> seriesByImdb = new HashMap<String, Series>();
	private HashMap<String, List<Series>> seriesByName = new HashMap<String, List<Series>>();  
	private HashMap<Long, List<Episode>> episodesBySeriesId = new HashMap<Long, List<Episode>>();
	
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
	public List<Series> getSeries(String seriesName) throws TvdbException, TvdbConnectionException 
	{
		if(!seriesByName.containsKey(seriesName))
		{
			try
			{
				URI uri = getURI("/api/GetSeries.php", String.format("seriesname=%s&language=%s", seriesName,language));
				SeriesListXmlWrapper response = doGet(SeriesListXmlWrapper.class, uri);
			
				if(response == null || response.getSeries() == null || response.getSeries().size() == 0)
					return new ArrayList<Series>();
				
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
	public List<Episode> getEpisodes(Long seriesId) throws TvdbException, TvdbConnectionException
	{
		if(!episodesBySeriesId.containsKey(seriesId))
		{
			try
			{
				URI uri = getURI(String.format("/api/%s/series/%d/all/%s.xml", apiKey, seriesId, language), null);
				EpisodesXmlWrapper response =  doGet(EpisodesXmlWrapper.class, uri);
				
				if(response == null || response.getEpisodes() == null || response.getEpisodes().size() == 0)
					return new ArrayList<Episode>();
				
				episodesBySeriesId.put(seriesId, response.getEpisodes());
			} 
			catch (UniformInterfaceException e)
			{
				//a 404 is a normal part of execution.  simply means resource not found
				if(e.getMessage().endsWith("404 Not Found"))
					return new ArrayList<Episode>(); 
				
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
	public Series getSeriesForImdbId(String imdbid) throws TvdbException, TvdbConnectionException 
	{
		if(!seriesByImdb.containsKey(imdbid))
		{
			try
			{
				URI uri = getURI("/api/GetSeriesByRemoteID.php", String.format("imdbid=%s&language=%s", imdbid,language));
				SeriesXmlWrapper response = doGet(SeriesXmlWrapper.class, uri);
				
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
	public  List<Banner> getBanners(Long seriesId) throws TvdbException, TvdbConnectionException
	{		
		if(!bannersBySeriesId.containsKey(seriesId))
		{
			try 
			{
				URI uri = getURI(String.format("/api/%s/series/%s/banners.xml", apiKey, seriesId), null);
				BannersXmlWrapper response =  doGet(BannersXmlWrapper.class, uri);
				
				//TODO: check actual response for a non existant title
				if(response == null || response.getBanners() == null || response.getBanners().size() == 0)
					return new ArrayList<Banner>();
				
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
	

	
	/**
	 * Gets highest rated season banner. to get banner url append BannerPath returned in banners.xml 
	 * to <mirrorpath>/banners/. See http://thetvdb.com/wiki/index.php?title=API:banners.xml for more info.
	 * 
	 *  test url: http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml
	 * @param seriesId
	 * @param seasonNumber
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public Banner getTopSeasonBanner(Long seriesId, int seasonNumber) throws TvdbException, TvdbConnectionException 
	{
		List<Banner> banners = getBanners(seriesId);
		Banner highestRatedBanner = null;
		String seasonNum = Integer.toString(seasonNumber);
		
		for(Banner banner : banners)
		{
			if(!banner.getBannerType().toLowerCase().equals(BannerType.Season.toString().toLowerCase()))
				continue;
			
			if(!banner.getBannerType2().toLowerCase().equals(BannerSubtype.Seasonwide.toString().toLowerCase()))
				continue;
			
			if(!banner.getSeason().equals(seasonNum))
				continue;
					
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
		
		return highestRatedBanner;
	}
	
	
	/**
	 * Gets the first episode that belongs to the series corresponding to seriesId and 
	 * has the specified season number and episode number  
	 * @param seriesId
	 * @param seasonNumber
	 * @param episodeNumber
	 * @return
	 * @throws TvdbException
	 * @throws TvdbConnectionException
	 */
	public Episode getEpisode(Long seriesId, int seasonNumber, int episodeNumber) throws TvdbException, TvdbConnectionException
	{
		List<Episode> episodes = getEpisodes(seriesId);
		String seasonNum = Integer.toString(seasonNumber);
		String episodeNum = Integer.toString(episodeNumber);
		
		for(Episode episode : episodes)
		{
			if(episode.getSeasonNumber().equals(seasonNum) && episode.getEpisodeNumber().equals(episodeNum))
			{
				return episode;
			}
		}
		
		return null;
	}
	
	
	
	/*
	 * webResource.get(returnType) will throw a runtime exception if there is a fundamental connection problem of some description, (eg the site is down).
	 * if the title is not found the service will return the parent wrapper object with no content. Wrap and throw fundamental connection errors 
	 * for the client to decide what to do 
	 */
	private <R, T> R doGet(Class<R> returnType, URI uri) 
	{
		WebResource webResource = client.resource(uri);
		int attempts = 0;
		
		while(true)
		{
			try
			{
				R result = webResource.get(returnType);		//throws UniformInterfaceException
				return result;
			}
			catch(Throwable e)
			{
				if(++attempts >= allowedAttempts)
					throw e;
			}
		}
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




