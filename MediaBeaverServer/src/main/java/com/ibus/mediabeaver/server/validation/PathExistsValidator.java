package com.ibus.mediabeaver.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ibus.mediabeaver.core.filesystem.FileSystem;

/* 
 * Valid if the path exists on the file system. path is comprised of component paths which may be 
 * specified in one or more fields
 *  */
public class PathExistsValidator implements ConstraintValidator<PathExists, String> 
{
	public void initialize(PathExists annotation)
	{
	}

	public boolean isValid(String path, ConstraintValidatorContext ctx)  
	{
		if(path == null || path.length() == 0)
			return false;
		
		return FileSystem.pathExists(path);	

	}
	
}

