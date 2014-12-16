package com.ibus.tvdb.client.integrationtest;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodeDto;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesDto;
import com.ibus.tvdb.client.domain.TvdbSeriesListResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;
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
		
		List<TvdbEpisodeDto> dto = c.getEpisodes(121361L);
		
		assertTrue(dto != null);
		assertTrue(dto.get(0) != null);
		assertTrue(dto.get(0).getSeriesId().length() > 0);
	}
	
	
	@Test
	public void getSeriesByImdbTest() throws TvdbException, TvdbConnectionException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		@SuppressWarnings("unused")
		TvdbSeriesDto dto = c.getSeriesForImdbId("tt0944947");
		
		assertTrue(true);
	}
	
	@Test
	public void getSeriesByNameTest() throws TvdbException, TvdbConnectionException 
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		List<TvdbSeriesDto> dto = c.getSeries("the killing");
		
		assertTrue(dto.size() > 1);
		
		dto = c.getSeries("game of thrones");
		assertTrue(dto.size() == 1);
	}
}
