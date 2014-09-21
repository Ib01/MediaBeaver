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
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;

public class FileSystem
{
	private Logger log = Logger.getLogger(Main.class.getName());
	HashMap<String, List<String>> cachedDirectories = new HashMap<String, List<String>>();
	
	
	
	/*
	 * c:\\ab\cd\ef.avi
	 * 
	 * c:\\ab\cd\ef.avi: Exact file with same casing exists: throw. 
	 * c:\\AB\cd\ef.avi: Same file with different parent folder casing: throw. 
	 * c:\\AB\cd\Ef.avi: Same file with different parent folder casing and different file casing: throw
	 * 
	 * c:\\AB: part of the path in different casing.  throw.
	 * c:\\ab\Cd: part of the path in different casing.  throw.
	 * c:\\ab: part of the path in same casing.  continue.
	 * 
	 * 
	 * otherwise create parent dir: c:\\ab\cd
	 * 
	*/
	public boolean fileExist(String file) throws DuplicateFileException    
	{
		Path filePath = Paths.get(file);
		
		//under windows this is case insensitive. if we search for a.txt and a file exists 
		//on the file system named A.txt, this method will return true. under linux it will not 
		if(Files.exists(filePath))
			throw new DuplicateFileException(file.toString());
		
		//if this is linux we also need to check if there is a file with alternate casing
		
		
		
		
		return false;
	}
	
	
	
	
	public boolean siblingExistsWithAlternateCasing(String root, String file) throws IOException   
	{
		String fileString = file.replaceFirst("[\\\\/]$", "");
		Path filePath = Paths.get(fileString);	
		Path parentPath = filePath.getParent();
		
		//cache sibling files and directories if we have not already done so
		if(!cachedDirectories.containsKey(parentPath.toString()))
		{
			File parentFile = parentPath.toFile(); 
			
			List<String> dirs = new ArrayList<String>();
			for (File sibling : parentFile.listFiles())
			{
				if (sibling.isDirectory())
				{
					String parsedFso = sibling.getAbsolutePath().replaceFirst("[\\\\/]$", "");
					dirs.add(parsedFso);
				}
			}
			cachedDirectories.put(parentPath.toString(), dirs);
		}
		
		//check if there is a case insensitive version of sourceString
		List<String> cachedPaths = cachedDirectories.get(parentPath.toString());
		for(String path : cachedPaths)
		{
			if(!path.equals(fileString) && path.equalsIgnoreCase(fileString))
				return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	/*public void validatePath(String destinationRoot, String source) throws IOException, FileWithAlternateCasingException  
	{
		
		
		
		//if this is windows we can do the check quickly without having to go through each path component
		Path finalPath = Paths.get(destinationRoot, source);
		Path finalParentPath = finalPath.getParent();
		
		if(windowsDirectoryExistWithAlternateCasing(finalParentPath.toString()))
			throw new FileWithAlternateCasingException(finalParentPath.toString());
		
		
		
		Path sourcePath = Paths.get(source);
		
		//iteratively append source directories to root and check that there is not alternative casing for each folder  
		for(int i=1; i < sourcePath.getNameCount(); i++)
		{
			Path subPath = sourcePath.subpath(0, i);
			Path appendedPath = Paths.get(destinationRoot, subPath.toString());
			
			if(!Files.exists(appendedPath))
				break;
				
			if(linuxDirectorySiblingExistWithAlternateCasing(appendedPath.toString()))
				throw new FileWithAlternateCasingException(appendedPath.toString());
			
			
			
			Path q = sourcePath.subpath(0, i); 
		}
		
		
		
		
		
		Path rootPath = Paths.get(destinationRoot);
		File rootFile = rootPath.toFile();
		
		for (File file : rootFile.listFiles())
		{
			
		}
		
		
	}
	
	
	*/

	
	
	
	public boolean windowsDirectoryExistWithAlternateCasing(String directory) throws IOException   
	{
		String directoryString = directory.replaceFirst("[\\\\/]$", "");
		Path directoryPath = Paths.get(directoryString);	
		
		//if true: we have case sensitive fs (ie linux) and we have a dir with exact casing 
		//OR we have case insensitive fs and dir that may have alternate casing (windows)  
		if(Files.exists(directoryPath))
		{
			Path realPath = directoryPath.toRealPath(); //get path exactly as cased on the file system
			
			//remove traling path seperators 
			String realString = realPath.toString().replaceFirst("[\\\\/]$", "");

			if(!realString.equals(directoryString))
				return true;  //directory exists on fs and is cased differently. can only happen on windows 
		}
		
		return false;
	}
	
	
	
	
	

	/*public void createDirectoriesInPath(String file) throws IOException, FileWithAlternateCasingException  
	{
		Path filePath = Paths.get(file);
		Path parentDir = filePath.getParent();
		
		//if parent dir already exists with exact casing simply return
		if(!directoryExistWithExactCasing(parentDir.toString()))
		{
			log.debug("A directory was found on the destination file system with the exact casing as: " + parentDir);
			return;
		}
		
		if(directoryExistWithAlternateCasing(parentDir.toString()))
			throw new FileWithAlternateCasingException(parentDir.toString());
		
		Files.createDirectories(parentDir);
		log.debug("A directory was found on the destination file system with the exact casing as: " + parentDir);
	}
	*/
	
	public boolean fileExistWithExactCasing(String directory) throws IOException  
	{
		String directoryString = directory.replaceFirst("[\\\\/]$", "");
		Path directoryPath = Paths.get(directoryString);
		
		if(Files.exists(directoryPath))
		{
			Path realPath = directoryPath.toRealPath(); 
			String realString = realPath.toString().replaceFirst("[\\\\/]$", "");
			
			//if linux it will be in right casing. not necessarily true under windows
			if(realString.equals(directoryString))
				return true;
		}
		
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










































