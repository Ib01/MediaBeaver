package com.ibus.tvdb.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TvdbClient
{
	protected static Client client = Client.create(); 
	private String scheme=  "http";
	private String host = "www.thetvdb.com";
	private String language=  "en";
	//private static String apiKey = "694FAD89942D3827"; file bots key?
	private static String apiKey = "FA86CE5B6769E616";
	
	public TvdbClient()
	{
	}
	
	public TvdbEpisodesResponseDto getEpisodes(String seriesId) throws URISyntaxException
	{		
		URI uri = getURI("/api/" + apiKey + "/series/" + seriesId + "/all/" + language + ".xml", null);
		return doGet(TvdbEpisodesResponseDto.class, uri);
	}
	
	public TvdbSeriesResponseDto getSeriesForImdbId(String imdbid) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeriesByRemoteID.php", "imdbid=" + imdbid + "&language=" + language);
		return doGet(TvdbSeriesResponseDto.class, uri);
	}
	
	//http://www.thetvdb.com/api/GetSeries.php?seriesname=Game Of Thrones
	public TvdbSeriesResponseDto getSeries(String seriesName) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeries.php", "seriesname=" + seriesName + "&language=" + language);
		return doGet(TvdbSeriesResponseDto.class, uri);
	}
	
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
