package com.ibus.mediabeaver.cli.test;

import static org.junit.Assert.*;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.model.FindResults;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.Before;
import org.junit.Test;

import com.ibus.mediabeaver.cli.utility.MediaManager;
import com.ibus.mediabeaver.core.entity.MediaConfig2;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;

public class MediaManagerTests
{
	
	
	@Before
	public void beforeTest()
	{
		refreshTestDirs();
	}
	
	private void refreshTestDirs()
	{
		try
		{
			/*recreate destination dir*/
			FileUtils.deleteDirectory(new File("D:\\MediabeaverTests\\Destination"));
			FileUtils.copyDirectory(new File("D:\\MediabeaverTests\\Destination - Copy"), new File("D:\\MediabeaverTests\\Destination"));
			
			/*recreate source dir*/
			FileUtils.copyDirectory(new File("D:\\MediabeaverTests\\Source - Copy"), new File("D:\\MediabeaverTests\\Source"));
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void processConfigsTest() throws XmlRpcException, IOException
	{
		MediaConfig2 c = new MediaConfig2();
		c.setSourceDirectory("D:\\MediabeaverTests\\Source");
	
		MediaManager mm = new MediaManager(null, c);
		mm.moveFiles();
		
		
		assertTrue(true);
	}
	
	
	@Test
	public void wtfTest() throws XmlRpcException, IOException
	{
		String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
		TmdbApi tmdbApi = new TmdbApi(tmdbApiKey);
		
		FindResults result = tmdbApi.getFind().find("tt0944947", TmdbFind.ExternalSource.imdb_id, null);
		
		assertTrue(true);
	}
	
	
	
	/*@Test
	public void processConfigsTest()
	{
		MediaManager mm = new MediaManager();

		List<MediaConfig> cl = new ArrayList<MediaConfig>();
		cl.add(getMediaConfig());

		mm.processConfigs(cl);

		assertTrue(true);
	}

	private MediaConfig getMediaConfig()
	{
		String movieNameVar = "MovieName";
		String movieYearVar = "MovieYear";
		String movieExtensionVar = "movieExtension";

		//create variables
		RegExVariableSetter nameVar = new RegExVariableSetter();
		nameVar.setVariableName(movieNameVar);
		nameVar.setGroupAssembly("{1}");
		nameVar.setReplaceExpression("[\\.-]+");
		nameVar.setReplaceWithCharacter(" ");

		RegExVariableSetter yearVar = new RegExVariableSetter();
		yearVar.setVariableName(movieYearVar);
		yearVar.setGroupAssembly("{2}");

		RegExVariableSetter extensionVar = new RegExVariableSetter();
		extensionVar.setVariableName(movieExtensionVar);
		extensionVar.setGroupAssembly("{3}");
		
		//create selector and add variables to it
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.addRegExVariable(nameVar);
		sel.addRegExVariable(yearVar);
		sel.addRegExVariable(extensionVar);
		
		//add media config and add selector to it
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		config.addConfigVariable(new ConfigVariable(movieNameVar));
		config.addConfigVariable(new ConfigVariable(movieYearVar));
		config.addConfigVariable(new ConfigVariable(movieExtensionVar));
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath(String.format("{%s} ({%s})\\{%s} ({%s}).{%s}", movieNameVar, movieYearVar, movieNameVar, movieYearVar, movieExtensionVar));
		config.addRegExSelector(sel);

		return config;
	}*/

}















