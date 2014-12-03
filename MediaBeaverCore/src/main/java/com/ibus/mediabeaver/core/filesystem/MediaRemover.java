package com.ibus.mediabeaver.core.filesystem;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;

public class MediaRemover extends SimpleFileVisitor<Path>
{
	protected Logger log = Logger.getLogger(MediaRemover.class.getName());
	boolean deleteSuccess;
	private List<Activity> deleted = new ArrayList<Activity>();
	private List<Activity> notDeleted = new ArrayList<Activity>();
	
	public List<Activity> getNotDeleted()
	{
		return notDeleted;
	}
	public void setNotDeleted(List<Activity> notDeleted)
	{
		this.notDeleted = notDeleted;
	}
	public List<Activity> getDeleted()
	{
		return deleted;
	}
	public void setDeleted(List<Activity> deleted)
	{
		this.deleted = deleted;
	}

	public boolean deleteFiles(List<String> pathStrings) throws IOException
	{
		for(String p : pathStrings)
		{
			if(!doDelete(p))
				return false;
		}
		
		return true;
	}
	
	public boolean deleteFile(String pathString) 
	{
		return doDelete(pathString);
	}
	
	public boolean doDelete(String filePath) 
	{
		deleteSuccess = true;
		Path path = Paths.get(filePath);
		try
		{
			Files.walkFileTree(path, this);
		} catch (IOException e)
		{
			log.error(String.format("An error occured while deleting the file %s", path.toString()), e);
			logEvent(path.toString(), null, ResultType.Failed, "An error occured while deleting the file. please check the log for details.");
			deleteSuccess = false;
		}
		
		return deleteSuccess;
	}
	
	 @Override
     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
             throws IOException
     {
		if(!delete(file))
		{
			deleteSuccess = false;
			return FileVisitResult.TERMINATE;
		}
		return FileVisitResult.CONTINUE;
     }

     @Override
     public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
     {
        // try to delete the file anyway, even if its attributes
        // could not be read, since delete-only access is
        // theoretically possible
    	if(!delete(file))
		{
			deleteSuccess = false;
			return FileVisitResult.TERMINATE;
		}
    	 
        return FileVisitResult.CONTINUE;
     }

     @Override
     public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
     {
         if (exc == null)
         {
        	if(!delete(dir))
        	{
        		deleteSuccess = false;
        		return FileVisitResult.TERMINATE;
        	}
            return FileVisitResult.CONTINUE;
         }
         else
         {
        	 //if we did not manage to delete any sub file then abort
        	 return FileVisitResult.TERMINATE;
         }
     }
     
     
     
     private boolean delete(Path path)
     {	 
    	try
		{
			Files.deleteIfExists(path);
			logEvent(path.toString(), null, ResultType.Succeeded, "File or directory deleted successfully");
			return true;
			
		} catch (IOException e)
		{
			log.error(String.format("An error occured while deleting the file %s", path.toString()), e);	
			logEvent(path.toString(), null, ResultType.Failed, "An error occured while deleting the file. please check the log for details.");
		}
    	
    	return false;
     }
     
     
     private void logEvent(String source, String destination, ResultType result, String errorDescription)
 	{
 		Activity event = new Activity();
 		
 		event.setEventTime(new Date());
 		event.setEventType(EventType.Delete);
 		event.setDestinationPath(destination);
 		event.setResult(result);
 		event.setSourcePath(source);
 		event.setErrorDescription(errorDescription);
 		
 		logEvent(event);
 		
 		if(result == ResultType.Failed)
 			notDeleted.add(event);
 		else
 			deleted.add(event);
 	}
     
     protected void logEvent(final Activity event)
 	{
 		//if we are calling this class from the web ui we will already have a transaction
 		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
 		Transaction t = s.getTransaction();
 		
 		if(t.isActive()){
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











