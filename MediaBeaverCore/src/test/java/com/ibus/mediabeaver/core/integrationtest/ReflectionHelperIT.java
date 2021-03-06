package com.ibus.mediabeaver.core.integrationtest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.ibus.mediabeaver.core.entity.ServiceFieldMap;
import com.ibus.mediabeaver.core.util.ReflectionHelper;

public class ReflectionHelperIT
{

	@Test
	public void callGetterTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		ServiceFieldMap dto = new ServiceFieldMap();
		dto.setAppField("Test");
		
		String fieldValue = ReflectionHelper.callGetter(dto, "com.ibus.mediabeaver.core.entity.ServiceFieldMap", "getAppField");
		
		assertTrue(dto.getAppField().equals(fieldValue));
	}
}
