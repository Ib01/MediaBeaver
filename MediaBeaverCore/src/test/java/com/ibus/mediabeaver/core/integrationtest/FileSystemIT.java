package com.ibus.mediabeaver.core.integrationtest;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.filesystem.FileSystem;

public class FileSystemIT
{


	@Test
	public void pathExistsWithArray()
	{
		FileSystem fileSys = new FileSystem();
		
		boolean result = false; 
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\Destination", "\\Movies"});
		assertTrue(result);
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\Destination", "Movies"});
		assertTrue(result);
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\Destination\\", "Movies"});
		assertTrue(result);
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\Destination", "Movies"});
		assertTrue(result);
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\Destination", "MoviesXX"});
		assertFalse(result);
		
		result = fileSys.pathExists(new String[]{"D:\\MediabeaverTests\\DestinationXX", "Movies"});
		assertFalse(result);
	}
	
	
	
	
	
	

}












