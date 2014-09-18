package com.ibus.mediabeaver.cli.utility;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.exception.FileExistsException;
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;
import com.ibus.mediabeaver.core.exception.FileNotExistException;
import static java.nio.file.StandardCopyOption.*;

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
	/*public boolean makeDirsInPath(String rootPath, String relativePath)
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
	*/
	/**
	 * Attempts to make all dirs in path. returns true if successful
	 * @param path
	 * @return
	 */
	/*public boolean makeDirsInPath(String path)
	{
		try
		{
			if file has no path (ie no parent dir) then we will simply return true to indicate that all dirs in path exist which is what we really want to know
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
	*/
	
	

	
	/**
	 * Move or rename a file from source to destination. all directories in the destination path will be 
	 * created if they do not already exist. If the file already exists an exception will be thrown
	 * 
	 * @param source
	 * @param destination
	 * @return
	 * @throws MediaBeaverConfigurationException 
	 * @throws IOException 
	 * @throws FileNotExistException 
	 */
	public boolean moveFile(String source, String destination, boolean overWrite) throws IOException 
	{
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(destination);
		
		if(overWrite)
			Files.move(sourcePath, targetPath,REPLACE_EXISTING);
		else
			Files.move(sourcePath, targetPath);
		
		log.debug(String.format(">>> Succesfully renamed or moved file from %s to %s", source, destination));
				
		return true;
	}
	
	
	
	

	/**
	 * Copy a file from source to destination. all directories in the destination path will be 
	 * created if they do not already exist. If the file already exists the move will be 
	 * aborted and the method will return false.
	 * 
	 * @param source
	 * @param destination
	 * @return
	 * @throws MediaBeaverConfigurationException 
	 * @throws IOException 
	 * @throws FileNotExistException 
	 */
	public boolean copyFile(String source, String destination, boolean overWrite) throws IOException 
	{
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(destination);
		
		if(overWrite)
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
		else
			Files.copy(sourcePath, targetPath);
		
		log.debug(String.format(">>> Succesfully copied file from %s to %s", source, destination));
		
		return true;
	}
	
	
	
}










































