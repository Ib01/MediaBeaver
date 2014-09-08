package com.ibus.mediabeaver.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class PathParser
{	
	public class Method{
		public String Name;
		public List<String> Parameters = new ArrayList<String>();
	}
	public class PathToken{
		public String WholeToken;
		public String Variable;
		public List<Method> Methods = new ArrayList<Method>();
		public String tokenValue;
	}
	
	
	public List<PathToken> parsePath(String Path)
	{
		List<PathToken> tokens = new ArrayList<PathToken>();
		
		//pulls every variable block from a string. i.e pulls {variable1}.method("foo","bar") and {variable2}.method("foo","bar") from: "some crap {variable1}.method("foo","bar") some crap. {variable2}.method("foo","bar")"
		String varaibleAndMethodsRegEx ="\\{([a-zA-Z]+)\\}(\\.(?:[a-zA-Z]+)\\((?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*,(?!\\s*\\)))*(?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*)?\\))+";
		
		Pattern varaibleAndMethodsPattern = Pattern.compile(varaibleAndMethodsRegEx);
		Matcher varaibleAndMethodsMatcher = varaibleAndMethodsPattern.matcher(Path.trim());
		
		while(varaibleAndMethodsMatcher.find())
		{
			PathToken token = new PathToken();
			token.WholeToken= varaibleAndMethodsMatcher.group(0).trim();
			token.Variable = varaibleAndMethodsMatcher.group(1).trim();
			//String varaibleAndMethods = varaibleAndMethodsMatcher.group(0);
			//String variable = varaibleAndMethodsMatcher.group(1);

			//pulls every method from variable block. i.e pulls .method1("foo","bar") and .method2("foo","bar") from: {variable}.method1("foo","bar").method2("foo","bar")
			String methodsAndParametersRegEx = "\\.([a-zA-Z]+)\\(((?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*,(?!\\s*\\)))*(?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*)?)\\)";
			
			Pattern methodsAndParametersPattern = Pattern.compile(methodsAndParametersRegEx);
			Matcher methodsAndParametersMatcher = methodsAndParametersPattern.matcher(token.WholeToken);
			
			while(methodsAndParametersMatcher.find())
			{
				Method method = new Method();
				method.Name= methodsAndParametersMatcher.group(1);
				//String methodAndParameters = methodsAndParametersMatcher.group(0);
				//String method = methodsAndParametersMatcher.group(1);
				String parameters = methodsAndParametersMatcher.group(2);
		
				//pulls parameters from a methods parameter list.  ie it extracts "foo" and "bar" from {adsf}.method("foo","bar")
				String parametersRegEx = "\"((?:[^\"]|(?<=\\\\)\")*)\"";
				
				Pattern parametersPattern = Pattern.compile(parametersRegEx);
				Matcher parametersMatcher = parametersPattern.matcher(parameters.trim());
				
				while(parametersMatcher.find())
				{
					method.Parameters.add(parametersMatcher.group(1));
					//String parameter = parametersMatcher.group(1);
				}
				
				token.Methods.add(method);
			}
		
			tokens.add(token);
		}
		
		return tokens;
	}
	
	
	
	
	
	
	
	
	
	/*
	itterate over all varaible with methods  
	\{([a-zA-Z]+)\}(\.(?:[a-zA-Z]+)\((?:\s*"(?:[^"]|(?<=\\)")*"\s*,(?!\s*\)))*(?:\s*"(?:[^"]|(?<=\\)")*"\s*)?\))+
	
	itterate over each above to get methods for each varaible  
	\.([a-zA-Z]+)\(((?:\s*"(?:[^"]|(?<=\\)")*"\s*,(?!\s*\)))*(?:\s*"(?:[^"]|(?<=\\)")*"\s*)?)\)
	
	//itterate over each methods parameters
	"((?:[^"]|(?<=\\)")*)"
	 */

	
	
	
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


















