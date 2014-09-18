package com.ibus.mediabeaver.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Event")
public class Event extends PersistentObject implements Persistable
{
	@Column
	private Date eventTime;
	@Column
	private EventType eventType;
	@Column
	private String sourcePath;
	@Column
	private String destinationPath;
	@Column
	private ResultType result;
	@Column
	private String errorDescription;
	
	public Date getEventTime()
	{
		return eventTime;
	}
	public void setEventTime(Date eventTime)
	{
		this.eventTime = eventTime;
	}
	public EventType getEventType()
	{
		return eventType;
	}
	public void setEventType(EventType eventType)
	{
		this.eventType = eventType;
	}
	public String getSourcePath()
	{
		return sourcePath;
	}
	public void setSourcePath(String sourcePath)
	{
		this.sourcePath = sourcePath;
	}
	public String getDestinationPath()
	{
		return destinationPath;
	}
	public void setDestinationPath(String destinationPath)
	{
		this.destinationPath = destinationPath;
	}
	public ResultType getResult()
	{
		return result;
	}
	public void setResult(ResultType result)
	{
		this.result = result;
	}
	public String getErrorDescription()
	{
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}
}
