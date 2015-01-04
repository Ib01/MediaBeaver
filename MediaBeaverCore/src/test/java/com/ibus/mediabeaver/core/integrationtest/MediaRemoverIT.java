package com.ibus.mediabeaver.core.integrationtest;

import java.io.IOException;

import org.junit.Test;

import com.ibus.mediabeaver.core.filesystem.MediaRemover;

public class MediaRemoverIT
{
	@Test
	public void deleteFile() throws IOException
	{
		MediaRemover mr = new MediaRemover();
		mr.doDelete("D:\\MediabeaverTests\\DeleteTest\\b\\c.docx");
	}
	
	@Test
	public void deleteFolder() throws IOException
	{
		MediaRemover mr = new MediaRemover();
		mr.doDelete("D:\\MediabeaverTests\\DeleteTest\\b");
	}
	
	@Test
	public void deleteNonexistantFile() throws IOException
	{
		MediaRemover mr = new MediaRemover();
		mr.doDelete("D:\\MediabeaverTests\\DeleteTest\\b\\xxx");
	}
}
