package com.ibus.tmdb.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.ibus.service.core.ServiceClient;
import com.ibus.service.core.exception.ServiceSearchException;
import com.ibus.tmdb.client.domain.OpenSearchDescription;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;
import com.ibus.tvdb.client.uri.TvdbSeriesAndEpisodesUri;
import com.ibus.tvdb.client.uri.TvdbSeriesUri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TmdbClient
{
	protected static Client client = Client.create(); 
	private String scheme;
	private String host;
	private String language;
	
	public TmdbClient(String scheme, String host, String language)
	{
		this.scheme= scheme;  
		this.host = host; 
		this.language = language;
	}
	
	public OpenSearchDescription getEpisodes(String seriesId)
	{		
		URI uri = getURI("/api/694FAD89942D3827/series/" + seriesId + "/all/" + language + ".xml", null);
		return doGet(OpenSearchDescription.class, uri);
	}
	
/*	public TvdbSeriesResponseDto getSeries(String imdbid)
	{
		URI uri = getURI("/api/GetSeriesByRemoteID.php", "imdbid=" + imdbid + "&language=" + language);
		return doGet(TvdbSeriesResponseDto.class, uri);
	}*/
	
	
	
	private static <R, T> R doGet(Class<R> returnType, URI uri)
	{
		//TODO: WRAP IN EXCEPTION BLOCK?
		try 
		{
			WebResource webResource = client.resource(uri);
			R result = webResource.get(returnType);
			
			return result;
		}
		catch (Exception ex)
		{
			//do we want client to terminate because it couldnt get resource? 
		}
		
		return null;
	}
	
	
	
	
	private URI getURI(String path, String query) 
	{
		try 
		{
			URI uri = new URI(scheme, null, host, -1, path, query, null);
			return uri;
			
		} catch (URISyntaxException e) 
		{
			throw new ServiceSearchException("The Uri used to communicate with the service is not well formed", e);
		}
	}
	
	
	
}
