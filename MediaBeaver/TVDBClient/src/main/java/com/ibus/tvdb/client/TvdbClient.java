package com.ibus.tvdb.client;

import com.ibus.service.core.ServiceClient;
import com.ibus.tvdb.client.domain.TvdbSeriesForIdDto;
import com.ibus.tvdb.client.uri.TvdbSeriesAndEpisodesUri;
import com.ibus.tvdb.client.uri.TvdbSeriesUri;

public class TvdbClient
{

	public TvdbSeriesForIdDto getSeriesAndEpisodes()
	{
		TvdbSeriesAndEpisodesUri uri = new TvdbSeriesAndEpisodesUri();
		TvdbSeriesForIdDto dto = ServiceClient.get(TvdbSeriesForIdDto.class, uri);
		return dto;
	}
	
	public TvdbSeriesForIdDto getSeries()
	{
		TvdbSeriesUri uri = new TvdbSeriesUri();
		TvdbSeriesForIdDto dto = ServiceClient.get(TvdbSeriesForIdDto.class, uri);
		return dto;
	}
	
}
