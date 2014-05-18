package com.ibus.mediabeaver.server.viewmodel;

import java.io.Serializable;
import java.util.Date;

public class ViewModel implements Serializable
{
	private String id;
	private int version = 0;
	private Date lastUpdate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}
}
