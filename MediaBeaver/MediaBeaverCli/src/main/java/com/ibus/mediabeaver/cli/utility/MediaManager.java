package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.entity.MediaConfig;

public abstract class MediaManager
{
	protected Logger log = Logger.getLogger(Main.class.getName());
	protected FileSystem fileSys = new FileSystem();
	
	public void processConfigs(List<MediaConfig> configs) throws XmlRpcException
	{
		for (MediaConfig c : configs)
		{
			log.debug(String.format("processing config with description: %s", c.getDescription()));
			
			try
			{
				iterateGet(new File(c.getSourceDirectory()), c);
			} catch (IOException e)
			{
				log.error("An exception occured while moving movies", e);
			}
		}

	}

	private void iterateGet(File directory, MediaConfig config)
			throws IOException, XmlRpcException
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());

		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				iterateGet(fso, config);
			} 
			else
			{
				processFile(fso, config);
			}
		}
	}
	
	protected abstract void processFile(File file, MediaConfig config) throws IOException, XmlRpcException;
	
	
}













