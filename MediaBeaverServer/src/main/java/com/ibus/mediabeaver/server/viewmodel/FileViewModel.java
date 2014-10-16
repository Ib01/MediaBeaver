package com.ibus.mediabeaver.server.viewmodel;

public class FileViewModel
{
	private String name;
	private boolean isFile;
	private boolean selected;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isFile()
	{
		return isFile;
	}
	public void setFile(boolean isFile)
	{
		this.isFile = isFile;
	}
	
	public boolean getSelected()
	{
		return selected;
	}
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

}
