package com.ibus.mediabeaver.cli.integrationtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.Before;
import org.junit.Test;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.FileProcessorBase.Platform;
import com.ibus.mediabeaver.core.filesystem.MediaMover;

public class MediaManagerIT
{
	@Before
	public void beforeTest()
	{
		//refreshTestDirs();
	}
	
	@Test
	public void processConfigsTest() throws XmlRpcException, IOException
	{
		Configuration c = new Configuration();
		c.setSourceDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Source\\");
		c.setTvRootDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Destination\\TV\\");
		c.setMovieRootDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Destination\\Movies\\");
		
		c.setEpisodePath("{SeriesName}\\Season {SeasonNumber}\\{SeriesName} S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		c.setMoviePath("{MovieName}({ReleaseDate}.substring(\"0\",\"4\"))\\{MovieName}({ReleaseDate}.substring(\"0\",\"4\"))");
		
		

		MediaMover mm = new MediaMover(Platform.CLI);
		mm.processFiles(c);

		
		
		assertTrue(true);
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















