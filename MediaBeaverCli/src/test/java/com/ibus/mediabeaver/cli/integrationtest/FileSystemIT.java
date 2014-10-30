package com.ibus.mediabeaver.cli.integrationtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.filesystem.FileSystem;


public class FileSystemIT
{
	@Before
	public void beforeTest()
	{
		//refreshTestDirs();
	}
	

	@Test
	public void validateDestinationPathTest() throws IOException
	{
		String root;
		String pathEnd;
		FileSystem fs = new FileSystem();
		
		root = "D:\\MediabeaverTests\\Destination\\Validation Tests";
		
		pathEnd = "\\Folder 1\\Folder 2\\somefile.mkv";
		try
		{
			fs.validateDestinationPath(root, pathEnd);
			
		} 
		catch (DuplicateFileException e)
		{
			assertTrue(false);
		}
		
		pathEnd = "Folder 2\\Folder 2\\somefile.mkv";
		try
		{
			fs.validateDestinationPath(root, pathEnd);
			assertTrue(false);
		} 
		catch (DuplicateFileException e)
		{}
		
		pathEnd = "Folder 3\\Folder 2\\somefile.mkv";
		try
		{
			fs.validateDestinationPath(root, pathEnd);
			assertTrue(false);
		} 
		catch (DuplicateFileException e)
		{}
		
		pathEnd = "Folder 4\\Folder 2\\somefile.mkv";
		try
		{
			fs.validateDestinationPath(root, pathEnd);
			assertTrue(false);
		} 
		catch (DuplicateFileException e)
		{}
		
		pathEnd = "\\Folder 5\\Folder 2\\somefile.mkv";
		try
		{
			fs.validateDestinationPath(root, pathEnd);
		} 
		catch (DuplicateFileException e)
		{
			assertTrue(false);
		}
		
		
		assertTrue(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private void refreshTestDirs()
	{
		try
		{
			File f = new File("D:\\MediabeaverTests\\Destination\\Movies");
			FileUtils.deleteDirectory(f);
			f.mkdir();
			
			FileUtils.copyDirectory(new File("D:\\MediabeaverTests\\Source - Copy"), new File("D:\\MediabeaverTests\\Source"));
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
}
