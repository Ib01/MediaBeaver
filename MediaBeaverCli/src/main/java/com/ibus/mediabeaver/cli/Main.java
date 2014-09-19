package com.ibus.mediabeaver.cli;


/*
 * TODO: 
 * 
 * 1) ensure that the logger writes to a file and that the location can be configured to something sensible in production.
 * */
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.cli.utility.MediaMover;
import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.QueryTransactable;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Configuration;

public class Main
{    
	static Logger log = Logger.getLogger(Main.class.getName());
	public static final String initialiseArg = "-initialise";
	public static final String moveArg = "-move";
	
	public static void main(String[] args) throws XmlRpcException, IOException
	{
		
		if(args[0].equals(initialiseArg))
		{
			initialiseApp();
			return;  
		}
		
		if(args[0].equals(moveArg))
		{
			moveMedia();
			return; 
		}
		
		showUsage();
	}
	
	
	public static void moveMedia() throws XmlRpcException, IOException
	{
		log.debug("Retreiving Media Configuration Items");
	
		Configuration config = Repository.getInTransaction(
				new QueryTransactable<Configuration>() 
				{
					public Configuration run()
					{
						return Repository.getFirstEntity(Configuration.class);
					}
				});
	
		MediaMover mm = new MediaMover();
		mm.processFiles(config);
	}
	
	
	public static void initialiseApp()
	{
		log.debug("Starting initialisation of Media Beaver");
		log.debug("Creating Database Schema");
		HibernateUtil.createSchema();
		
		log.debug("Commencing creation of default configuration items");
		Repository.doInTransaction(
				new UpdateTransactable() 
				{
					public void run()
					{
						DataInitialiser.addDefaultConfigs();
					}
				});
		log.debug("Default configuration items created");
		
	}
	
	public static void showUsage()
	{
		System.out.println("");
		System.out.println("Usage: ");
		System.out.println("");
		System.out.println("MediaBeaver "+initialiseArg);
		System.out.println("    Initialise the Media Beaver application.  This command must be run before the application is used.");
		System.out.println("MediaBeaver "+ moveArg);
		System.out.println("    Move content around according to the applications configuration.");
		System.out.println("");
	}

}
