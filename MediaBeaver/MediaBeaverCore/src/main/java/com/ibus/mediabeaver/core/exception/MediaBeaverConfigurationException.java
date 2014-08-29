package com.ibus.mediabeaver.core.exception;

public class MediaBeaverConfigurationException  extends Exception 
{
	private static final long serialVersionUID = -5064166279891703739L;

	public MediaBeaverConfigurationException() {
    }
 
    public MediaBeaverConfigurationException(String message) {
    	super(message);
    }
 
    public MediaBeaverConfigurationException(Throwable cause) {
    	super(cause);
    }
 
    public MediaBeaverConfigurationException(String message, Throwable cause) {
    	super(message, cause);
    }

}

