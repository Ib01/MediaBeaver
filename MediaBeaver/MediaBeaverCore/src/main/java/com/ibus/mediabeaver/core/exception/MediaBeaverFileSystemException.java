package com.ibus.mediabeaver.core.exception;

public class MediaBeaverFileSystemException  extends RuntimeException 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public MediaBeaverFileSystemException() {
    }
 
    public MediaBeaverFileSystemException(String message) {
    	super(message);
    }
 
    public MediaBeaverFileSystemException(Throwable cause) {
    	super(cause);
    }
 
    public MediaBeaverFileSystemException(String message, Throwable cause) {
    	super(message, cause);
    }

}

