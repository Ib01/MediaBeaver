package com.ibus.opensubtitles;

import java.util.Calendar;
import java.util.Date;


public class OpenSubtitlesToken 
{
	int expiryTimeUnit = Calendar.MINUTE;
	int expiryTimeAmount = 30; //minutes the token is valid for  
	Date tokenExpiry = null;
	String token = null;
	
	
	public OpenSubtitlesToken()
	{
	}
	
	public void setToken(String token)
	{
		tokenExpiry = null;
		this.token = null;
		if(token != null && token.trim().length() > 0)
		{
			resetExpiry();
		    this.token = token;
		}
	}	
	
	//if token has expired will return null
	public String getToken()
	{
		return token;
	}
	
	
	public boolean tokenHasExpired()
	{
		Date now = new Date();
		 
	    if(tokenExpiry == null || now.compareTo(tokenExpiry) > 0)
	      return true;
	    else
	      return false; 
	}
	
	private void resetExpiry()
	{
		Calendar calendar  = Calendar.getInstance();
	    calendar.add(expiryTimeUnit, expiryTimeAmount);
	    tokenExpiry = calendar.getTime();
	}
	
}











