package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "Open_Subtitles_Field_Map")
public class OpenSubtitlesFieldMap extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String openSubititleField;
	
	@Column
	private String configField;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;
	
	public String getOpenSubititleField()
	{
		return openSubititleField;
	}
	public void setOpenSubititleField(String openSubititleField)
	{
		this.openSubititleField = openSubititleField;
	}
	public String getConfigField()
	{
		return configField;
	}
	public void setConfigField(String configField)
	{
		this.configField = configField;
	}
	
	public MediaConfig getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(MediaConfig parentConfig) {
		this.parentConfig = parentConfig;
	}
	
}
