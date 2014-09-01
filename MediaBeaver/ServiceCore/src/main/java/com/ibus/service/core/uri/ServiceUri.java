package com.ibus.service.core.uri;

import java.net.URI;
import java.net.URISyntaxException;

import com.ibus.service.core.exception.ServiceSearchException;

public abstract class ServiceUri 
{	
	protected String _method;

	public String getMethod() {
		return _method;
	}
	public void setMethod(String _method) {
		this._method = _method;
	}
	
	public abstract URI getURI() throws ServiceSearchException;

	//RESDTRUCTURE THIS CRAP
	
	/*protected URI getURI(String path, String scheme, String host) throws ServiceSearchException
	{
		return getURI(path, scheme, host, 0);
	}
	
	protected URI getURI(String path, String scheme, String host, int port) throws ServiceSearchException
	{
		return getURI(path, scheme, host, 0, null);
	}
	
	protected URI getURI(String path, String scheme, String host, String query) throws ServiceSearchException
	{
		return getURI(path, scheme, host, 0, query);
	}
	
	private URI getURI(String path, String scheme, String host, int port, String query) throws ServiceSearchException
	{
		try 
		{
			if(port == 0)
				return new URI(scheme, host, path, null);
			else
				return new URI(scheme, null, host, port, path, query, null);
			
		} catch (URISyntaxException e) 
		{
			throw new ServiceSearchException("The Uri used to communicate with the service is not well formed", e);
		}
	}
	*/
	
}













