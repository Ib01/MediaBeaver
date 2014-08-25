package com.ibus.mediabeaver.server.viewmodel;


public class OpenSubtitlesSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	private String openSubititleField;
	private String pathTokenName;
	
	public OpenSubtitlesSelectorViewModel(){}
	
	
	public OpenSubtitlesSelectorViewModel(String pathTokenName)
	{
		this.pathTokenName = pathTokenName;
	}
	
	public OpenSubtitlesSelectorViewModel(String pathTokenName, String openSubititleField)
	{
		this.openSubititleField = openSubititleField;
		this.pathTokenName = pathTokenName;
	}
	
	public String getOpenSubititleField()
	{
		return openSubititleField;
	}
	public void setOpenSubititleField(String openSubititleField)
	{
		this.openSubititleField = openSubititleField;
	}
	public String getPathTokenName()
	{
		return pathTokenName;
	}
	public void setPathTokenName(String configField)
	{
		this.pathTokenName = configField;
	}
}
