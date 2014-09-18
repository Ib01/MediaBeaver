package com.ibus.mediabeaver.core.exception;

public class FileExistsException  extends Exception 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public FileExistsException() {
    }
 
    public FileExistsException(String message) {
    	super(message);
    }
 
    public FileExistsException(Throwable cause) {
    	super(cause);
    }
 
    public FileExistsException(String message, Throwable cause) {
    	super(message, cause);
    }

}

