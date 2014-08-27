package com.ibus.mediabeaver.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceFieldParser
{
	/**
	 * transform the value of a field from the a media service to the 
	 * format specified in transformFormat.  
	 * 
	 * @param fieldValue
	 * the value of a field from the open subtitles service 
	 * @param transformFormat
	 * the format string used for transforming the value of a service field. "{MovieYear}.replaceAll(regex, replacement)" is an example.
	 * the first value in brackets specifies the field. tokens after this are the methods used to transform the field value.
	 * @return
	 */
	public String parseField(String fieldValue, String transformFormat)
	{
		
		
		
		return transformFormat;
	}
	

	/**
	 * Gets the service field identifier from a servicer field transform string 
	 * 
	 * @param transformFormat
	 * The format string used for transforming the value of a service field. in the format string 
	 * "{MovieYear}.replaceAll(regex, replacement)" {MovieYear} is the service field identifier
	 * 
	 * @return
	 * the service field identifier
	 */
	public String getFieldIdentifier(String transformFormat)
	{		
		Pattern pattern = Pattern.compile("^\\{([a-zA-Z]+)\\}");
		Matcher matcher = pattern.matcher(transformFormat.trim());
		
		if (matcher.find()) 
		{
			if(matcher.groupCount() ==1 && matcher.group(1)!= null )
			{
				return matcher.group(1);
			}
		}
		
		return null;
	}
	
}


















