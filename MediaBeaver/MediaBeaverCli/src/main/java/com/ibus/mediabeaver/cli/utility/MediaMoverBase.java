package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;

public abstract class MediaMoverBase
{
	private static String ostUserName = "";
	private static String ostPassword = "";
	private static String ostUseragent = "OS Test User Agent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	
	protected Logger log = Logger.getLogger(Main.class.getName());
	protected FileSystem fileSys = new FileSystem();
	protected Configuration config;
	OpenSubtitlesClient openSubtitlesClient;
	
	public void moveFiles() throws IOException, XmlRpcException 
	{	
		openSubtitlesClient = new OpenSubtitlesClient(ostHost,ostUseragent,ostUserName, ostPassword,ostSublanguageid);
		
		log.debug("Logging into the Open Subtitles web serivce.");
		if(!openSubtitlesClient.login())
		{
			log.debug("Aborting movement of files. Could not login to the Open Subtitles web serivce.");
			return;
		}
		
		log.debug("Commencing Movement of files");
		iterateGet(new File(config.getSourceDirectory()));
		log.debug("Successfully completed file movement");
		
		openSubtitlesClient.logOut();
	}

	private void iterateGet(File directory)
			throws IOException 
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());

		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				iterateGet(fso);
			} 
			else
			{
				/*try
				{*/
					moveFile(fso);
				/*} catch (IOException e)
				{
					log.error("An exception occured while moving movies", e);
				}*/
			}
		}
	}
	
	
	protected abstract void moveFile(File file);
	
	
}













