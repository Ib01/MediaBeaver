package com.ibus.mediabeaver.core.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public abstract class AppConfig 
{
	public static String TVDBScheme=  "http";
	public static String TVDBhost = "www.thetvdb.com";
	public static String TVDBlanguage=  "en";
	//private static String apiKey = "694FAD89942D3827"; file bots key?
	public static String TVDBApiKey = "FA86CE5B6769E616";
	
	  
	
	
	
	

	/*public static void createFile()
	{
		Properties prop = new Properties();
		OutputStream output = null;
	 
		try {
	 
			output = new FileOutputStream("config.properties");
	 
			// set the properties value
			prop.setProperty("database", "localhost");
			prop.setProperty("dbuser", "mkyong");
			prop.setProperty("dbpassword", "password");
	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) 
		{
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
	}*/
	
}
