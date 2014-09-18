package com.ibus.mediabeaver.core.entity;

import java.util.ArrayList;
import java.util.List;

public class PathMethod
{
	private String Name;
	private List<String> Parameters = new ArrayList<String>();
	
	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public List<String> getParameters()
	{
		return Parameters;
	}
	public void setParameters(List<String> parameters)
	{
		Parameters = parameters;
	}
}
