package com.ibus.mediabeaver.server.viewmodel;

import javax.persistence.Column;

public class OpenSubtitlesFieldMapViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	private String openSubititleField;
	private String configField;
	
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
}
