package com.ibus.mediabeaver.core.exception;

public class PathParseException extends Exception 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public PathParseException() {
    }
 
    public PathParseException(String message) {
    	super(message);
    }
 
    public PathParseException(Throwable cause) {
    	super(cause);
    }
 
    public PathParseException(String message, Throwable cause) {
    	super(message, cause);
    }

}