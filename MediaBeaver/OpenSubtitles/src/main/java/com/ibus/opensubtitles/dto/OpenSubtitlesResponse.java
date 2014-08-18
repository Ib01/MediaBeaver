package com.ibus.opensubtitles.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovieTitle")
public class OpenSubtitlesResponse
{
	private String name = "";
	private String year = "";

	public OpenSubtitlesResponse()
	{
	}

	public OpenSubtitlesResponse(String name, String year)
	{
		this.setName(name);
		this.setYear(year);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public String getYear()
	{
		return year;
	}

	
}
