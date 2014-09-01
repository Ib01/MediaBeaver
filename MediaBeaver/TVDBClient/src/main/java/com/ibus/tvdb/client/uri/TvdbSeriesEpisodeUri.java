package com.ibus.tvdb.client.uri;

import java.net.URI;

import com.ibus.service.core.exception.ServiceSearchException;
import com.ibus.service.core.uri.ServiceUri;

public class TvdbSeriesEpisodeUri extends ServiceUri
{
	@Override
	public URI getURI() throws ServiceSearchException
	{
		return getURI("/api/694FAD89942D3827/series/121361/all/en.xml", "http", "www.thetvdb.com");
	}

}
