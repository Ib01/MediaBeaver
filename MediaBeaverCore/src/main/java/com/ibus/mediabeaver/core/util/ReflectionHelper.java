package com.ibus.mediabeaver.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper
{
	
	public static <T> T callGetter(Object object, String fullyQualifiedClassName, String methodName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		Class noparams[] = {};
		Class cls = Class.forName(fullyQualifiedClassName);		
		Method method = cls.getDeclaredMethod(methodName, noparams);
		
		T methodValue = (T) method.invoke(object, null);
		return methodValue; 
	}
}
