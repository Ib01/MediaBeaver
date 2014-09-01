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
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashData;
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














