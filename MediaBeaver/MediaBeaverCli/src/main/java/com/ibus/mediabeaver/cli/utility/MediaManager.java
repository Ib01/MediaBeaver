package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.ibus.mediabeaver.cli.Main;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExPathTokenSetter;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.RegExHelper;

/*TODO:
 * 
 * 1) Capitalise file tokens 
 * 
 * */

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
				 * 	if new File Name does not have variables in it
				 * 		move or delete file
				 * 	else 
				 * 		if all variables are set 
				 * 
				 * if moving 
				 * 
				 * 	if destination path has no tokens then 
				 * 		move x from p to q  with no change of file name
				 * 
				 * 	if destination path has tokens
				 * 		if we have nominated use of open subtitles service then 
				 * 			get tokens from the service
				 * 		else
				 * 			get tokens from selectors	
				 * 
				 * 		move file using the tokes in the file name
				 * */
				
				if (config.getAction() == TransformAction.Move)
				{
					
					if(regExHelper.containsTokenPlaceholders(config.getRelativeDestinationPath()))
					{
						log.debug(String.format("Destination path %s contains file token placeholders that will need to be resolved before the file can be processed", config.getRelativeDestinationPath()));
						
						//if we have nominated use of open subtitles service then 
						//		get tokens from the service
						//else 	 
						
						HashMap<String, String> tokens = getFileTokensFromRegexSelectors(fso, config);
						if(tokens.size() > 0)
						{
							String fileName = regExHelper.assembleFileName(tokens, config.getRelativeDestinationPath());
							
							//if we still have placehonders in the file name abort move 
							if(!regExHelper.containsTokenPlaceholders(fileName))
							{
								log.debug(String.format("Attempting to move %s to %s", fso.getPath(), FilenameUtils.concat(config.getDestinationRoot(), fileName)));
								fileSys.moveFile(fso.getAbsolutePath(), config.getDestinationRoot(), fileName);	
							}
							else
							{
								log.debug(String.format("Aborting move of %s. could not assemble new filename for the file. Data could not be found for all of the file token placeholders.", 
										fso.getPath()));
							}
						}
						else
						{
							log.debug(String.format("Aborting movement of %s. Tokens could not be found to populate the token placeholders present in the destination path", 
									fso.getPath()));
						}
						
					}
					//else
					//...
				}
			}
		}
	}

	
	/**
	 * foreach selector if expression is found in file name then 
	 * get token collection.  if a null is returned then no 
	 * match found for any selector.  
	 * @param fso
	 * @param config
	 * @return
	 */
	private HashMap<String, String> getFileTokensFromRegexSelectors(File fso, MediaConfig config)
	{
		log.debug("processing regex selectors");
		HashMap<String, String> tokens = new HashMap<String, String>();
		
		/*go through each regex selectors*/
		for(RegExSelector selector : config.getRegExSelectors())
		{
			log.debug(String.format("Processing regex selctor with description: %s.", selector.getDescription()));
			log.debug(String.format("Matching regex %s against file name: %s", selector.getExpression(), fso.getName()));

			/*capture substrings from file name. These substrings will be used to create the final file name.*/
			List<String> captures = regExHelper.captureStrings(selector.getExpression(), fso.getName());
			if(captures.size() > 0)
			{
				log.debug(String.format("regex %s matched file name: %s", selector.getExpression(), fso.getName()));
				
				/*populate our token setters*/
				for(RegExPathTokenSetter setter : selector.getPathTokenSetters())
				{
					String tokenValue = regExHelper.assembleFileToken(captures, setter.getGroupAssembly());
					if(regExHelper.containsCaptureGroup(tokenValue))
					{
						log.debug(String.format("Aborting match against regex selctor. the selector %s has an invalid value for the token setter with name: %s"
								, selector.getDescription(), setter.getPathTokenName()));
						tokens.clear();
						break; //we have an invalid token which contains an unassigned token.ie the string contains something like: {1}. 
							   //go to next selector
					}
					
					tokenValue = regExHelper.cleanFileToken(tokenValue, setter.getReplaceExpression(), setter.getReplaceWithCharacter());
					if(tokenValue.trim().length() == 0)
					{
						//don't think this should ever happen.
						log.debug(String.format("Aborting match against regex selctor: %s. File token setter %s, is 0 length."
								, selector.getDescription(), setter.getPathTokenName()));
						tokens.clear();
						break; 
					}
					
					tokens.put(setter.getPathTokenName(), tokenValue);
				}
				
				//if tokens = null then selector matched but we do not have a valid list of tokens so go to next selector.  
				//otherwise so just return our token list to indicate a successful match
				if(tokens.size() > 0)
					return tokens;
			}
			else
			{
				log.debug(String.format("Either regex %s did not match on file name %s, or the regular expression did not define any capture groups", 
						selector.getExpression(), fso.getName()));
			}
		}
		
		return tokens;
	}
	
	
	
	
	
}








































