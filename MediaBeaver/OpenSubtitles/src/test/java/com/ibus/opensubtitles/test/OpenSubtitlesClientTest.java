package com.ibus.opensubtitles.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;
import org.junit.Test;

import com.ibus.opensubtitles.OpenSubtitlesClient;
import com.ibus.opensubtitles.dto.OpenSubtitlesResponse;
import com.ibus.opensubtitles.utilities.OpenSubtitlesHashData;
import com.ibus.opensubtitles.utilities.OpenSubtitlesHashGenerator;

public class OpenSubtitlesClientTest
{
	
	@Test
	public void getHashTest()
	{
		try
		{
			OpenSubtitlesHashData data = OpenSubtitlesHashGenerator.computeHash(new File("D:\\MediabeaverTests\\Aliens (1986) Special Edition.avi"));
			assertTrue(data != null && data.getHashData() != null && data.getHashData().length() > 0);
			
		} catch (IOException e)
		{
			assertTrue(false);
		}
		
	}
	
	@Test
	public void logonTest()
	{
		try
		{
			OpenSubtitlesClient osClient = new OpenSubtitlesClient();
			
			boolean logedIn = osClient.login();
			assertTrue(logedIn);
			osClient.logOut();
			
		} catch (IOException e)
		{
			assertTrue(false);
		}
		
	}
	
	@Test
	public void getTitleForHashTest()
	{
		try
		{
			OpenSubtitlesHashData data = OpenSubtitlesHashGenerator.computeHash(new File("D:\\MediabeaverTests\\Aliens (1986) Special Edition.avi"));
			OpenSubtitlesClient osClient = new OpenSubtitlesClient();
			
			boolean logedIn = osClient.login();
			OpenSubtitlesResponse xml = osClient.getTitleForHash(data);
			assertTrue(xml != null && xml.getName() != null && xml.getName().length() > 0);
			osClient.logOut();
			
		} catch (IOException e)
		{
			assertTrue(false);
		}
		
	}
	
	
	
	@Test
	public void logon2Test() throws MalformedURLException, XmlRpcException
	{
		OpenSubtitlesClient osClient = new OpenSubtitlesClient();
		
		boolean logedIn = osClient.login2();
		assertTrue(logedIn);
	}
	
	@Test
	public void logout2Test() throws XmlRpcException, IOException
	{
		OpenSubtitlesClient osClient = new OpenSubtitlesClient();
		
		osClient.login2();
		boolean logedOut = osClient.logOut2();
		assertTrue(logedOut);
		
	}
	
	@Test
	public void getTitleForHash2Test() throws XmlRpcException, IOException
	{
		OpenSubtitlesHashData data = OpenSubtitlesHashGenerator.computeHash(new File("D:\\MediabeaverTests\\Aliens (1986) Special Edition.avi"));
		OpenSubtitlesClient osClient = new OpenSubtitlesClient();
		
		osClient.login2();
		osClient.getTitleForHash2(data);
		osClient.logOut2();
		
		assertTrue(true);
		
	}
	
}














