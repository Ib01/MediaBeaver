package com.ibus.mediabeaver.cli;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.cli.utility.MediaManager;
import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;

public class Main
{
	static Logger log = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args)
	{
		log.debug("initialising data");
		DataInitialiser.Initialise();
		
		
		log.debug("Starting media movement");
		MediaManager h = new MediaManager();
		//h.processConfigs(configs);

		
	}

}
