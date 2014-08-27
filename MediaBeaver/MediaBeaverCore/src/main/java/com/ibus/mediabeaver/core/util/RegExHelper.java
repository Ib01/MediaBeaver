package com.ibus.mediabeaver.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExHelper 
{
	
	/**
	 * Get variable names from a path
	 * @param regularExpression
	 * @param text
	 * @return
	 */
	public List<String> getFileTokenNames(String path)
	{
		if(path == null || path.trim().length() == 0)
			throw new IllegalArgumentException("path cannot be null or empty");
		
		Pattern pattern = Pattern.compile("\\{\\{(\\w+)\\}\\}");
		Matcher matcher = pattern.matcher(path);
		
		//List<String> captures = new ArrayList<String>();
		Hashtable<String, String> captures = new Hashtable<String, String>();
		
		while(matcher.find())
		{
			captures.put(matcher.group(1).trim(), matcher.group(1));
		}
		
		return new ArrayList<String>(captures.values());
	}
	
	
	
	public boolean matchFound(String regularExpression, String text)
	{
		if(regularExpression == null || regularExpression.trim().length() == 0)
			throw new IllegalArgumentException("regularExpression cannot be null or empty");
		if(text == null || text.trim().length() == 0)
			throw new IllegalArgumentException("text cannot be null or empty");
		
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find();
	}
	
	
	/**
	 * Capture text from a regular expression. if the regular expression does not contain capture groups this method will return an empty list 
	 * @param regularExpression
	 * @param text
	 * @return
	 */
	public List<String> captureStrings(String regularExpression, String text)
	{
		if(regularExpression == null || regularExpression.trim().length() == 0)
			throw new IllegalArgumentException("regularExpression cannot be null or empty");
		if(text == null || text.trim().length() == 0)
			throw new IllegalArgumentException("text cannot be null or empty");
		
		List<String> captures = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(text);
		
		if (matcher.find()) 
		{
			for(int i = 0; i <= matcher.groupCount(); i++)
			{
				if(matcher.group(i)!= null )
				{
					String group = matcher.group(i).trim();
					if(group.length() > 0)
						captures.add(group);
				}
			}
		}	
		
		return captures;		
	}
	
	
	/**
	 * parse a group assembly string and regex capture groups, to something that could be used as a file token.
	 * ie. pass {1}  (the number corresponds to a regex capture group),to for example "Iron Man".  this method 
	 * assumes that 
	 * @param capturedStrings
	 * @param assemblyString
	 * @return
	 */
	public String assembleFileToken(List<String> capturedStrings, String assemblyString) throws IllegalArgumentException
	{
		if(assemblyString == null || assemblyString.trim().length() == 0)
			throw new IllegalArgumentException("assemblyString cannot be null or empty");
		
		if(!containsCaptureGroup(assemblyString))//assemblyString must contain atleast one capture group
			throw new IllegalArgumentException("assemblyString must contain at least one capture group");

		String s = assemblyString;
		
		for(int i=0; i < capturedStrings.size(); i++)
		{
			s = s.replace(String.format("{%d}", i), capturedStrings.get(i).trim());
		}
		
		return s;
	}
	
	
	public boolean containsTokenPlaceholders(String text)
	{
		Pattern pattern = Pattern.compile("\\{\\{\\w+\\}\\}");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find();
	}
	
	public boolean containsCaptureGroup(String text)
	{
		if(text == null || text.trim().length() == 0)
			new IllegalArgumentException("text cannot be null or empty");
		
		Pattern pattern = Pattern.compile("\\{\\d\\}");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find();
	}
	                                          
	
	public String cleanFileToken(String toClean, String regEx, String replaceWith)
	{
		if(toClean == null || toClean.trim().length() == 0)
			throw new IllegalArgumentException("toClean cannot be null or empty");
		
		if(regEx == null || regEx.trim().length() == 0)
			return toClean;
		
		if(replaceWith == null)
			replaceWith = "";
		
		String s = toClean.trim();
		s = s.replaceAll(regEx, replaceWith);
		
		return s;
	}
	
	
	
	public String assembleFileName(HashMap<String, String> tokens, String fileName)
	{
		if(fileName == null || fileName.trim().length() == 0)
			throw new IllegalArgumentException("fileName cannot be null or empty");
		
		String s = fileName;
		
		Iterator<Entry<String, String>> i = tokens.entrySet().iterator();
		while(i.hasNext()) 
		{
			Map.Entry<String, String> me = (Map.Entry<String, String>)i.next();
			s = s.replace(String.format("{{%s}}", me.getKey()), me.getValue().trim());
		}
		
		return s;
		
	}
	
	
	
	
	
	
}
























