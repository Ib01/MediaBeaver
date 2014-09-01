package com.ibus.tvdb.client.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;

public class TvdbClientTests
{
	@Test
	public void getEpisodesTest()
	{
		TvdbClient c = new TvdbClient();
		
		c.getSeriesAndEpisodes();
		
		assertTrue(true);
	}
	
	
	@Test
	public void getSeriesTest()
	{
		TvdbClient c = new TvdbClient();
		
		c.getSeries();
		
		assertTrue(true);
	}
}
