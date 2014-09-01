package com.ibus.service.core;

import java.net.URI;

import org.apache.log4j.Logger;

import com.ibus.service.core.exception.ServiceSearchException;
import com.ibus.service.core.uri.ServiceUri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public abstract class ServiceClient
{
	//public static Logger _log = Logger.getLogger("ServiceClient");
	protected static Client client;
	
	static {
		client = Client.create(); 
	}
	
	/**
	 * do a post to a web method that does not return a response
	 * @param uri
	 * the uri that defines the web method to call
	 * @param query
	 * the query object to post to the web method
	 */
	public static <T> void post(ServiceUri uri, T query) 
	{		
		if(query == null)
			throw new ServiceSearchException(String.format("query in ServiceClient.post(Class<R> returnType, ServiceUri uri, T query) cannot be null"));
		
		try 
		{
			doPost(uri.getURI(), query);
		}
		catch (Exception ex)
		{
			//_log.warn(String.format("An attempt to communicate with {0} failed.", uri.toString()), ex);
		}
	}
	
	
	/**
	 * do a post to a web method that returns an entity
	 * @param returnType
	 * the type of the object to return 
	 * @param uri
	 * the uri that defines the web method to call
	 * @param query
	 * the query object to post to the web method
	 * @return
	 */
	public static  <R, T> R post(Class<R> returnType, ServiceUri uri, T query) 
	{		
		if(query == null)
			throw new ServiceSearchException(String.format("query in ServiceClient.post(Class<R> returnType, ServiceUri uri, T query) cannot be null"));
		
		try 
		{
			R ret;
			ret = doPost(returnType, uri.getURI(), query);
			
			return ret;
		}
		catch (Exception ex)
		{
			@SuppressWarnings("unused")
			String h = "";

			//_log.warn(String.format("An attempt to communicate with {0} failed.", uri.toString()), ex);
		}
		
		return null;	
	}
	
	
	/**
	 * do a get against a web method
	 * @param returnType: 
	 * the type of the object to return 
	 * @param uri:
	 * the uri that defines the web method to call
	 * @return
	 */
	public static <R> R get(Class<R> returnType, ServiceUri uri) 
	{	
		try 
		{
			R ret;
			ret = doGet(returnType, uri.getURI());
			
			return ret;
		}
		catch (Exception ex)
		{
			//_log.warn(String.format("An attempt to communicate with {0} failed.", uri.toString()), ex);
		}
		
		return null;	
	}
	
	
	
	/**
	 * do a post to a web method that returns an entity
	 * @param returnType
	 * @param uri
	 * @param query
	 * @return
	 */
	private static <R, T> R doPost(Class<R> returnType, URI uri, T query)
	{
		WebResource webResource = client.resource(uri);
		R result = webResource.post(returnType, query);
	
		return result;
	}
	
	/**
	 * do a post to a web method that does not return a response
	 * @param uri
	 * @param query
	 */
	private static <T> void doPost(URI uri, T query)
	{
		WebResource webResource = client.resource(uri);
		webResource.post(query);
	}
	
	
	/**
	 * do a get against a web method
	 * @param returnType
	 * @param uri
	 * @return
	 */
	private static <R, T> R doGet(Class<R> returnType, URI uri)
	{
		WebResource webResource = client.resource(uri);
		R result = webResource.get(returnType);
		
		return result;
	}
	

	

}










