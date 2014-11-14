package com.ibus.tvdb.client.integrationtest;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

public class TvdbClientIT
{
	@Test
	public void getEpisodesTest() throws URISyntaxException
	{
		TvdbClient c = new TvdbClient();
		
		TvdbEpisodesResponseDto dto = c.getEpisodes("121361");
		
		assertTrue(dto != null);
		assertTrue(dto.getEpisodes().get(0) != null);
		assertTrue(dto.getEpisodes().get(0).getSeriesId().length() > 0);
	}
	
	
	@Test
	public void getSeriesTest() throws URISyntaxException
	{
		TvdbClient c = new TvdbClient();
		TvdbSeriesResponseDto dto = c.getSeriesForImdbId("tt0944947");
		
		assertTrue(true);
	}
}
