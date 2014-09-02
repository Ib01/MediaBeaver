package com.ibus.tvdb.client.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

public class TvdbClientTests
{
	@Test
	public void getEpisodesTest()
	{
		//e482b9df13cbf32a25570c09174a1d84
		TvdbClient c = new TvdbClient("http", "www.thetvdb.com", "en");
		
		TvdbEpisodesResponseDto dto = c.getEpisodes("121361");
		
		assertTrue(true);
	}
	
	
	@Test
	public void getSeriesTest()
	{
		TvdbClient c = new TvdbClient("http", "www.thetvdb.com", "en");
		TvdbSeriesResponseDto dto = c.getSeries("tt0944947");
		
		assertTrue(true);
	}
}
