package com.ibus.tvdb.client.exception;

public class TvdbException extends Exception 
{
	private static final long serialVersionUID = 8246135177208671499L;

	public TvdbException()
	{
		super("An unxpected error occured while attempting to communicate with the tvdbService");
	}
	
	public TvdbException(Throwable cause)
	{
		super("An unxpected error occured while attempting to communicate with the tvdbService", cause);
	}
	
    public TvdbException(String message) 
    {
    	super(message);
    }
 
    public TvdbException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
