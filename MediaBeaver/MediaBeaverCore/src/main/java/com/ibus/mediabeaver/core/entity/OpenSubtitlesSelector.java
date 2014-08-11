package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity(name = "Open_Subtitles_Field_Map")
public class OpenSubtitlesSelector extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String openSubititleField;
	
	@Column
	private String pathTokenName;
	
	/*@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;*/
	
	public String getOpenSubititleField()
	{
		return openSubititleField;
	}
	public void setOpenSubititleField(String openSubititleField)
	{
		this.openSubititleField = openSubititleField;
	}
	/*public String getConfigField()
	{
		return configField;
	}
	public void setConfigField(String configField)
	{
		this.configField = configField;
	}*/
	public String getPathTokenName()
	{
		return pathTokenName;
	}
	public void setPathTokenName(String name)
	{
		this.pathTokenName = name;
	}
	

}
