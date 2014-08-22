package com.ibus.tmdbclient.uri;

import java.net.URI;
import java.net.URISyntaxException;

import com.ibus.tmdbclient.exception.ServiceSearchException;

public class TmdbThumbprintSearchUri extends TmdbUri
{
	private String _hash;
	private String _bytes;
	
	
	public TmdbThumbprintSearchUri(String hash, String bytes)
	{
		_hash = hash;
		_bytes = bytes;
		setMethod("Media.getInfo");
	}
	
	public URI getURI() throws ServiceSearchException
	{
		String pathEnd = String.format("%s/%s", _hash, _bytes);
		return getURI(pathEnd);
	}
	
	
}
