package com.ibus.mediabeaver.core.integrationtest;

import java.io.IOException;

import org.junit.Test;

import com.ibus.mediabeaver.core.util.FileSystem;

public class FileSystemIT
{
	@Test
	public void deleteFile() throws IOException
	{
		FileSystem fs = new FileSystem();
		fs.deleteFile("D:\\MediabeaverTests\\DeleteTest\\b\\c.txt");
	}
	
	@Test
	public void deleteFolder() throws IOException
	{
		FileSystem fs = new FileSystem();
		fs.deleteFile("D:\\MediabeaverTests\\DeleteTest\\b");
	}
	
	@Test
	public void deleteNonexistantFile() throws IOException
	{
		FileSystem fs = new FileSystem();
		fs.deleteFile("D:\\MediabeaverTests\\DeleteTest\\xxx");
	}
}
