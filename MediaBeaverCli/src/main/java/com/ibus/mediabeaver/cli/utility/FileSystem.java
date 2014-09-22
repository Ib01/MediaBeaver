package com.ibus.mediabeaver.cli.utility;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.FileSystemException;
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;

public class FileSystem
{
	private Logger log = Logger.getLogger(Main.class.getName());
	HashMap<String, List<String>> cachedPaths = new HashMap<String, List<String>>();

	
	/**
	 * Move or rename a file from source to destination. all directories in the destination path will be 
	 * created if they do not already exist. If the file already exists an exception will be thrown
	 * 
	 * @param source
	 * @param destination
	 * @return
	 * @throws MediaBeaverConfigurationException 
	 * @throws IOException 
	 * @throws DuplicateFileException 
	 */
	public boolean moveFile(String source, String destinationRoot, String destination, String extension, boolean overWrite) throws IOException, DuplicateFileException 
	{
		validateDestinationPath(destinationRoot, destination);
		
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
	 * @throws DuplicateFileException 
	 */
	public boolean copyFile(String source, String destinationRoot, String destination, String extension, boolean overWrite) throws IOException, DuplicateFileException 
	{
		validateDestinationPath(destinationRoot, destination);
		
		createAllDirectoriesInPath()
		
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(destination);
		
		if(overWrite)
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
		else
			Files.copy(sourcePath, targetPath);
		
		log.debug(String.format(">>> Succesfully copied file from %s to %s", source, destination));
		
		return true;
	}
	
	
	private void createAllDirectoriesInPath(String pathString) throws IOException
	{
		Path path = Paths.get(pathString);
		
		if(path.toFile().isFile())
			path = path.getParent();
		
		Files.createDirectories(path);
	}
	
	
	
	/**
	 * with this file c:\\ab\cd\ef.avi, we need to ensure that none of the 
	 * following exist on the file system: 
	 * 
	 * c:\\ab\cd\ef.avi: (Exact path with same casing),
	 * c:\\AB\cd\ef.avi: (path with different casing),
	 * c:\\ab\cd\EF.avi: (path with different casing),
	 * c:\\AB\cd\EF.avi: (path with different casing),
	 * c:\\AB: (part of the path in different casing),
	 * c:\\ab\Cd: (part of the path in different casing). 
	 * 
	 * the following is allowed (so long as the full identically cased path does not exist).
	 * we will simply create the missing dirs and complete the move or copy: 
	 *  
	 * c:\\ab: (part of the path in same casing),
	 * c:\\ab\cd\: (part of the path in same casing).
	 * 
	 * @param root
	 * @param file
	 * @throws DuplicateFileException
	 * @throws IOException
	 */
	private boolean validateDestinationPath(String root, String file) throws DuplicateFileException, IOException    
	{
		Path rootPath = Paths.get(root);
		if(!Files.exists(rootPath))
			throw new FileSystemException(String.format("Root path %s does not exist on the file system", root));
		
		Path filePath = Paths.get(file);
		
		 /* 
			 if file = c:\\ab\cd\ef.avi and below path exists on the file system, then Files.exists 
			 will return true on windows and linux:
			 
			 c:\\ab\cd\ef.avi: (Exact path with same casing):   
			 
			 for these paths only windows will return true (since it is not case sensitive):
			 
			 c:\\AB\cd\ef.avi: (path with different casing)	
			 c:\\ab\cd\EF.avi: (path with different casing)
			 c:\\AB\cd\EF.avi: (path with different casing)
		 */
		if(Files.exists(filePath))
			throw new DuplicateFileException(file.toString());
		
		/*
			Now check path in bits from the root up.  if file = c:\\ab\cd\ef.avi. 
			then the following partially matching paths will be invalid
			
			c:\\AB: (part of the path in different casing) 
			c:\\ab\Cd: (part of the path in different casing)
			
			full paths with different casing will be invalid also (relevant under linux)
			c:\\ab\cd\EF.avi
			
			partially matching identically cased paths will be valid:
			
			c:\\ab: (part of the path in same casing)
			c:\\ab\cd\: (part of the path in same casing)
		*/
		
		if(!pathExistsWithAlternateCasing(root, file));
			throw new DuplicateFileException(file.toString());
	}
	
	
	private boolean pathExistsWithAlternateCasing(String root, String file) throws IOException 
	{
		Path filePath = Paths.get(file);
		
		//iteratively append source directories to root and check that there is not alternative casing for each path  
		for(int i=1; i <= filePath.getNameCount(); i++)
		{
			Path subPath = filePath.subpath(0, i);
			Path appendedPath = Paths.get(root, subPath.toString());
			
			if(siblingExistsWithAlternateCasing(appendedPath))
			{
				return true;
			}
		}
	
		return false;
	}
	
	
	private boolean siblingExistsWithAlternateCasing(Path filePath) throws IOException   
	{
		Path parentPath = filePath.getParent();
		
		//cache sibling files and directories if we have not already done so
		if(!cachedPaths.containsKey(parentPath.toString()))
		{
			File parentFile = parentPath.toFile(); 
			
			List<String> dirs = new ArrayList<String>();
			for (File sibling : parentFile.listFiles())
			{
				dirs.add(sibling.getAbsolutePath());
			}
			cachedPaths.put(parentPath.toString(), dirs);
		}
		
		//check if there is a case insensitive version of sourceString
		List<String> paths = cachedPaths.get(parentPath.toString());
		for(String path : paths)
		{
			String parsedPath = filePath.toString().replaceFirst("[\\\\/]$", ""); 
			String parsedFileString = filePath.toString().replaceFirst("[\\\\/]$", "");
			
			if(!parsedPath.equals(parsedFileString) && parsedPath.equalsIgnoreCase(parsedFileString))
				return true;
		}
		
		return false;
	}
}










































