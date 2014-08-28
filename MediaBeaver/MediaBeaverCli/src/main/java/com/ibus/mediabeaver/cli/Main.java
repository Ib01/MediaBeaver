package com.ibus.mediabeaver.cli;


/*
 * TODO: 
 * 
 * 1) ensure that the logger writes to a file and that the location can be configured to something sensible in production.
 * */
import java.io.InputStreamReader;
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
	/*private static String userName = "";
	private static String password = "";
	private static String useragent = "OS Test User Agent";
	private static String host = "http://api.opensubtitles.org/xml-rpc";
	private static String sublanguageid = "eng";
	private OpenSubtitlesToken token = new OpenSubtitlesToken();*/
	
	static Logger log = Logger.getLogger(Main.class.getName());
	public static final String initialiseArg = "-initialise";
	public static final String moveArg = "-move";
	
	public static void main(String[] args)
	{
		//new BufferedReader(new InputStreamReader(getResourceAsStream("/resources/" + filename)))
		
		
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
