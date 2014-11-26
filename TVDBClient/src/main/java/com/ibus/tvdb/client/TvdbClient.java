package com.ibus.tvdb.client;

import java.net.URI;
import java.net.URISyntaxException;

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
	
	public TvdbClient(String scheme,String host,String language, String apiKey)
	{
		this.scheme = scheme;
		this.host = host;
		this.language = language;
		this.apiKey = apiKey;
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
	public TvdbSeriesListResponseDto getSeries(String seriesName) throws URISyntaxException
	{
		URI uri = getURI("/api/GetSeries.php", "seriesname=" + seriesName + "&language=" + language);
		return doGet(TvdbSeriesListResponseDto.class, uri);
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
