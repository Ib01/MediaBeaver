package com.ibus.opensubtitles.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashData;

public class OpenSubtitlesClient
{
	/*
	 * useragent: used by opensubtitles to identify the connecting application.
	 * Looks like u/n and pass can be blank if this is a test 
	 * userName: name of the connecting user 
	 * password: password of the connecting user 
	 * host: the OpenSubtitles web service host 
	 * sublanguageid: the id of the language(s) to download subtitles for. 
	 * token: a security token. Once logged on the security token will remain valid for 30 minutes.
	 */
	static Logger log = Logger.getLogger(OpenSubtitlesClient.class.getName());
	private OpenSubtitlesToken token = new OpenSubtitlesToken();
	
	private static String userName;
	private static String password;
	private static String useragent;
	private static String host;
	private static String sublanguageid;

	public OpenSubtitlesClient(String host,String useragent,String userName, String password,String sublanguageid)
	{
		this.userName = userName;
		this.password = password;
		this.useragent = useragent;
		this.host = host;
		this.sublanguageid = sublanguageid;
	}

	// this method should be called before any other.
	@SuppressWarnings("unchecked")
	public boolean login() throws MalformedURLException, XmlRpcException 
	{
		if (!token.tokenHasExpired())
			return true; 
		
        Object[] params = new Object[]{ userName, password, "", useragent };
        Map<String, String> result = (Map<String, String>)callRemoteProcedure("LogIn", params);
		        		        
        token.setToken((String) result.get("token"));
		
        return responseOk(result);
	}

	@SuppressWarnings("unchecked")
	public boolean logOut() throws MalformedURLException, IOException, XmlRpcException
	{
		if (token.tokenHasExpired())
			return true; 

		Object[] params = new Object[]{ token.getToken() };
        Map<String, String> result = (Map<String, String>)callRemoteProcedure("LogOut", params);
        
        return responseOk(result);	
	}

	
	
	@SuppressWarnings("unchecked")
	public OstTitleDto getTitleForHash(OpenSubtitlesHashData data) throws MalformedURLException, IOException, XmlRpcException
	{
		if (token.tokenHasExpired())
			throw new RuntimeException("Open subtitles conection token has timed out.  you need to call logon on the OpenSubtitlesClient before calling any other methods.");
		
		Map<String, String> mapQuery = new HashMap<String, String>();
        mapQuery.put("sublanguageid", sublanguageid);
        mapQuery.put("moviehash", data.getHashData());
        mapQuery.put("moviebytesize", data.getTotalBytes());

        Object[] params = new Object[]{token.getToken(), new Object[]{mapQuery}};
        Map result = (Map)callRemoteProcedure("SearchSubtitles", params);
        
    	OstTitleDto dto = new OstTitleDto();
        if(responseOk(result))
        	dto.setPossibleTitles((Object[]) result.get("data"));
        
    	return dto;
 	}
	
	
	@SuppressWarnings("unchecked")
	private <T> T callRemoteProcedure(String method, Object[] params) throws MalformedURLException, XmlRpcException
	{
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(host));
		
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        
        T result = (T) client.execute(method, params);
        return result;
	}
	
	private boolean responseOk(Map result)
	{
		return (result.get("status") != null && ((String) result.get("status")).trim().toUpperCase().equals("200 OK"));
	}
	
}







