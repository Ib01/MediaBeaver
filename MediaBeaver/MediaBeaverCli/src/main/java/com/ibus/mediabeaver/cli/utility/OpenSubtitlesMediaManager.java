package com.ibus.mediabeaver.cli.utility;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.xmlrpc.XmlRpcException;

import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.OpenSubtitlesSelector;
import com.ibus.mediabeaver.core.exception.FileExistsException;
import com.ibus.mediabeaver.core.exception.FileNotExistException;
import com.ibus.mediabeaver.core.exception.MediaBeaverConfigurationException;
import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;

public class OpenSubtitlesMediaManager extends MediaManagerBase
{
	OpenSubtitlesClient client;
	ServiceFieldParser fieldParser;
	RegExHelper regExHelper;
	
	public OpenSubtitlesMediaManager(OpenSubtitlesClient client){
		this.client = client;
		fieldParser = new ServiceFieldParser();
		regExHelper = new RegExHelper();
	}

	public boolean Login() throws MalformedURLException, XmlRpcException
	{
		return client.login();
	}
	
	public boolean Logout() throws MalformedURLException, IOException, XmlRpcException
	{
		return client.logOut();
	}
	
	@Override
	protected void processFile(File file, MediaConfig config) throws IOException, XmlRpcException
	{
		//get media information from Open Subtitles Service using file thumbprint 
		OpenSubtitlesHashData fileThumbprint = OpenSubtitlesHashGenerator.computeHash(file);
		
		if(!fileThumbprint.isValid())
		{
			log.debug(String.format("Open Subtitles Thumbprint serch failed for file: %s. Could not generate valid thumbprint for file", 
					file.getAbsolutePath()));
			return;
		}
		
		OstTitleDto dto = client.getTitleForHash(fileThumbprint);
		
		if(dto.getPossibleTitles().size() == 0)
		{
			log.debug(String.format("Open Subtitles Thumbprint serch failed for file: %s. No results were returned by the service", 
					file.getAbsolutePath()));
			return;
		}
		
		//fill path tokens from the first title that has data for all our fields
		HashMap<String, String> tokens = new HashMap<String, String>(); 
		for(Map<String, String> title : dto.getPossibleTitles())
		{
			tokens = new HashMap<String, String>();
			
			//using our selectors get the values that can be used for file token paths    
			for(OpenSubtitlesSelector selector : config.getOpenSubtitlesSelectors())
			{
				//get token identifier from getOpenSubititleField. i.e get MovieYear in {MovieYear}.replaceAll(regex, replacement).
				//then get the value for that field from our service object
				String field = fieldParser.getFieldIdentifier(selector.getOpenSubititleField());
				String fieldVal = title.get(field);
				
				if(fieldVal != null && fieldVal.length() > 0)
				{
					log.debug(String.format("A raw value of %s was found for token %s ", fieldVal, selector.getPathTokenName()));
					
					//Parse the value of our service field using our parser and the methods specified in the 
					//getOpenSubititleField
					fieldVal = fieldParser.parseField(fieldVal, selector.getOpenSubititleField());	
					tokens.put(selector.getPathTokenName(), fieldVal);
					
					log.debug(String.format("The value for token %s was parsed and set to %s", selector.getPathTokenName(), fieldVal));
				}
			}
			
			//we have a value for all of our tokens.  time to format our path using the tokens and then move the file 
			if(tokens.size() == config.getOpenSubtitlesSelectors().size())
			{
				String finalPath = regExHelper.assembleFileName(tokens, config.getRelativeDestinationPath());
				MoveFile(file, config, finalPath);
				break;
			}
		}
	   
	}
	
	private void MoveFile(File file, MediaConfig config, String path)
	{
		try
		{
			fileSys.moveFile(file.getAbsolutePath(), config.getDestinationRoot(), path, FilenameUtils.getExtension(file.getAbsolutePath()));
			return;
		} 
		catch (IOException e)
		{
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		} 
		catch (FileNotExistException e){
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		} 
		catch (FileExistsException e)
		{
			log.error((String.format("file %s was NOT moved to %s/%s. An error occured while attempting to move the file.", 
					file.getAbsolutePath(), config.getDestinationRoot(), path)), e);	
		}
		
	}
	
	

}




















