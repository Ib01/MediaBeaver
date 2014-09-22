package com.ibus.mediabeaver.cli.integrationtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.ibus.mediabeaver.cli.utility.FileSystem;


public class FileSystemIT
{
	@Before
	public void beforeTest()
	{
		//refreshTestDirs();
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
