package com.ibus.opensubtitles.client.exception;

public class OpenSubtitlesLoginException extends Exception 
{
	private static final long serialVersionUID = -2586484496593679147L;

	public OpenSubtitlesLoginException()
	{
		super("Could not login to the Open Subtitles web service");
	}
	
	public OpenSubtitlesLoginException(Throwable cause)
	{
		super("Could not login to the Open Subtitles web service", cause);
	}
	
    public OpenSubtitlesLoginException(String message) 
    {
    	super(message);
    }
 
    public OpenSubtitlesLoginException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
