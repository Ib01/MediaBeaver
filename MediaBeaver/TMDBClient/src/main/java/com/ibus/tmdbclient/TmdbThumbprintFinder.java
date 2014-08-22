package com.ibus.tmdbclient;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibus.tmdbclient.domain.IServicePoster;

public class TmdbThumbprintFinder implements IServicePoster
{	
	protected Logger _log;
	
	public TmdbThumbprintFinder() 
	{
		_log = Logger.getLogger(TmdbThumbprintFinder.class.getName());
	}
	
	
	/*public List<MediaFile> post(List<MediaFile> files) throws ServiceSearchException 
	{
		_log.info("Searching for media using the TmdbThumbprintFinder");
		
		for(MediaFile f : files)
		{
			TmdbThumbprintSearchUri uri = new TmdbThumbprintSearchUri(
					f.getOpenSubtitlesHash(), f.getBytes());
			
			_log.info("searching for file: " + f.getFileName() 
					+ " with hash" + f.getOpenSubtitlesHash()
					+ " and bytes: " + f.getBytes());
			
			OpenSearchDescription osd = 
					ServiceClient.get(OpenSearchDescription.class, uri);
			
			if(osd != null)
			{
				_log.info("Searching for media using the TmdbFuzzyTextFinder");
				System.out.println("Searching for media using the TmdbFuzzyTextFinder: " + osd.getMovies().size());
				
				//only do something if we can be certain about the movie this file represents
				if(osd.getMovies().size() == 1)
				{
					//clear any pre-existing movies as we are certain about this result 
					f.setMovies(osd.getMovies());
					
					//flag that we are certain about the result
					f.setIsResolved(true);
				}
			}
		}
	
		
		return files;
	}	*/
	
	
	

	

}
