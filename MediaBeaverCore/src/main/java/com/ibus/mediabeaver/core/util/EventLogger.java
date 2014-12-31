package com.ibus.mediabeaver.core.util;

import java.util.Date;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;

public class EventLogger 
{
	Platform platform;
	
	public EventLogger(Platform platform)
	{
		this.platform = platform;
	}

	public Activity logEvent(String source, String destination, ResultType result, String errorDescription)
	{
		Activity event = new Activity();
		
		event.setEventTime(new Date());
		event.setEventType(EventType.Move);
		event.setDestinationPath(destination);
		event.setResult(result);
		event.setSourcePath(source);
		event.setErrorDescription(errorDescription);
		
		logEvent(event);
		
		return event;
	}
	
	public void logEvent(final Activity event)
	{

		if(platform == Platform.Web)
		{
			Repository.saveEntity(event);
			return;
		}
		
		//otherwise we are calling from the cli
		Repository.doInTransaction(
			new UpdateTransactable(){
				public void run()
				{
					Repository.saveEntity(event);
				}
			});
	}
}
