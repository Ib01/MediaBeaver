package com.ibus.tvdb.client.integrationtest;

import static org.junit.Assert.assertTrue;
import java.net.URISyntaxException;
import org.junit.Test;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.TvdbEpisodesResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesListResponseDto;
import com.ibus.tvdb.client.domain.TvdbSeriesResponseDto;

public class TvdbClientIT
{
	public static String TVDBScheme=  "http";
	public static String TVDBhost = "www.thetvdb.com";
	public static String TVDBlanguage=  "en";
	//private static String apiKey = "694FAD89942D3827"; file bots key?
	public static String TVDBApiKey = "FA86CE5B6769E616";
	
	
	@Test
	public void getEpisodesTest() throws URISyntaxException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		
		TvdbEpisodesResponseDto dto = c.getEpisodes("121361");
		
		assertTrue(dto != null);
		assertTrue(dto.getEpisodes().get(0) != null);
		assertTrue(dto.getEpisodes().get(0).getSeriesId().length() > 0);
	}
	
	
	@Test
	public void getSeriesByImdbTest() throws URISyntaxException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		TvdbSeriesResponseDto dto = c.getSeriesForImdbId("tt0944947");
		
		assertTrue(true);
	}
	
	@Test
	public void getSeriesByNameTest() throws URISyntaxException
	{
		TvdbClient c = new TvdbClient(TVDBScheme, TVDBhost, TVDBlanguage, TVDBApiKey);
		TvdbSeriesListResponseDto dto = c.getSeries("the killing");
		
		assertTrue(dto.getSeries().size() > 1);
		
		dto = c.getSeries("game of thrones");
		assertTrue(dto.getSeries().size() == 1);
	}
}
