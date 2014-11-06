package com.ibus.mediabeaver.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/* 
 * Valid if this path contains a path separators appropriate for its 
 * environment: / for linux. \ for windows
 */
public class EnvironmentPathValidator implements ConstraintValidator<EnvironmentPath, String> 
{

	public void initialize(EnvironmentPath arg0)
	{		
	}

	public boolean isValid(String pathField, ConstraintValidatorContext arg1)
	{
		String seperator = java.nio.file.FileSystems.getDefault().getSeparator();
		
		return (
				(seperator.equals("\\") && !pathField.contains("/")) ||
				(seperator.equals("//") && !pathField.contains("\\"))
				);
	}
 
}

