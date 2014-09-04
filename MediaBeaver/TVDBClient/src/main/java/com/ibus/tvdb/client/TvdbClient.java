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
	private String scheme;
	private String host;
	private String language;
	
	public TvdbClient(String scheme, String host, String language)
	{
		this.scheme= scheme;  
		this.host = host; 
		this.language = language;
	}
	
	public TvdbEpisodesResponseDto getEpisodes(String seriesId) throws URISyntaxException
	{		
		URI uri = getURI("/api/694FAD89942D3827/series/" + seriesId + "/all/" + language + ".xml", null);
		return doGet(TvdbEpisodesResponseDto.class, uri);
	}
	
	public TvdbSeriesResponseDto getSeries(String imdbid) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeriesByRemoteID.php", "imdbid=" + imdbid + "&language=" + language);
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
