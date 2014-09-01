package com.ibus.tvdb.client.uri;

import java.net.URI;

import com.ibus.service.core.exception.ServiceSearchException;
import com.ibus.service.core.uri.ServiceUri;

public class TvdbSeriesUri extends ServiceUri
{
	@Override
	public URI getURI() throws ServiceSearchException
	{
		return getURI("/api/GetSeriesByRemoteID.php", "http", "www.thetvdb.com", "imdbid=tt0944947&language=en");
	}

}
