package com.ibus.mediabeaver.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ibus.mediabeaver.core.filesystem.FileSystem;

/* 
 * Valid if the path exists on the file system
 */
public class PathExistsValidator implements ConstraintValidator<PathExists, String> 
{

	public void initialize(PathExists arg0)
	{
	}

	public boolean isValid(String fieldValue, ConstraintValidatorContext arg1)
	{
		//TODO :need to somehow get the root path?
		
		FileSystem fs = new FileSystem();
		fs.pathExists(fieldValue);
		
		return false;
	}

	
}

