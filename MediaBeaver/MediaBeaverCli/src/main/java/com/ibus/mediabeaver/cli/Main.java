package com.ibus.mediabeaver.cli;


/*
 * TODO: 
 * 
 * 1) ensure that the logger writes to a file and that the location can be configured to something sensible in production.
 * */
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.cli.utility.OpenSubtitlesMediaManager;
import com.ibus.mediabeaver.cli.utility.RegExMediaManager;
import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.QueryTransactable;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;

public class Main
{
	private static String ostUserName = "";
	private static String ostPassword = "";
	private static String ostUseragent = "OS Test User Agent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	
	static Logger log = Logger.getLogger(Main.class.getName());
	public static final String initialiseArg = "-initialise";
	public static final String moveArg = "-move";
	
	public static void main(String[] args) throws XmlRpcException, IOException
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
	
	
	public static void moveMedia() throws XmlRpcException, IOException
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
	
		
		
	}
	
	
	
	
	
	/*private static void moveMediaWithOpenSubtitlesMediaManager(List<MediaConfig> configs) throws XmlRpcException, IOException
	{
		log.debug("Starting media movement using Open Subtitles Services");
		
		OpenSubtitlesClient client = new OpenSubtitlesClient(ostHost,ostUseragent,ostUserName, ostPassword,ostSublanguageid);
		OpenSubtitlesMediaManager mediaManager = new OpenSubtitlesMediaManager(client);
		
		if(!mediaManager.Login()){
			log.debug("Could not move media with Open Subtitles Service. Could not login to the service");
			return;
		}
		
		mediaManager.processConfigs(configs);
		
		if(!mediaManager.Logout()){
			log.debug("An error occured while logging out of the Open Subtitles Service.  you may not be properly logged out of the service");
			return;
		}
	}
	
	private static void moveMediaWithRegExMediaManager(List<MediaConfig> configs) throws XmlRpcException
	{
		log.debug("Starting media movement using regex selectors");
		RegExMediaManager h = new RegExMediaManager();
		h.processConfigs(configs);
	}*/
	
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
