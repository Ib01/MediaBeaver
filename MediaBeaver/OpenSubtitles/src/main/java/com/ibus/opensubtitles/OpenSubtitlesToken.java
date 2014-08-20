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
		if(token == null || token.length() == 0)
			return;
		 
		resetExpiry();
		this.token = token;
		
	}	
	
	/**
	 * before calling this method call tokenHasExpired to ensure that the 
	 * token is valid and has not expired.    
	 * @return
	 */
	public String getToken()
	{
		//will return null if token has expired 
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











