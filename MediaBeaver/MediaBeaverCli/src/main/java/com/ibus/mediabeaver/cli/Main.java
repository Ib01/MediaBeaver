package com.ibus.mediabeaver.cli;

import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.utility.MediaManager;
import com.ibus.mediabeaver.core.data.DataInitialiser;

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
