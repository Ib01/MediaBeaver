package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Open_Subtitles_Field")
public class OpenSubtitlesField extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String Name;

	@Column
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
