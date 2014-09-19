package com.ibus.mediabeaver.core.integrationtest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EnvironmentVariablesIT
{
	static Logger log = Logger.getLogger(EnvironmentVariablesIT.class.getName());
	
	@BeforeClass
	public static void initialiseClass()
	{
	}

	@Before
	public void beforeTest()
	{
	}


	@Test
	public void initialiseDatabase()
	{
		 Map<String, String> env = System.getenv();
	        for (String envName : env.keySet()) 
	        {
	        	log.debug(String.format("%s=%s%n",envName,env.get(envName)));
	        }

		assert(true);
	}
	
	
	
	
	
	

}












