package com.ibus.opensubtitles.client.exception;

public class OpenSubtitlesResponseException extends Exception 
{
	private static final long serialVersionUID = 5356107148397511248L;
	private static String BaseMessage ="The Open Subtitles service responded with an exception";  
	
	public OpenSubtitlesResponseException()
	{
		super(BaseMessage);
	}
	
	
    public OpenSubtitlesResponseException(String status) 
    {
    	super(BaseMessage + ". The status returned from the service was: " + status);
    }
 
    public OpenSubtitlesResponseException(String status, Throwable cause) 
    {
    	super(BaseMessage + ". The status returned from the service was: " + status, cause);
    }

}
