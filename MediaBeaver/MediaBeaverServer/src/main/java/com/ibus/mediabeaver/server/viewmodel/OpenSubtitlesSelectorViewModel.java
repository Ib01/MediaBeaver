package com.ibus.mediabeaver.server.viewmodel;


public class OpenSubtitlesSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	private OpenSubtitlesFieldViewModel openSubititleField;
	private String pathTokenName;
	
	public OpenSubtitlesSelectorViewModel(){}
	
	
	public OpenSubtitlesSelectorViewModel(String pathTokenName)
	{
		this.pathTokenName = pathTokenName;
	}
	
	public OpenSubtitlesSelectorViewModel(String pathTokenName, OpenSubtitlesFieldViewModel openSubititleField)
	{
		this.openSubititleField = openSubititleField;
		this.pathTokenName = pathTokenName;
	}
	
	public OpenSubtitlesFieldViewModel getOpenSubititleField()
	{
		return openSubititleField;
	}
	public void setOpenSubititleField(OpenSubtitlesFieldViewModel openSubititleField)
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
