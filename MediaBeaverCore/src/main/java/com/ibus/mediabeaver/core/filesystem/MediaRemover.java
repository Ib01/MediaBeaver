package com.ibus.mediabeaver.core.filesystem;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.data.UpdateTransactable;
import com.ibus.mediabeaver.core.entity.Event;
import com.ibus.mediabeaver.core.entity.EventType;
import com.ibus.mediabeaver.core.entity.ResultType;

public class MediaRemover extends SimpleFileVisitor<Path>
{
	protected Logger log = Logger.getLogger(MediaRemover.class.getName());
	
	public void deleteFiles(List<String> pathStrings) throws IOException
	{
		for(String p : pathStrings)
		{
			deleteFile(p);
		}
	}
	
	public void deleteFile(String filePath) 
	{
		Path path = Paths.get(filePath);
		try
		{
			Files.walkFileTree(path, this);
		} catch (IOException e)
		{
		}
	}
	
	 @Override
     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
             throws IOException
     {
		 delete(file);
         return FileVisitResult.CONTINUE;
     }

     @Override
     public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
     {
         // try to delete the file anyway, even if its attributes
         // could not be read, since delete-only access is
         // theoretically possible
    	 delete(file);
         return FileVisitResult.CONTINUE;
     }

     @Override
     public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
     {
         if (exc == null)
         {
        	 delete(dir);
            return FileVisitResult.CONTINUE;
         }
         else
         {
        	 //if we did not manage to delete any sub file then abort
        	 return FileVisitResult.TERMINATE;
         }
     }
     
     
     
     private void delete(Path path)
     {	 
    	try
		{
			Files.deleteIfExists(path);
			logEvent(path.toString(), null, ResultType.Succeeded, "File or directory deleted successfully");
			
		} catch (IOException e)
		{
			log.error(String.format("An error occured while deleting the file %s", path.toString()), e);	
			logEvent(path.toString(), null, ResultType.Failed, "An error occured while deleting the file");
		}
     }
     
     
     private void logEvent(String source, String destination, ResultType result, String errorDescription)
 	{
 		Event event = new Event();
 		
 		event.setEventTime(new Date());
 		event.setEventType(EventType.Delete);
 		event.setDestinationPath(destination);
 		event.setResult(result);
 		event.setSourcePath(source);
 		event.setErrorDescription(errorDescription);
 		
 		logEvent(event);
 	}
     
     protected void logEvent(final Event event)
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











