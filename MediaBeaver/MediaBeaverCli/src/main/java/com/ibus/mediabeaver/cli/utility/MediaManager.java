package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariableSetter;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;
import com.ibus.mediabeaver.core.util.RegExHelper;

public class MediaManager
{
	private Logger log = Logger.getLogger(Main.class.getName());
	private RegExHelper regExHelper = new RegExHelper();
	private FileSystem fileSys = new FileSystem();
	
	public void processConfigs(List<MediaConfig> configs)
	{
		for (MediaConfig c : configs)
		{
			log.debug(String.format("processing config with description: %s", c.getDescription()));
			
			try
			{
				iterateGet(new File(c.getSourceDirectory()), c);
			} catch (IOException e)
			{
				log.error("An exception occured while moving movies", e);
			}

		}

	}

	private void iterateGet(File directory, MediaConfig config)
			throws IOException
	{
		List<File> fileSysObjects = Arrays.asList(directory.listFiles());

		for (File fso : fileSysObjects)
		{
			log.debug(String.format("Inspecting file system object: %s", fso.getPath()));
			
			if (fso.isDirectory())
			{
				iterateGet(fso, config);
			} 
			else
			{
				
				/*
				 * if deleting 
				 * 	
				 * 
				 * if new File Name does not have variables in it
				 * 		move or delete file
				 * else 
				 * 		if all variables are set 
				 * 			 
				*/
				
				if(!fileIsTarget(fso, config))
					continue;
	
				if (config.getAction() == TransformAction.Move)
				{
					String fileName = regExHelper.assembleFileName(config.getConfigVariables(), config.getRelativeDestinationPath());
					fileSys.moveFile(fso.getAbsolutePath(), config.getDestinationRoot(), fileName);
				}

			}
		}
	}

	private boolean fileIsTarget(File fso, MediaConfig config)
	{
		return processRegExSelectors(fso, config);
	}

	private boolean processRegExSelectors(File fso, MediaConfig config)
	{
		log.debug("processing regex selectors");
		boolean isTarget = false;
		
		/*go through each regex selectors*/
		for(RegExSelector selector : config.getRegExSelectors())
		{
			log.debug(String.format("Matching regex %s against file name: %s", selector.getExpression(), fso.getName()));
			
			/*capture substrings from file name*/
			List<String> captures = regExHelper.captureStrings(selector.getExpression(), fso.getName());
			if(captures.size() > 0)
			{
				log.debug(String.format("regex %s matched file name: %s", selector.getExpression(), fso.getName()));
				
				isTarget = true;
				
				/*populate our config variables form the getRegExVariables list*/
				for(RegExVariableSetter rev : selector.getVariables())
				{
					/*ConfigVariable cv = getConfigVariable(config.getConfigVariables(), rev.getConfigVariable().getName());
					if(cv == null){
						 //our UI should ensure this should never happens
						log.error("An exception occured: no corresponding ConfigVariable for RegExVariable");
						throw new MediaBeaverConfigurationException("An exception occured: no corresponding ConfigVariable for RegExVariable");
					}*/
					
					String uncleanVariable = regExHelper.assembleRegExVariable(captures, rev.getGroupAssembly());	
					String cleanVariable = regExHelper.cleanString(uncleanVariable, rev.getReplaceExpression(), rev.getReplaceWithCharacter());
				
					//TODO: CHECK THAT THE CONFIG OBJECT HOLDS A REFERENCE TO THIS getConfigVariable AND NOT SOME OTHER INSTACE!!!!!
					rev.getConfigVariable().setValue(cleanVariable);
					
					log.debug(String.format("config variable %s was set to %s", rev.getConfigVariable().getName(), rev.getConfigVariable().getValue()));
					
					//cv.setValue(cleanVariable);
					    
					//log.debug(String.format("config variable %s was set to %s", cv.getName(), cv.getValue()));
				}
				
				/*
				 * if new File Name does not have variables in it  
				*/
				
			}
		}
		
		/*
			each RegExSelector
				Iron Man (2013).mkv
				Iron Man
				2013
				mkv
		
		
		*/
		
		return isTarget;
	}
	
	/*private ConfigVariable getConfigVariable(Set<ConfigVariable> variables, String variableName)
	{
		for(ConfigVariable v : variables)
		{
			if(v.getName().equals(variableName))
				return v;
		}
		
		return null;
	}
	*/
	
	
	
}








































