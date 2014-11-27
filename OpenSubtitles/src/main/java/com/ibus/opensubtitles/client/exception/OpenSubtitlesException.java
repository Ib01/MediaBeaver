package com.ibus.opensubtitles.client.exception;

public class OpenSubtitlesException extends Exception 
{
	private static final long serialVersionUID = 8246135177208671499L;

	public OpenSubtitlesException()
	{
		super("An Error occured communicating with the Open Subtitles web service");
	}
	
	public OpenSubtitlesException(Throwable cause)
	{
		super("An Error occured communicating with the Open Subtitles web service", cause);
	}
	
    public OpenSubtitlesException(String message) 
    {
    	super(message);
    }
 
    public OpenSubtitlesException(String message, Throwable cause) 
    {
    	super(message, cause);
    }

}
