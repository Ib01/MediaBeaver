package com.ibus.mediaBeaverCore.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExGenerator 
{
	/**
	 * Capture text from a regular expression. the regular expression must contain capture groups 
	 * @param regularExpression
	 * @param text
	 * @return
	 */
	public List<String> captureStrings(String regularExpression, String text)
	{
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(text);
		
		List<String> capturedText = new ArrayList<String>();
		
		if (matcher.find()) 
		{
			for(int i = 0; i <= matcher.groupCount(); i++)
			{
				capturedText.add(matcher.group(i));	
			}
		}	
		
		return capturedText;		
	}
	
	/**
	 * Clean a string with a regular expression designed to keep portions of the string.
	 * @param keepExpression
	 * @param text
	 * @return
	 */
	public String cleanString(String keepExpression, String text, String joinString)
	{
		Pattern pattern = Pattern.compile(keepExpression);
		Matcher matcher = pattern.matcher(text);
		
		String parsedString = "";
		while(matcher.find())
		{
			if(matcher.groupCount() <= 0)
				continue;
			
			if(parsedString.length() > 0)
				parsedString += joinString + matcher.group(1);
			else
				parsedString += matcher.group(1);
		}
		
		return parsedString;
	}
	
	
	public String assembleString(List<String> capturedStrings, String assemblyString)
	{
		String s = assemblyString;
		for(int i=0; i < capturedStrings.size(); i++)
		{
			s = s.replace(String.format("{%d}", i), capturedStrings.get(i));
		}
		
		return s;
	}
	
	public boolean containsCaptureGroup(String string)
	{
		Pattern pattern = Pattern.compile("\\{\\d\\}");
		Matcher matcher = pattern.matcher(string);
		
		return matcher.find();
	}
	
}
























