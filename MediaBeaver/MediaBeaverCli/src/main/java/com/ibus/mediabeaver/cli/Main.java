package com.ibus.mediabeaver.cli;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.cli.utility.MediaManager;
import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.QueryTransactable;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.MediaConfig;

public class Main
{
	static Logger log = Logger.getLogger(Main.class.getName());
	public static final String initialiseArg = "-initialise";
	public static final String moveArg = "-move";
	
	public static void main(String[] args)
	{
		if(args[0].equals(initialiseArg))
		{
			initialiseApp();
			return;
		}
		
		if(args[0].equals(moveArg))
		{
			moveMedia();
		}
		
		
		moveMedia();
	}
	
	
	public static void initialiseApp()
	{
		log.debug("Starting initialisation of Media Beaver");
		log.debug("Creating Database Schema");
		HibernateUtil.createSchema();
		
		log.debug("Creating default configuration items");
		Repository.doInTransaction(
				new UpdateTransactable() 
				{
					public void run()
					{
						DataInitialiser.addDefaultConfigs();
					}
				});
		
	}
	
	public static void moveMedia()
	{
		log.debug("Retreiving Media Configuration Items");
		List<MediaConfig> configs = Repository.getInTransaction(
				new QueryTransactable<List<MediaConfig>>() 
				{
					public List<MediaConfig> run()
					{
						return Repository.getAllMediaConfig();
					}
				});
		
		log.debug("Starting media movement");
		MediaManager h = new MediaManager();
		h.processConfigs(configs);
	}

}
