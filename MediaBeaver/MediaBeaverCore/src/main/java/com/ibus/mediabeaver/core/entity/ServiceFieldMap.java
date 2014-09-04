package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "ServiceFieldMap")
public class ServiceFieldMap
{
	@Column
	private String appField;
	@Column
	private String appFieldAbbreviation;
	@Column
	private String TvdbField;
	@Column
	private String TmdbField;
	
	public String getAppField()
	{
		return appField;
	}
	public void setAppField(String appField)
	{
		this.appField = appField;
	}
	public String getAppFieldAbbreviation()
	{
		return appFieldAbbreviation;
	}
	public void setAppFieldAbbreviation(String appFieldAbbreviation)
	{
		this.appFieldAbbreviation = appFieldAbbreviation;
	}
	public String getTvdbField()
	{
		return TvdbField;
	}
	public void setTvdbField(String tvdbField)
	{
		TvdbField = tvdbField;
	}
	public String getTmdbField()
	{
		return TmdbField;
	}
	public void setTmdbField(String tmdbField)
	{
		TmdbField = tmdbField;
	}
	
}
