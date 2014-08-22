package com.ibus.tmdbclient;


public class ServiceSearchException extends RuntimeException 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public ServiceSearchException() {
    }
 
    public ServiceSearchException(String message) {
    	super(message);
    }
 
    public ServiceSearchException(Throwable cause) {
    	super(cause);
    }
 
    public ServiceSearchException(String message, Throwable cause) {
    	super(message, cause);
    }
 
}