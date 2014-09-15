package com.ibus.opensubtitles.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.Test;

import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.opensubtitles.client.OpenSubtitlesToken;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;

public class OpenSubtitlesClientTest
{
	private static String userName = "";
	private static String password = "";
	private static String useragent = "OS Test User Agent";
	private static String host = "http://api.opensubtitles.org/xml-rpc";
	private static String sublanguageid = "eng";
	private OpenSubtitlesToken token = new OpenSubtitlesToken();
	
	
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
	public void logonLogoutTest() throws XmlRpcException, IOException
	{
		OpenSubtitlesClient osClient = new OpenSubtitlesClient(host,useragent,userName, password,sublanguageid);
		
		assertTrue(osClient.login());
		assertTrue(osClient.logOut());
	}
	
	@Test
	public void getTitleForHashTest() throws XmlRpcException, IOException
	{
		OpenSubtitlesHashData data = OpenSubtitlesHashGenerator.computeHash(new File("D:\\MediabeaverTests\\Aliens (1986) Special Edition.avi"));
		OpenSubtitlesClient osClient = new OpenSubtitlesClient(host,useragent,userName, password,sublanguageid);
		
		assertTrue(osClient.login());
		OstTitleDto dto = osClient.getTitleForHash(data);
		assertTrue(osClient.logOut());
		
		assertTrue(dto.getPossibleTitles().size() > 0);
	}
	
	
	
	
}














