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
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void openSubtitlesT1Test() throws XmlRpcException, IOException
	{
		 	XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	        config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
	        XmlRpcClient client = new XmlRpcClient();
	        client.setConfig(config);
	        Object username = "xxx";
	        Object password = "xxx";
	        Object language = "pob";
	        Object useragent = "OS Test User Agent";
	        Object[] params = new Object[] { username, password, language, useragent };
	        
	        //LOGIN /////////////////////////////////////////////
	        Map result = (Map) client.execute("LogIn", params);
	        String status = (String) result.get("status");
	        String token = (String) result.get("token");

	        Object imdbQuery = "true blood";
	        params = new Object[] { token, imdbQuery  };
	        
	        //SearchMoviesOnIMDB /////////////////////////////////////////////
	        result = (Map) client.execute("SearchMoviesOnIMDB", params);
	        
	        System.out.println(result);
	        
	        Object[] data = (Object[])result.get("data");
	        Map dataMap = (Map)data[0];
	        Object id = dataMap.get("id");
	        Object title = dataMap.get("title");
	        
	        Map movieQuery = new HashMap();
	        movieQuery.put("sublanguageid", "pob");
	        movieQuery.put("imdbid", id);
	        
	        Object[] listQuery = {movieQuery};
	        
	        params = new Object[] { token, listQuery   };
	        
	        //SearchSubtitles /////////////////////////////////////////////
	        result = (Map) client.execute("SearchSubtitles", params);
	        
	        System.out.println(result);
	        
	        data = (Object[])result.get("data");
	        dataMap = (Map)data[0];
	        
	       /* System.out.println(dataMap.get("SubFileName"));
	        //SubDownloadLink=http://dl.opensubtitles.org/en/download/filead/1953590867.gz/sid-ojl76mmpi89o8tvpb3ebl45d83
	        System.out.println(dataMap.get("SubDownloadLink"));
	        
	        String downloadLink = (String)dataMap.get("SubDownloadLink");
	        downloadLink = downloadLink.replaceFirst("http://dl.opensubtitles.org/en/download/filead/", "");
	        Object subFileId = downloadLink.substring(0, downloadLink.indexOf(".gz")); 

	        Map listOfSubtitles = new HashMap();
	        listOfSubtitles.put("data", subFileId);
	        params = new Object[] { token, listOfSubtitles    };
	        result = (Map) client.execute("DownloadSubtitles", params);
	        
	        System.out.println(result);
	        
	        data = (Object[])result.get("data");
	        dataMap = (Map)data[0];
	        
	        System.out.print(dataMap.get("data"));
	        
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] gzip = decoder.decodeBuffer((String)dataMap.get("data"));

	        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(gzip));
	        StringWriter sw = new StringWriter();
	        
	        IOUtils.copy(gis, sw);
	        sw.flush();
	        sw.close();
	        gis.close();
	        
	        System.out.println(sw.toString());
	        
	        params = new Object[] { token };
	        client.execute("LogOut", params);*/
	}
	
	
	
	
	
	
	
	
	
	
}














