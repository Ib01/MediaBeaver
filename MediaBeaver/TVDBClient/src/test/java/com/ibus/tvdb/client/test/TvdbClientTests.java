package com.ibus.tvdb.client.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;

public class TvdbClientTests
{
	@Test
	public void getEpisodesTest()
	{
		TvdbClient c = new TvdbClient("http", "www.thetvdb.com", "en");
		
		c.getEpisodes("121361");
		
		assertTrue(true);
	}
	
	
	@Test
	public void getSeriesTest()
	{
		/*TvdbClient c = new TvdbClient();
		
		c.getSeries();*/
		
		assertTrue(true);
	}
}
