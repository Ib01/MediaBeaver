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
	
	
	
	
	public void directoriesInPathExist(String root, String path)  
	{
		
		/*
		
		if dir exists in same casing 
			move to that dir
		
		(dir)
		{
			if(Files.exist(dir))
			{
				//if linux it will be in right casing. not necessarily true under windows
				d = getDir()
				if(d has different casing)
					return false;
			}
			return true;
		}
		
		cases
		
		windows 
				- exists with wrong case = false
				- exists with right case = true
		linux
				- exists with wrong case = false
				- exists with right case = true
		
		-------------------------------------------------
		
		if dir exists in different casing
			throw
			
		(dir)
		{
			//case insensitive file systems 
			if(Files.exist(dir))
			{
				//if linux it will be in right casing. not necessarily true under windows
				d = getDir()
				if(d has different casing)
					return true;
					
				return false;
			}
			
			//case sensitive file systems
			parentDir = getParentDir(dir)
			
			foreach dir in parentDir
			{
				if dir.equalsIgnoreCase(dir)
					return true;
			}
			
			return false;
		}
			
		windows 
				- exists with wrong case = true
				- exists with right case = false
		linux
				- exists with wrong case = true
				- exists with right case = true
		
		
		-------------------------------------------------
		
		if dir not exist 
			create it and all dirs in path
		
		
		 
		
		 
		 
				 
		 Ab/cD/e.txt
		 
		 ab/cD/e.txt
		 Ab/cd/e.txt
		 Ab/cD/E.txt
		 
		 */
		
	}
	
	
	public boolean directoryExistWithAlternateCasing(String directory)  
	{
		Path sourcePath = Paths.get(directory);	
		
		//if true: we have case sensitive fs and we have a dir with exact casing 
		//OR we have case insensitive fs and dir that may have alternate casing   
		if(Files.exists(sourcePath))
		{
			File file = sourcePath.toFile();
			
			if(!file.getAbsolutePath().equals(directory))
				return true;
				
			return false;
		}
			
		/*Path parentPath = sourcePath.getParent();
		
		Files.w
		
		
		
			//case sensitive file systems
			parentDir = getParentDir(dir)
			
			foreach dir in parentDir
			{
				if dir.equalsIgnoreCase(dir)
					return true;
			}
			*/
			return false;
		
	}
	
	
	
	
	

	
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










































