package com.ibus.mediabeaver.core.exception;

public class FileNotExistException  extends Exception 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public FileNotExistException() {
    }
 
    public FileNotExistException(String message) {
    	super(message);
    }
 
    public FileNotExistException(Throwable cause) {
    	super(cause);
    }
 
    public FileNotExistException(String message, Throwable cause) {
    	super(message, cause);
    }

}

