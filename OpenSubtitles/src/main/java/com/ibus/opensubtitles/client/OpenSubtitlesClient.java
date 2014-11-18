package com.ibus.opensubtitles.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.exception.OpenSubtitlesResponseException;

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

	/**
	 * Login to Open Subtitles service.  This method must be called before any other method is called
	 * @return
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 * @throws OpenSubtitlesResponseException 
	 */
	@SuppressWarnings("unchecked")
	public void login() throws MalformedURLException, XmlRpcException, OpenSubtitlesResponseException 
	{
		if (!token.tokenHasExpired()) return; 
		
        Object[] params = new Object[]{ userName, password, "", useragent };
        Map<String, String> result = (Map<String, String>)callRemoteProcedure("LogIn", params);
		        		        
        token.setToken((String) result.get("token"));
		
        responseOk(result);
	}

	/**
	 * Logout of the Open Subtitles Service
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws OpenSubtitlesResponseException 
	 */
	@SuppressWarnings("unchecked")
	public void logOut() throws MalformedURLException, IOException, XmlRpcException, OpenSubtitlesResponseException
	{
		if (token.tokenHasExpired()) return; 

		Object[] params = new Object[]{ token.getToken() };
        Map<String, String> result = (Map<String, String>)callRemoteProcedure("LogOut", params);
        
        responseOk(result);
	}

	
	
	/**
	 * Get a Title (Movie or Episode) for the supplied hash data 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws XmlRpcException
	 * @throws OpenSubtitlesResponseException 
	 */
	@SuppressWarnings("unchecked")
	public OstTitleDto getTitleForHash(OpenSubtitlesHashData data) throws MalformedURLException, IOException, XmlRpcException, OpenSubtitlesResponseException
	{
		//this should only happen if the developer miss-uses this class, as it should not take 30 minutes to call in here after calling logon.
		if (token.tokenHasExpired())
			throw new RuntimeException("Open subtitles conection token has timed out.  you need to call logon on the OpenSubtitlesClient before calling any other methods.");
		
		Map<String, String> mapQuery = new HashMap<String, String>();
        mapQuery.put("sublanguageid", sublanguageid);
        mapQuery.put("moviehash", data.getHashData());
        mapQuery.put("moviebytesize", data.getTotalBytes());

        Object[] params = new Object[]{token.getToken(), new Object[]{mapQuery}};
        Map result = (Map)callRemoteProcedure("SearchSubtitles", params);
        
    	OstTitleDto dto = new OstTitleDto();
        responseOk(result);
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
	
	private void responseOk(Map result) throws OpenSubtitlesResponseException
	{
		//return (result.get("status") != null && ((String) result.get("status")).trim().toUpperCase().equals("200 OK"));
		
		if(result.get("status") == null || !((String) result.get("status")).trim().toUpperCase().equals("200 OK"))
		{
			throw new OpenSubtitlesResponseException();
		}
	}
	
}







