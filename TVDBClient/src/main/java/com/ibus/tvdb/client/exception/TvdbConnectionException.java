package com.ibus.tvdb.client.exception;

public class TvdbConnectionException extends Exception 
{
	private static final long serialVersionUID = 8246135177208671499L;

	public TvdbConnectionException()
	{
		super("An error occured communicating with the TVDB web service.  the site may not be available or there may be a problem with your network connectivity.");
	}
	
	public TvdbConnectionException(Throwable cause)
	{
		super("An error occured communicating with the TVDB web service.  the site may not be available or there may be a problem with your network connectivity.", cause);
	}
	
    public TvdbConnectionException(String message) 
    {
    	super(message);
    }
 
    public TvdbConnectionException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
