package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Open_Subtitles_Selector")
public class OpenSubtitlesSelector extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private OpenSubtitlesField openSubititleField;
	
	@Column
	private String pathTokenName;
	
	/*@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;*/
	
	public OpenSubtitlesField getOpenSubititleField()
	{
		return openSubititleField;
	}
	public void setOpenSubititleField(OpenSubtitlesField openSubititleField)
	{
		this.openSubititleField = openSubititleField;
	}
	public String getPathTokenName()
	{
		return pathTokenName;
	}
	public void setPathTokenName(String name)
	{
		this.pathTokenName = name;
	}
	

}
