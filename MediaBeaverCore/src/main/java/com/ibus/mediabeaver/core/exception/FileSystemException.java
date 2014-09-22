package com.ibus.mediabeaver.core.exception;

public class FileSystemException  extends RuntimeException 
{
	private static final long serialVersionUID = -5064166279891703739L;
	private String filePath;
	
 
    public FileSystemException(String message, String filePath) 
    {
    	super(message);
    	this.filePath = filePath;
    }
    
    public FileSystemException(String message, String filePath, Throwable cause) 
    {
    	super(message, cause);
    	this.filePath = filePath;
    }
    
    public FileSystemException(String message) {
    	super(message);
    }
 
    public FileSystemException(String message, Throwable cause) {
    	super(message, cause);
    }
    
    
 
}

