package com.ibus.mediabeaver.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExHelper 
{
	
	/**
	 * Get unique variable names from a path.  for example will parse the path {{MovieName}} ({{MovieYear}})\{{MovieName}} ({{MovieYear}}) 
	 * to the following list:  {"MovieName", "MovieYear"}
	 * @param regularExpression
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPathTokenList(String path)
	{
		HashMap<String, String> captures = getPathTokenHashMap(path);
		List<String> ret = new ArrayList<String>(captures.keySet());
		
		return ret;
	}
	
	/**
	 * Get unique variable names from a path.  for example will parse the path {{MovieName}} ({{MovieYear}})\{{MovieName}} ({{MovieYear}}) 
	 * to the following HashMap:  {{"MovieName",""} {"MovieYear",""}}
	 * @param regularExpression
	 * @param text
	 * @return
	 */
	public HashMap<String, String> getPathTokenHashMap(String path)
	{
		if(path == null || path.trim().length() == 0)
			throw new IllegalArgumentException("path cannot be null or empty");
		
		Pattern pattern = Pattern.compile("\\{\\{(\\w+)\\}\\}");
		Matcher matcher = pattern.matcher(path);
		
		HashMap<String, String> captures = new HashMap<String, String>();
		
		while(matcher.find())
		{
			captures.put(matcher.group(1).trim(), "");
		}
		
		return captures;
	}
	
	
	
	
	/**
	 * Determine if if the pattern defined by regularExpression is found inside text. 
	 * used by regExSelector to populate path tokens 
	 * @param regularExpression
	 * @param text
	 * @return
	 */
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
	 * Capture text from a regular expression. if the regular expression does not contain capture groups 
	 * this method will return an empty list. used by regExSelector to populate path tokens 
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
	 * ie. pass {1}  (the number corresponds to a regex capture group),to for example "Iron Man".  used by regExSelector 
	 * to populate path tokens. soon to be deprecated
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
	
	
	/**
	 * determines whether a path contains token place holders such as 
	 * {{MovieName}} ({{MovieYear}})\{{MovieName}} ({{MovieYear}}).  
	 * @param text
	 * @return
	 */
	public boolean containsTokenPlaceholders(String text)
	{
		Pattern pattern = Pattern.compile("\\{\\{\\w+\\}\\}");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find();
	}
	
	/**
	 * used to determine whether a string contains a regExCapture group.  capture groups are used in 
	 * assembly fields of the regexSlector and must be surrounded by brackets.  soon to be deprecated
	 * @param text
	 * @return
	 */
	public boolean containsCaptureGroup(String text)
	{
		if(text == null || text.trim().length() == 0)
			new IllegalArgumentException("text cannot be null or empty");
		
		Pattern pattern = Pattern.compile("\\{\\d\\}");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find();
	}
	                                          
	
	/**
	 * used by regexselector to clean populated values of path tokens.  soon to be deprecated 
	 * @param toClean
	 * @param regEx
	 * @param replaceWith
	 * @return
	 */
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
	
	
	
	/**
	 * parses the file tokens in a path.  for example given the following HashMap {{"MovieName", "Iron Man"},{"MovieYear", "2010"}}, the path   
	 * {{MovieName}} ({{MovieYear}})\{{MovieName}} ({{MovieYear}}) will be parsed to Iron Man (2010)\Iron Man (2010)
	 * @param tokens
	 * @param fileName
	 * @return
	 */
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
	
	
	/**
	 * The white list of characters we allow for file names
	 * @param text
	 * @return
	 */
	public boolean containsValidFileNameCharacters(String text)
	{
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9~`!@#\\$%\\^&\\(\\)_-\\+=\\{\\}\\[\\];'',\\.]");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.find(); 
	}
	
	/*
	 * illegal windows characters
	 * < (less than)
	> (greater than)
	: (colon)
	" (double quote)
	/ (forward slash)
	\ (backslash)
	| (vertical bar or pipe)
	? (question mark)
	* (asterisk)*/
	
	//illegal windows file names?
	//CON, PRN, AUX, NUL, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9, LPT1, LPT2, LPT3, 
	//LPT4, LPT5, LPT6, LPT7, LPT8, and LPT9. Also avoid these names followed immediately by an extension; 
	//for example, NUL.txt is not recommended. For more information, see Namespaces.
	
	
}
























