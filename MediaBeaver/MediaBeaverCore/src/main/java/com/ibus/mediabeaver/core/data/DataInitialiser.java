package com.ibus.mediabeaver.core.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.core.entity.MediaConfig;

public class DataInitialiser 
{
	static Logger log = Logger.getLogger(DataInitialiser.class.getName());
	
	public static void Initialise(List<MediaConfig> configs)
	{
		if(configs.size() == 0)
		{
			//addMovieConfig();
		}
		
		/*Transaction tx = null; 
		try
		{
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			log.debug("Getting configuration from h2 db");
			
			List<MediaConfig> configs =  Repository.getAllMediaConfig();
			
			log.debug(String.format("Retreived %d configs from db", configs.size()));
			
			//initialise some useful default config only if the db is fresh (i.e this is the first time the app has run)
			if(configs.size() == 0)
			{
				//addMovieConfig();
			}
			
			tx.commit();
		}
		catch(RuntimeException e)
		{
			tx.rollback();
			throw e;
		}	
		 */			
	}
	
	/*private static void addMovieConfig()
	{
		RegExSelector re = new RegExSelector();
		re.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]");
		re.getNameParser().setAssembledItem("{1}");
		re.getNameParser().setCleaningRegEx("([a-z,A-Z']+)");
		re.getYearParser().setAssembledItem("{2}");
		re.setTestFileName("Iron-Man (1992).mkv");
		
		
		MediaConfig it = new MediaTransformConfig();
		it.setAction(TransformAction.Move);
		it.setTargetDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Target");
		it.setMediaType(MediaType.Movie);
		it.setName("Move Movie Files");
		it.setProcessOrder(1);
		it.setDestinationFolder("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Destination\\Movies");
		it.setRenamingService(RenamingService.TMDB);
		it.addSelectExpression(re);
		
		
		Repository.addEntity(it);
		
		log.debug("added 1 config to db");
	}
	*/
	
	
}























