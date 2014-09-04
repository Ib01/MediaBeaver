package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.MediaConfig2;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;

public abstract class MediaManagerBase2
{
	private static String ostUserName = "";
	private static String ostPassword = "";
	private static String ostUseragent = "OS Test User Agent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	
	protected Logger log = Logger.getLogger(Main.class.getName());
	protected FileSystem fileSys = new FileSystem();
	protected MediaConfig2 config;
	OpenSubtitlesClient openSubtitlesClient;
	
	public void moveFiles() throws IOException, XmlRpcException 
	{	
		openSubtitlesClient = new OpenSubtitlesClient(ostHost,ostUseragent,ostUserName, ostPassword,ostSublanguageid);
		
		openSubtitlesClient.login();
		iterateGet(new File(config.getSourceDirectory()));
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
					processFile(fso);
				/*} catch (IOException e)
				{
					log.error("An exception occured while moving movies", e);
				}*/
			}
		}
	}
	
	protected abstract void processFile(File file);
	
	
}













