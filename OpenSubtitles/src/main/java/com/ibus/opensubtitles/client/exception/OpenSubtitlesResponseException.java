package com.ibus.opensubtitles.client.exception;

public class OpenSubtitlesResponseException extends Exception 
{
	private static final long serialVersionUID = -2586484496593679147L;

	public OpenSubtitlesResponseException()
	{
		super("The Open Subtitles service responded with an exception");
	}
	
    public OpenSubtitlesResponseException(String message) 
    {
    	super(message);
    }
 
    public OpenSubtitlesResponseException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
