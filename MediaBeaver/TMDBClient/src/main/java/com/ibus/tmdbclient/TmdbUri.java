package com.ibus.tmdbclient;

import java.net.URI;
import java.net.URISyntaxException;



public abstract class TmdbUri extends ServiceUri 
{
	public static String _scheme;
	public static String _host;
	public static String _apiKey;
	public static final String API = "2.1";
	public static final String LANGUAGE = "en";
	public static final String FORMAT = "xml";
	
	
	@Override
	public abstract URI getURI() throws ServiceSearchException;
	
	protected URI getURI(String pathEnd) throws ServiceSearchException
	{
		String path = String.format("/%s/%s/%s/%s/%s/%s", API, _method, LANGUAGE, FORMAT, _apiKey, pathEnd);
		return getURI(path, _scheme, _host);
	}
	
	

}
