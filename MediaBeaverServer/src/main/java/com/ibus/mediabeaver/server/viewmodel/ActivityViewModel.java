package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.ibus.mediabeaver.core.entity.Activity;

public class ActivityViewModel extends ViewModel
{
	private List<Activity> activities = new ArrayList<Activity>();
	private String selectedPath;
	
	//@DateTimeFormat(pattern = "dd MMMMM yyyy")
	@NotNull
	private Date earlistDate;
	
	public Date getEarlistDate() {
		return earlistDate;
	}

	public void setEarlistDate(Date earlistDate) {
		this.earlistDate = earlistDate;
	}

	public List<Activity> getActivities()
	{
		return activities;
	}

	public void setActivities(List<Activity> activities)
	{
		this.activities = activities;
	}

	public String getSelectedPath()
	{
		return selectedPath;
	}

	public void setSelectedPath(String selectedPath)
	{
		this.selectedPath = selectedPath;
	}
	
	
	/*private Date eventTime;
	private EventType eventType;
	private String sourcePath;
	private String destinationPath;
	private ResultType result;
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
	}*/
}
