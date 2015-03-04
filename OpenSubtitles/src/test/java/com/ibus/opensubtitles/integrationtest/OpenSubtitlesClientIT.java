package com.ibus.opensubtitles.integrationtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;
import org.junit.Test;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.opensubtitles.client.OpenSubtitlesToken;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesLoginException;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;

public class OpenSubtitlesClientIT
{
	public static String OstUserName = "";
	public static String OstPassword = "";
	public static String OstUseragent = "MediaBeaver V0.1";
	//private static String ostUseragent = "FileBot v4.5";
	//private static String ostUseragent = "OS Test User Agent";
	//private static String ostUseragent = "OSTestUserAgent";
	public static String OstHost = "http://api.opensubtitles.org/xml-rpc";
	public static String OstSublanguageid = "eng";
	
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
	public void logonLogoutTest() throws OpenSubtitlesLoginException, OpenSubtitlesResponseException, OpenSubtitlesException
	{
		OpenSubtitlesClient osClient = new OpenSubtitlesClient(OstHost,OstUseragent,OstUserName, OstPassword,OstSublanguageid);
		
		osClient.login();
		osClient.logOut();
	}
	
	@Test
	public void getTitleForHashTest() throws IOException, OpenSubtitlesLoginException, OpenSubtitlesResponseException, OpenSubtitlesException 
	{
		OpenSubtitlesHashData data = OpenSubtitlesHashGenerator.computeHash(new File("D:\\MediabeaverTests\\Aliens (1986) Special Edition.avi"));
		OpenSubtitlesClient osClient = new OpenSubtitlesClient(OstHost,OstUseragent,OstUserName, OstPassword,OstSublanguageid);
		
		osClient.login();
		OstTitleDto dto = osClient.getTitleForHash(data);
		osClient.logOut();
		
		assertTrue(dto.getPossibleTitles().size() > 0);
	}
	
	
	
	
}














