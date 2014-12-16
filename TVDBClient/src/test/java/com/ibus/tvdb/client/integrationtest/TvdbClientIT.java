package com.ibus.tvdb.client.integrationtest;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.Banner;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.domain.wrapper.EpisodesXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesListXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesXmlWrapper;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

public class TvdbClientIT
{
	public static String TVDBScheme=  "http";
	public static String TVDBhost = "www.thetvdb.com";
	public static String TVDBlanguage=  "en";
	//private static String apiKey = "694FAD89942D3827"; file bots key?
	public static String TVDBApiKey = "FA86CE5B6769E616";
	
	
	@Test
	public void getEpisodesTest() throws TvdbException, TvdbConnectionException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		
		List<Episode> dto = c.getEpisodes(121361L);
		
		assertTrue(dto != null);
		assertTrue(dto.get(0) != null);
		assertTrue(dto.get(0).getSeriesId().length() > 0);
		
		dto = c.getEpisodes(1L);
		assertTrue(dto.size() == 0);
	}
	
	
	@Test
	public void getSeriesByImdbTest() throws TvdbException, TvdbConnectionException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		
		Series dto = c.getSeriesForImdbId("tt0944947");
		assertTrue(dto != null);
		
		dto = c.getSeriesForImdbId("ttxxxxxx");
		assertFalse(dto != null);
	}
	
	@Test
	public void getSeriesByNameTest() throws TvdbException, TvdbConnectionException 
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		List<Series> dto = c.getSeries("the killing");
		assertTrue(dto.size() > 1);
		
		dto = c.getSeries("game of thrones");
		assertTrue(dto.size() == 1);
		
		dto = c.getSeries("xxxbladsfaiuueh");
		assertTrue(dto.size() == 0);
	}
	
	@Test
	public void getBanners() throws TvdbException, TvdbConnectionException 
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		List<Banner> dto = c.getBanners(121361L);
		assertTrue(dto.size() > 1);
		
		dto = c.getBanners(1L);
		assertFalse(dto.size() > 1);
	}
	
	@Test
	public void getTopSeasonBanner() throws TvdbException, TvdbConnectionException 
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		Banner dto = c.getTopSeasonBanner(121361L, 1);
		assertTrue(dto != null);
		assertTrue(dto.getSeason().equals("1"));
		
		dto = c.getTopSeasonBanner(1L, 1);
		assertFalse(dto != null);
	}
	
	
	@Test
	public void getEpisode() throws TvdbException, TvdbConnectionException 
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		Episode dto = c.getEpisode(121361L,1,1);
		assertTrue(dto != null);
		assertTrue(dto.getEpisodeNumber().equals("1"));
		assertTrue(dto.getSeasonNumber().equals("1"));
		
		dto = c.getEpisode(1L,1,1);
		assertFalse(dto != null);
	}
	
	
	
}











