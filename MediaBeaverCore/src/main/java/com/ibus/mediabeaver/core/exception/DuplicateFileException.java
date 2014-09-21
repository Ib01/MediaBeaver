package com.ibus.mediabeaver.core.exception;

public class DuplicateFileException  extends Exception 
{
	private static final long serialVersionUID = -5064166279891703739L;
	private String filePath;
	
 
    public DuplicateFileException(String filePath) 
    {
    	super(String.format("The file %s already exists on the destination file system", filePath));
    	
    	this.filePath = filePath;
    }
 
    
 
}

