package com.ibus.mediabeaver.server.viewmodel;

import java.io.Serializable;
import java.util.Date;

public class ViewModel implements Serializable
{
	
	protected  String id;
	protected Date lastUpdate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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
