package com.ibus.mediabeaver.core.entity;

import java.util.Date;

public interface Persistable 
{
	public String getId();
	public int getVersion();
	public Date getLastUpdate();
}
