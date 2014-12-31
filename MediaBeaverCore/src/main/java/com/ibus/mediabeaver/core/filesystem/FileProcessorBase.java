package com.ibus.mediabeaver.core.filesystem;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.UserConfiguration;
import com.ibus.mediabeaver.core.entity.Activity;

public abstract class FileProcessorBase
{
	protected UserConfiguration config;		
	protected Logger log = Logger.getLogger(FileProcessorBase.class.getName());
	protected Platform platform;
	public enum Platform{
		Web,
		CLI
	}
	
	/**
	 * processes all files in directory and in all its sub directories
	 * @param config
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	public void processFiles(UserConfiguration config) throws IOException, XmlRpcException 
	{	
		this.config = config;
		beforeProcess();
		processFileTree(new File(config.getSourceDirectory()));
		afterProcess();
	}
	
	private void processFileTree(File directory) throws IOException 
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());
		
		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				processFileTree(fso); 
			} 
			else
			{
				processFile(fso);
			}
		}
	}
	
	/**
	 * process a flat list of files.  directories will not be processed
	 * @param paths
	 * @throws XmlRpcException 
	 * @throws IOException 
	 */
	public void processFiles(UserConfiguration config, List<String> paths) throws XmlRpcException, IOException 
	{ 
		this.config = config;
		beforeProcess();
		
		for(String path : paths)
		{
			File file = new File(path);
			
			if(!file.isDirectory())
				processFile(file);
		}
		
		afterProcess();
	}
	
	
	protected abstract void beforeProcess() throws MalformedURLException, XmlRpcException;
	protected abstract void processFile(File file);
	protected abstract void afterProcess() throws MalformedURLException, IOException, XmlRpcException;
	
	protected void logEvent(final Activity event)
	{

		if(platform == Platform.Web)
		{
			Repository.saveEntity(event);
			return;
		}
		
		//otherwise we are calling from the cli
		Repository.doInTransaction(
			new UpdateTransactable(){
				public void run()
				{
					Repository.saveEntity(event);
				}
			});
	}
	
}
































