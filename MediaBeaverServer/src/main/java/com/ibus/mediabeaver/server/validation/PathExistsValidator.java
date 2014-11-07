package com.ibus.mediabeaver.server.validation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidatorContext;

import com.ibus.mediabeaver.core.filesystem.FileSystem;

/* 
 * Valid if the path exists on the file system
 */
public class PathExistsValidator implements ConstraintValidator<PathExists, Object> 
{
	String rootPathField;
    String pathField;
	String message;
    
	public void initialize(PathExists annotation)
	{
		rootPathField = annotation.rootPathField();
		pathField = annotation.pathField();
		message = annotation.message();
	}

	public boolean isValid(Object object, ConstraintValidatorContext ctx)  
	{
		try
		{
			//get the path we are validating comprised of a root path and a path
			String root = BeanUtils.getProperty(object, rootPathField);
			String path = BeanUtils.getProperty(object, pathField);
			
			//validate the path
			FileSystem fs = new FileSystem();
			if(fs.pathExists(root, path))	
				return true;
		} 
		catch (IllegalAccessException e){throw new RuntimeException(e);}
		catch (InvocationTargetException e){throw new RuntimeException(e);}
		catch (NoSuchMethodException e){throw new RuntimeException(e);}

		//set the field that the error will be displayed for  
		ctx.disableDefaultConstraintViolation();
		ctx.buildConstraintViolationWithTemplate(message).addNode(pathField).addConstraintViolation();
		
		return false;
	}

	
}

