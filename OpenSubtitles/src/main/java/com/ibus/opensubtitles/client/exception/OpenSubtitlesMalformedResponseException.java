package com.ibus.opensubtitles.client.exception;

public class OpenSubtitlesMalformedResponseException extends Exception 
{
	private static final long serialVersionUID = 5356107148397511248L;

	public OpenSubtitlesMalformedResponseException()
	{
		super("The response from the open subtitles service was not in the format expected. data fields may be malformed or missing in the response");
	}
	
    public OpenSubtitlesMalformedResponseException(String message) 
    {
    	super(message);
    }
 
    public OpenSubtitlesMalformedResponseException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
