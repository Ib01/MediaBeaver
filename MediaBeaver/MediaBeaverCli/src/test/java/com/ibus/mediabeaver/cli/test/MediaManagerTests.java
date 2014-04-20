package com.ibus.mediabeaver.cli.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.ibus.mediabeaver.cli.utility.MediaManager2;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class MediaManagerTests
{
	@Test
	public void processConfigsTest()
	{
		MediaManager2 mm = new MediaManager2();

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

		
		RegExVariable nameVar = new RegExVariable();
		nameVar.setVariableName(movieNameVar);
		nameVar.setGroupAssembly("{1}");
		nameVar.setReplaceCharacters(".-");
		nameVar.setReplaceWithCharacter(" ");

		RegExVariable yearVar = new RegExVariable();
		yearVar.setVariableName(movieYearVar);
		yearVar.setGroupAssembly("{2}");

		RegExVariable extensionVar = new RegExVariable();
		extensionVar.setVariableName(movieExtensionVar);
		extensionVar.setGroupAssembly("{3}");
		
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.addRegExVariable(nameVar);
		sel.addRegExVariable(yearVar);
		sel.addRegExVariable(extensionVar);
		
		
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Source");
		config.addConfigVariables(new ConfigVariable(movieNameVar));
		config.addConfigVariables(new ConfigVariable(movieYearVar));
		config.addConfigVariables(new ConfigVariable(movieExtensionVar));
		config.setDestinationRoot("C:\\Users\\Ib\\Desktop\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath(String.format("{%s} ({%s})\\{%s} ({%s}).{%s}", movieNameVar, movieYearVar, movieNameVar, movieYearVar, movieExtensionVar));
		config.addRegExSelector(sel);

		return config;
	}

}















