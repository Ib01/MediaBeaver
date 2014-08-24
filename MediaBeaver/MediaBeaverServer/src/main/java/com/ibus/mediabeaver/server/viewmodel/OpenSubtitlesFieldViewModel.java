package com.ibus.mediabeaver.server.viewmodel;

public class OpenSubtitlesFieldViewModel extends ViewModel
{	
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private String Description;

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public String getDescription()
	{
		return Description;
	}

	public void setDescription(String description)
	{
		Description = description;
	}
}
