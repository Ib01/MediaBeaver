package com.ibus.tvdb.client;

import com.ibus.service.core.ServiceClient;
import com.ibus.tvdb.client.domain.SeriesDetailedDto;
import com.ibus.tvdb.client.uri.TvdbSeriesEpisodeUri;

public class TvdbClient
{

	public SeriesDetailedDto getEpisodes()
	{
		SeriesDetailedDto dto = ServiceClient.get(SeriesDetailedDto.class, new TvdbSeriesEpisodeUri());
		return dto;
	}
	
}
