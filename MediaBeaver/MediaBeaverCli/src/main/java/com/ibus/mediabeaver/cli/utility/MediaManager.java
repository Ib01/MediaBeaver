package com.ibus.mediabeaver.cli.utility;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.FindResults;

import com.ibus.mediabeaver.core.entity.MediaConfig2;
import com.ibus.mediabeaver.core.entity.ServiceFieldMap;
import com.ibus.opensubtitles.client.dto.OstTitleDto;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesField;
import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;
import com.ibus.opensubtitles.client.utilities.OpenSubtitlesHashGenerator;

import info.movito.themoviedbapi.TmdbFind;


public class MediaManager extends MediaManagerBase2
{	
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	
	protected static TmdbApi tmdbApi;
	List<ServiceFieldMap> serviceFieldMaps;
	ServiceFieldParser fieldParser; //TODO: keep?
	RegExHelper regExHelper;//TODO: keep?
	
	
	public MediaManager(List<ServiceFieldMap> serviceFieldMaps, MediaConfig2 config)
	{
		this.openSubtitlesClient = openSubtitlesClient;
		this.config = config;
		this.serviceFieldMaps = serviceFieldMaps;
		
		tmdbApi = new TmdbApi(tmdbApiKey);
		/*fieldParser = new ServiceFieldParser();
		regExHelper = new RegExHelper();*/
	}

	
	
	@Override
	protected void processFile(File file) 
	{
		Map<String,String> ostTitle = getOpenSubtitlesTitle(file);
		if(ostTitle == null)
			return;
		
		FindResults result;
		if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("episode"))
		{
			//I am guessing that these are stored as number in the open subtitles db. gop figure.
			String imdbId = "tt" + StringUtils.leftPad(ostTitle.get(OpenSubtitlesField.SeriesIMDBParent.toString()), 7, "0");
			result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);		
			
			//DOES NOT RETURN ANYTHING UNDER SEASONS  
		}
		else if(ostTitle.get(OpenSubtitlesField.MovieKind.toString()).equals("movie"))
		{
			//I am guessing that these are stored as number in the open subtitles db. gop figure.
			String imdbId = "tt" + StringUtils.leftPad(ostTitle.get(OpenSubtitlesField.IDMovieImdb.toString()), 7, "0");
			result = tmdbApi.getFind().find(imdbId, TmdbFind.ExternalSource.imdb_id, null);
			
			ostTitle = null;
		}
		

		ostTitle = null;
		

		//tt0090605
		//tt1480055
		//tt1480055
		//tt1480055
		//tt0944947
		
		/*//fill path tokens from the first title that has data for all our fields
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
		}*/
	   
	}
	
	/*private void MoveFile(File file, MediaConfig config, String path)
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
		
	}*/
	
	
	
	private Map<String,String> getOpenSubtitlesTitle(File file) 
	{
		//get media information from Open Subtitles Service using file thumbprint 
		OpenSubtitlesHashData fileThumbprint;
		try
		{
			fileThumbprint = OpenSubtitlesHashGenerator.computeHash(file);
			
			if(!fileThumbprint.isValid())
			{
				log.debug(String.format("File %s could not be moved. thumbprint for file is invalid", file.getAbsolutePath()));
				return null;
			}
		} 
		catch (IOException e)
		{
			log.debug(String.format("File %s could not be moved. could not acquire a thumbprint for the file", file.getAbsolutePath()));
			return null;
		}
		
		
		OstTitleDto dto = null;
		try
		{
			dto = openSubtitlesClient.getTitleForHash(fileThumbprint);
			
			if(dto == null || dto.getPossibleTitles().size() == 0)
			{
				log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
						file.getAbsolutePath()));
				return null;
			}
			
			return dto.getFirstMovieOrEpisodeTitleWithImdb();
		} 
		catch (Exception e)
		{
			log.debug(String.format("Open Subtitles thumbprint search on file %s failed. No results were returned by the service", 
					file.getAbsolutePath()));
			return null;
		}
		
	}
	

}




















