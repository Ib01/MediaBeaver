package com.ibus.mediabeaver.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class ServiceFieldParser2
{	
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
	
	public String parseField(String fieldValue, String transformFormat)
	{
		/*
			itterate over all varaible with methods  
			\{([a-zA-Z]+)\}(\.(?:[a-zA-Z]+)\((?:\s*"(?:[^"]|(?<=\\)")*"\s*,(?!\s*\)))*(?:\s*"(?:[^"]|(?<=\\)")*"\s*)?\))+
			
			itterate over each above to get methods for each varaible  
			\.([a-zA-Z]+)\(((?:\s*"(?:[^"]|(?<=\\)")*"\s*,(?!\s*\)))*(?:\s*"(?:[^"]|(?<=\\)")*"\s*)?)\)
			
			//itterate over each methods parameters
			"((?:[^"]|(?<=\\)")*)"
		*/
		
		
		Pattern methodPattern = Pattern.compile("\\.([a-zA-Z]+)\\((.*?[^\\\\])\\)");
		Matcher methodMatcher = methodPattern.matcher(transformFormat.trim());
		
		//itterate over each method
		while(methodMatcher.find())
		{
			String methodName = methodMatcher.group(1);
			String methodParameters = methodMatcher.group(2);
			
			Pattern parameterPattern = Pattern.compile("\"(.*?[^\\\\])\"");
			Matcher parameterMatcher = parameterPattern.matcher(methodParameters);
		
			List<String> parameters = new ArrayList<String>();
			while(parameterMatcher.find())
			{
				parameters.add(parameterMatcher.group(1));
			}
			
			fieldValue = executeMethod(fieldValue, methodName, parameters);
		}
		
		return fieldValue;
	}
	
	
	private String executeMethod(String fieldValue, String methodName, List<String> parameters)
	{
		if(methodName.equals("normalizeSpace"))
		{
			return StringUtils.normalizeSpace(fieldValue);
		}
		if(methodName.equals("leftPad"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new RuntimeException("Service Field Method leftPad has incorrect number of parameters");
			
			if(!StringUtils.isNumeric(parameters.get(0)))
				throw new RuntimeException("Service Field Method leftPad has an incorrect parameter: parameter 1 must be numeric");
			
			return StringUtils.leftPad(fieldValue, Integer.parseInt(parameters.get(0)), parameters.get(1));
		}
		if(methodName.equals("rightPad"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new RuntimeException("Service Field Method rightPad has incorrect number of parameters");
			
			if(!StringUtils.isNumeric(parameters.get(0)))
				throw new RuntimeException("Service Field Method rightPad has an incorrect parameter: parameter 1 must be numeric");
			
			return StringUtils.rightPad(fieldValue, Integer.parseInt(parameters.get(0)), parameters.get(1));
		}
		if(methodName.equals("capitalizeFully"))
		{
			//TODO: IMPLEMENT THIS?
			//WordUtils.capitalizeFully(fieldValue, new char[]{'.'});
			
			return WordUtils.capitalizeFully(fieldValue);
		}
		if(methodName.equals("replaceFirst"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new RuntimeException("Service Field Method leftPad has incorrect number of parameters");
			
			return fieldValue.replaceFirst(parameters.get(0), parameters.get(1));
		}
		if(methodName.equals("replaceAll"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new RuntimeException("Service Field Method replaceAll has incorrect number of parameters");
			
			return fieldValue.replaceAll(parameters.get(0), parameters.get(1));
		}
		if(methodName.equals("toUpperCase"))
		{
			return fieldValue.toUpperCase();
		}
		if(methodName.equals("toLowerCase"))
		{
			return fieldValue.toLowerCase();
		}
		if(methodName.equals("trim"))
		{
			return fieldValue.trim();
		}
		
		throw new RuntimeException("Service Field Method not recognised");
	}
	
	
}


















