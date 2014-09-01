package com.ibus.tmdb.client.uri;

import java.net.URI;
import java.net.URISyntaxException;

import com.ibus.tmdb.client.exception.ServiceSearchException;


public class TmdbTitleSearchUri extends TmdbUri
{
	private String _title;
	private String _year;
	
	public TmdbTitleSearchUri(String movieTitle)
	{
		_title = movieTitle;
		setMethod("Movie.search");
	}
	
	public TmdbTitleSearchUri(String movieTitle, String year)
	{
		this(movieTitle);
		_year = year;
	}
	
	public URI getURI() throws ServiceSearchException
	{
		return getURI(GetTitleYear(_title, _year));
	}
	
	public static String GetTitleYear(String title, String year)
	{
		String titleYear = title;
		
		if(year != null)
			if(!year.trim().isEmpty())
				titleYear += "+" + year;
		
		return titleYear;
	}
	
	
}
