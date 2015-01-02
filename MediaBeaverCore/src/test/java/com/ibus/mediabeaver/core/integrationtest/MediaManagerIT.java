package com.ibus.mediabeaver.core.integrationtest;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.Before;
import org.junit.Test;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.util.Factory;

public class MediaManagerIT
{
	Configuration configuration;
	@Before
	public void beforeTest()
	{
		configuration = new Configuration();
		
		configuration.setSourceDirectory("D:\\MediabeaverTests\\Source\\");
		configuration.setTvRootDirectory("D:\\MediabeaverTests\\Destination\\TV\\");
		configuration.setMovieRootDirectory("D:\\MediabeaverTests\\Destination\\Movies\\");
		configuration.setCopyAsDefault(false);
		configuration.setEpisodeFormatPath("{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()\\Season {SeasonNumber}\\{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace() S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		configuration.setMovieFormatPath("{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))\\{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))");
		configuration.setVideoExtensionFilter(".3g2, .3gp, .asf, .avi, .drc, .flv, .flv, .m4v, .mkv, .mng, .mov, .qt, .mp4, .m4p, .m4v, .mpg, .mp2, .mpeg, .mpg, .mpe, .mpv, .mpg, .mpeg, .m2, .mxf, .nsv, .ogv, .ogg, .rm, .rmvb, .roq, .svi, .webm, .wmv");
		
		Factory.initialise(com.ibus.mediabeaver.core.util.Platform.CLI, configuration);
	}
	
	@Test
	public void processConfigsTest() throws XmlRpcException, IOException
	{
		MediaMover mm = Factory.getMediaMover();
		mm.moveFiles(configuration.getSourceDirectory());
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

	
	

}















