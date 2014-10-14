package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.Event;
import com.ibus.mediabeaver.core.util.FileSystem;

public abstract class FileProcessorBase
{
	protected Configuration config;		
	protected Logger log = Logger.getLogger(Main.class.getName());
	protected FileSystem fileSys = new FileSystem();
	
	
	public void processFiles(Configuration config) throws IOException, XmlRpcException 
	{	
		this.config = config;
		beforeProcess();
		processFiles(new File(config.getSourceDirectory()));
		afterProcess();
	}

	private void processFiles(File directory)
			throws IOException 
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());

		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				processFiles(fso); 
			} 
			else
			{
				processFile(fso);
			}
		}
	}
	
	
	protected abstract void beforeProcess() throws MalformedURLException, XmlRpcException;
	protected abstract void processFile(File file);
	protected abstract void afterProcess() throws MalformedURLException, IOException, XmlRpcException;
	
	protected void logEvent(final Event event)
	{
		Repository.doInTransaction(
			new UpdateTransactable(){
				public void run()
				{
					Repository.saveEntity(event);
				}
			});
	}
	
}













