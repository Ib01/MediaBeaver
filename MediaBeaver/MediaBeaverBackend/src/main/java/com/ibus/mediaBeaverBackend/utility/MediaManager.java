package com.ibus.mediaBeaverBackend.utility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibus.mediaBeaverBackend.Main;
import com.ibus.mediaBeaverCore.data.QueryTransactable;
import com.ibus.mediaBeaverCore.data.Repository;
import com.ibus.mediaBeaverCore.entity.MediaTransformConfig;
import com.ibus.mediaBeaverCore.entity.MediaType;
import com.ibus.mediaBeaverCore.entity.MovieRegEx;
import com.ibus.mediaBeaverCore.entity.TransformAction;
import com.ibus.mediaBeaverCore.util.RegExGenerator;
import org.apache.commons.io.*;

public class MediaManager 
{
	static Logger log = Logger.getLogger(Main.class.getName());
	
	public void moveMedia() 
	{
		List<MediaTransformConfig> configs = getConfigs();
		
		for(MediaTransformConfig c : configs)
		{
			if(c.getMediaType() == MediaType.Movie && c.getAction() == TransformAction.Move)
			{
				moveMovies(c);
			}
		}
		
	}

	public  static List<MediaTransformConfig> getConfigs()
	{
		log.debug("Getting configuration from h2 db");
		
		List<MediaTransformConfig> l = Repository.getInTransaction(new QueryTransactable<List<MediaTransformConfig>>()
		{
			public List<MediaTransformConfig> run()
			{
				return Repository.getAllMediaTransformConfig();
			}
		});
		
		log.debug(String.format("Retreived %d configs from db", l.size()));
		
		return l;
	}
	

	public static void moveMovies(MediaTransformConfig config)
	{
		try
		{
			iterateGet(new File(config.getTargetDirectory()),config);
		} catch (IOException e)
		{
			log.error("An exception occured while moving movies", e);
		}
	}
	
	
	private static void iterateGet(File directory, MediaTransformConfig config) throws IOException 
	{
		List<File> fileSysItems = Arrays.asList(directory.listFiles());
	
		for(File fso : fileSysItems) 
	    {
			if(fso.isDirectory())
			{
				iterateGet(fso, config);
			}
			else 
			{
				//use regular expressions to determine if this is a movie file
				if(config.getSelectExpressions().size() > 0)
				{
					RegExGenerator rex = new RegExGenerator();
					for(MovieRegEx exp : config.getSelectExpressions())
					{
						List<String> fileParts = rex.captureStrings(exp.getExpression(), fso.getName());
						String movieName = rex.assembleString(fileParts, exp.getNameParser().getAssembledItem());
						String movieYear = rex.assembleString(fileParts, exp.getYearParser().getAssembledItem());
						
						if(exp.getNameParser().hasCleaner())
							movieName = rex.cleanString(exp.getNameParser().getCleaningRegEx(), movieName, " ");
						if(exp.getYearParser().hasCleaner())
							movieYear = rex.cleanString(exp.getYearParser().getCleaningRegEx(), movieYear, " ");
						
						if(config.getRenamingService() != null)
						{
							//check the service to see if we can find the movie  
							
							//if movie not found in service then abort this expression and continue
							//continue;
						}
						

						
						
						/*File sourceFile = new File(file.getFilePath());
						
						File destinationDir = new File(destinationDirPath);
						

						if(!destinationDir.exists())
						{
							try
							{
								boolean created = destinationDir.mkdir();
								if(!created)
								{
									_log.error(file.getFileName() + " could not be moved. a parent directory could not be created for the file.");
								}
							}
							catch(SecurityException ex)
							{
								_log.error(file.getFileName() + " could not be moved. a security error occured while trying to make a directory for the file. Exception details follow", ex);
								return;
							}
						}
						
						//destination file
						String destFileName = MediaNameUtil.getMovieFileName(movie, file.getFileName());
						File destinationFile = new File(FilenameUtils.concat(destinationDirPath, destFileName));
						
						try 
						{
							FileUtils.moveFile(sourceFile, destinationFile);
					
						} catch (IOException e) 
						{
							_log.error(file.getFileName() + " could not be moved. Exception details follow", e);
							
							//clean up the destination file system
							try
							{
								boolean deleted = destinationDir.delete();
								if(!deleted)
								{
									_log.error("An attempt to cleanup the destination file system failed. the parent directory created for " + file.getFileName() + " could not be deleted");
								}
							}
							catch(SecurityException ex)
							{
								_log.error("An attempt to cleanup the destination file system failed." +
										" A security error occured while attempting to delete the parent directory created for " + file.getFileName() + ". Exception details follow", e);
								return;
							}
						}*/
						
						
						
						
					}
						
				}
				
				
				
			} 
	    }
	}
	
}










