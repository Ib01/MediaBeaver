package com.ibus.mediabeaver.cli.test;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.ibus.mediabeaver.cli.utility.FileSystem;

public class FileSystemTests
{
	@Before
	public void beforeTest()
	{
		refreshTestDirs();
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
	
	@Test
	public void makeDirsInPathTest()
	{
		FileSystem fileSys = new FileSystem();
		String destRoot ="D:\\MediabeaverTests\\Destination\\Movies";
		String destRelative = "Iron Man (2000)\\Iron Man (2000).mkv";
		
		boolean success = fileSys.makeDirsInPath(destRoot, destRelative);
		
		assertTrue(success);
	}

	@Test
	public void movieFile()
	{
		FileSystem fileSys = new FileSystem();
		String source = "D:\\MediabeaverTests\\Source\\Iron-Man (1992) bla some carap.mkv";
		String destRoot ="D:\\MediabeaverTests\\Destination\\Movies";
		String destRelative = "Iron Man (2000)\\Iron Man (2000).mkv";
		
		boolean success = fileSys.makeDirsInPath(destRoot, destRelative);
		fileSys.moveFile(source, destRoot, destRelative);
		
		assertTrue(success);
	}
	
	
	
}
