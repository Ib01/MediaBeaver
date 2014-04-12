package com.ibus.mediaBeaverCore.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediaBeaverCore.entity.MediaTransformConfig;
import com.ibus.mediaBeaverCore.entity.MediaType;
import com.ibus.mediaBeaverCore.entity.MovieRegEx;
import com.ibus.mediaBeaverCore.entity.RenamingService;
import com.ibus.mediaBeaverCore.entity.TransformAction;

public class DataInitialiser 
{
	public static void Initialise()
	{
		Transaction tx = null; 
		try
		{
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			List<MediaTransformConfig> configs =  Repository.getAllMediaTransformConfig();
			
			//initialie some useful default config only if the db is fresh (i.e this is the first time the app has run)
			if(configs.size() == 0)
			{
				addMovieConfig();
			}
			
			tx.commit();
		}
		catch(RuntimeException e)
		{
			tx.rollback();
			throw e;
		}
	
			
	}
	
	private static void addMovieConfig()
	{
		MovieRegEx re = new MovieRegEx();
		re.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]");
		re.getNameParser().setAssembledItem("{1}");
		re.getNameParser().setRecursiveRegEx("([a-z,A-Z']+)");
		re.getYearParser().setAssembledItem("{2}");
		re.setTestFileName("Iron-Man (1992).mkv");
		
		
		MediaTransformConfig it = new MediaTransformConfig();
		it.setAction(TransformAction.Move);
		it.setDestinationFolder("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Target\\Movies");
		it.setMediaType(MediaType.Movie);
		it.setName("Move Movie Files");
		it.setProcessOrder(1);
		it.setTargetDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Destination\\Movies");
		it.setRenamingService(RenamingService.TMDB);
	}
	
	
	
}























