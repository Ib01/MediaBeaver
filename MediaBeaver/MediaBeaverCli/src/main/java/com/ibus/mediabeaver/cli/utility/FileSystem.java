package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;
import com.ibus.mediabeaver.core.exception.MediaBeaverFileSystemException;

public class FileSystem
{
	private Logger log = Logger.getLogger(Main.class.getName());
	
	
	/**
	 * make all dirs in a path which is composed of a root path and a relative path.  if the root path does not exist 
	 * on the file system this operation will fail with an exception.  
	 * @param rootPath
	 * @param relativePath
	 * @return
	 */
	public boolean makeDirsInPath(String rootPath, String relativePath)
	{
		if(!fileExists(rootPath))
		{
			log.error(String.format("Root path does not exist %s", rootPath));
			throw new MediaBeaverConfigurationException(String.format("Root path does not exist %s", rootPath));
		}
		
		//if relative path has a parent dir
		if(FilenameUtils.getPath(relativePath).length() ==  0)
		{
			log.debug(String.format("Creating all dirs in path: %s, is not required. no parent directory was found ", relativePath));
			return true;
		}
		
		String path = FilenameUtils.concat(rootPath, relativePath);
		return makeDirsInPath(path);
	}
	
	
	
	/**
	 * Checks the existance of a file
	 * @param path
	 * @return
	 */
	public boolean fileExists(String path)
	{
		try
		{
			File file = new File(path);
			boolean exists = file.exists(); 
			
			if(exists)
				log.debug(String.format("The following file or folder exists: %s ", path));
			
			return exists; 
		}
		catch(SecurityException ex)
		{
			log.error(String.format("A security violation was thrown when attempting to determine the exitance of: %s.", path), ex);
			throw ex;
		}
		
	}
	
	
	/**
	 * Attempts to make all dirs in path. returns true if successful
	 * @param path
	 * @return
	 */
	public boolean makeDirsInPath(String path)
	{
		try
		{
			/*if file has no path (ie no parent dir) then we will simply return true to indicate that all dirs in path exist which is what we really want to know*/
			if(FilenameUtils.getPath(path).length() >  0)
			{
				File parentDir = new File(path).getParentFile();
				parentDir.mkdirs();
				log.debug(String.format("All directories in the following path were successfully created: %s ", path));	
			}
			else
			{
				log.debug(String.format("Creating all dirs in path: %s, is not required. no parent directory was found ", path));		
			}
			
			return true;
		}
		catch(SecurityException ex)
		{
			log.error(String.format("A security violation was thrown when attempting to make all directories in path: %s.", path), ex);
			throw ex;
		}
	}
	
	
	
	public boolean moveFile(String source, String destinationRootPath, String destinationRelativePath)
	{
		if(!fileExists(destinationRootPath))
		{
			log.error(String.format("Root path does not exist %s", destinationRootPath));
			throw new MediaBeaverConfigurationException(String.format("Root path does not exist %s", destinationRootPath));
		}
		
		String path = FilenameUtils.concat(destinationRootPath, destinationRelativePath);
		
		return moveFile(source, path);
	}
	
	
	
	public boolean moveFile(String source, String destination)
	{
		try
		{
			if(!fileExists(source))
			{
				log.error(String.format("Source file does not exist %s", source));
				throw new MediaBeaverConfigurationException(String.format("Source file does not exist %s", source));
			}
			
			File srcFile = new File(source);
			File destFile = new File(destination);
			FileUtils.moveFile(srcFile, destFile);
			
			log.debug(String.format("%s was succesfully moved to %s", source, destination));
			
			return true;
		}
		catch(IOException ex)
		{
			log.error(String.format("an io exception occured while attempting to move file %s to %s.", source, destination), ex);
			throw new MediaBeaverFileSystemException(String.format("an io exception occured while attempting to move file %s to %s.", source, destination));
		}
	}
	
	public boolean deleteQuietly(String file)
	{
		return FileUtils.deleteQuietly(new File(file));
	}
	
}










































