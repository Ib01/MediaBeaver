package com.ibus.mediabeaver.core.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.ibus.mediabeaver.core.entity.PathMethod;
import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.PathParseException;

public class PathParser
{
	private static String TokensExistRegEx ="\\{([a-zA-Z]+)\\}";
	private static String VaraibleRegEx ="\\{([a-zA-Z]+)\\}(?!\\.)";
	//pulls every variable block from a string. i.e pulls {variable1}.method("foo","bar") and {variable2}.method("foo","bar") from: "some crap {variable1}.method("foo","bar") some crap. {variable2}.method("foo","bar")"
	private static String VaraibleAndMethodsRegEx ="\\{([a-zA-Z]+)\\}(\\.(?:[a-zA-Z]+)\\((?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*,(?!\\s*\\)))*(?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*)?\\))+";
	//pulls every method from variable block. i.e pulls .method1("foo","bar") and .method2("foo","bar") from: {variable}.method1("foo","bar").method2("foo","bar")
	private static String MethodsAndParametersRegEx = "\\.([a-zA-Z]+)\\(((?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*,(?!\\s*\\)))*(?:\\s*\"(?:[^\"]|(?<=\\\\)\")*\"\\s*)?)\\)";
	//pulls parameters from a methods parameter list.  ie it extracts "foo" and "bar" from {variable1}.method("foo","bar")
	private static String ParametersRegEx = "\"((?:[^\"]|(?<=\\\\)\")*)\"";
	
	/**
	 * Get all Varaiables, their methods and each methods parameters from a path.  i.e turn 
	 * some rubbish {variable1}.method("foo","bar") some rubbish. into 
	 *  PathToken{
	 *  Variable: "variable1", Methods: {Name: "method", Parameters {"foo", "bar"}
	 *  }
	 * @param path
	 * @return
	 */
	public static List<PathToken> getTokens(String path)
	{
		path = path.trim();
		
		List<PathToken> tokens = getTokensWithParameters(new ArrayList<PathToken>(),path);
		tokens = getTokensWithoutParameters(tokens,path);
		
		return tokens;
	}
	
	
	/**
	 * Parse a path variable into a value using its methods
	 * @return
	 * @throws PathParseException 
	 */
	public static PathToken parseToken(PathToken token, String value) throws PathParseException
	{
		for(PathMethod method : token.getMethods())
		{
			value = executeMethod(value, method.getName(), method.getParameters());
		}
		
		token.setValue(value);
		return token;
	}
	
	/**
	 * Parse a path using a PathToken
	 * @param token
	 * @param value
	 * @return
	 */
	public static String parsePath(PathToken token, String path)
	{
		return path.replace(token.getPathString(), token.getValue());
	}
	
	
	/**
	 * determines whether a path contains tokens such as 
	 * {{MovieName}} ({{MovieYear}})\{{MovieName}} ({{MovieYear}}).  
	 * @param text
	 * @return
	 */
	public static boolean containsTokens(String path)
	{
		Pattern pattern = Pattern.compile(TokensExistRegEx);
		Matcher matcher = pattern.matcher(path);
		
		return matcher.find();
	}
	
	
	
	
	private static List<PathToken> getTokensWithoutParameters(List<PathToken> tokens,String path)
	{
		//Process tokens without methods.
		Pattern varaiblePattern = Pattern.compile(VaraibleRegEx);
		Matcher varaibleMatcher = varaiblePattern.matcher(path.trim());
		
		while(varaibleMatcher.find())
		{
			PathToken token = new PathToken();
			token.setPathString(varaibleMatcher.group(0).trim());
			token.setName(varaibleMatcher.group(1).trim());
			
			tokens.add(token);
		}
		
		return tokens;
	}
	
	
	private static List<PathToken> getTokensWithParameters(List<PathToken> tokens,String path)
	{
		//Process tokens with methods.
		Pattern varaibleAndMethodsPattern = Pattern.compile(VaraibleAndMethodsRegEx);
		Matcher varaibleAndMethodsMatcher = varaibleAndMethodsPattern.matcher(path);
		
		while(varaibleAndMethodsMatcher.find())
		{
			PathToken token = new PathToken();
			token.setPathString(varaibleAndMethodsMatcher.group(0).trim());
			token.setName(varaibleAndMethodsMatcher.group(1).trim());

			Pattern methodsAndParametersPattern = Pattern.compile(MethodsAndParametersRegEx);
			Matcher methodsAndParametersMatcher = methodsAndParametersPattern.matcher(token.getPathString());
			
			while(methodsAndParametersMatcher.find())
			{
				PathMethod method = new PathMethod();
				method.setName(methodsAndParametersMatcher.group(1));
				String parameters = methodsAndParametersMatcher.group(2);

				Pattern parametersPattern = Pattern.compile(ParametersRegEx);
				Matcher parametersMatcher = parametersPattern.matcher(parameters.trim());
				
				while(parametersMatcher.find())
				{
					method.getParameters().add(parametersMatcher.group(1));
				}
				token.getMethods().add(method);
			}
			tokens.add(token);
		}
		
		return tokens;
	}
	
	private static String executeMethod(String fieldValue, String methodName, List<String> parameters) throws PathParseException
	{
		if(methodName.equals("normalizeSpace"))
		{
			return StringUtils.normalizeSpace(fieldValue);
		}
		if(methodName.equals("leftPad"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new PathParseException("Service Field Method leftPad has incorrect number of parameters");
			
			if(!StringUtils.isNumeric(parameters.get(0)))
				throw new PathParseException("Service Field Method leftPad has an incorrect parameter: parameter 1 must be numeric");
			
			return StringUtils.leftPad(fieldValue, Integer.parseInt(parameters.get(0)), parameters.get(1));
		}
		if(methodName.equals("rightPad"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new PathParseException("Service Field Method rightPad has incorrect number of parameters");
			
			if(!StringUtils.isNumeric(parameters.get(0)))
				throw new PathParseException("Service Field Method rightPad has an incorrect parameter: parameter 1 must be numeric");
			
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
				throw new PathParseException("Service Field Method leftPad has incorrect number of parameters");
			
			return fieldValue.replaceFirst(parameters.get(0), parameters.get(1));
		}
		if(methodName.equals("replaceAll"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new PathParseException("Service Field Method replaceAll has incorrect number of parameters");
			
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
		if(methodName.equals("substring"))
		{
			if(parameters == null || parameters.size() != 2)
				throw new PathParseException("Service Field Method substring has incorrect number of parameters");
			if(!StringUtils.isNumeric(parameters.get(0)))
				throw new PathParseException("Service Field Method substring has an incorrect parameter: parameter 1 must be numeric");
			if(!StringUtils.isNumeric(parameters.get(1)))
				throw new PathParseException("Service Field Method substring has an incorrect parameter: parameter 2 must be numeric");
			
			return fieldValue.substring(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
		}
		
		throw new PathParseException(String.format("Method token %s not recognised", methodName));
	}
	
}


















