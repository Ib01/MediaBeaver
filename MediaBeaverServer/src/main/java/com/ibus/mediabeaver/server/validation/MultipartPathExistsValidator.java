package com.ibus.mediabeaver.server.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.ibus.mediabeaver.core.filesystem.FileSystem;

/* 
 * Valid if the path exists on the file system. path is comprised of component paths which may be 
 * specified in one or more fields
 *  */
public class MultipartPathExistsValidator implements ConstraintValidator<MultipartPathExists, Object> 
{
	String message;
	String ownerField;
    String[] pathFields;
	
	public void initialize(MultipartPathExists annotation)
	{
		pathFields = annotation.pathComponents();
		message = annotation.message();
		ownerField = annotation.ownerField();
	}

	public boolean isValid(Object object, ConstraintValidatorContext ctx)  
	{
		try
		{
			boolean pathIsIncomplete = false;
			List<String> pathComponents = new ArrayList<String>();
			
			for(String field : pathFields)
			{
				String path = BeanUtils.getProperty(object, field);
				if(path == null || path.trim().length() == 0)
				{
					pathIsIncomplete = true;
					break;
				}
				
				pathComponents.add(path);
			}
			
			if(!pathIsIncomplete)
			{
				if(FileSystem.pathExists(pathComponents))	
					return true;
			}
			
			//set the field that the error will be displayed for  
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(message).addNode(ownerField).addConstraintViolation();
			
			return false;
		} 
		catch (IllegalAccessException e){throw new RuntimeException(e);}
		catch (InvocationTargetException e){throw new RuntimeException(e);}
		catch (NoSuchMethodException e){throw new RuntimeException(e);}

	}

	
}

