package com.ibus.mediabeaver.core.entity;

import java.util.ArrayList;
import java.util.List;

public class PathToken
{
	private String pathString;
	private String name;
	private List<PathMethod> methods = new ArrayList<PathMethod>();
	private String value;
	
	public String getPathString()
	{
		return pathString;
	}
	public void setPathString(String pathString)
	{
		this.pathString = pathString;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<PathMethod> getMethods()
	{
		return methods;
	}
	public void setMethods(List<PathMethod> methods)
	{
		this.methods = methods;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
}
