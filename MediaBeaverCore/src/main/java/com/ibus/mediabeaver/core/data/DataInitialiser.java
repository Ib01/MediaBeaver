package com.ibus.mediabeaver.core.data;

import org.apache.log4j.Logger;

import com.ibus.mediabeaver.core.entity.UserConfiguration;
import com.ibus.mediabeaver.core.entity.ServiceFieldMap;

public class DataInitialiser 
{
	static Logger log = Logger.getLogger(DataInitialiser.class.getName());
	
	public static void addDefaultConfigs()
	{
		addConfiguration();
		
	}
	
	
	public static void addConfiguration()
	{
		UserConfiguration c = new UserConfiguration();
		c.setSourceDirectory("D:\\MediabeaverTests\\Source\\");
		c.setTvRootDirectory("D:\\MediabeaverTests\\Destination\\TV\\");
		c.setMovieRootDirectory("D:\\MediabeaverTests\\Destination\\Movies\\");
		
		c.setEpisodeFormatPath("{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()\\Season {SeasonNumber}\\{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace() S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		c.setMovieFormatPath("{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))\\{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))");
		
		c.setVideoExtensionFilter(".3g2, .3gp, .asf, .avi, .drc, .flv, .flv, .m4v, .mkv, .mng, .mov, .qt, .mp4, .m4p, .m4v, .mpg, .mp2, .mpeg, .mpg, .mpe, .mpv, .mpg, .mpeg, .m2, .mxf, .nsv, .ogv, .ogg, .rm, .rmvb, .roq, .svi, .webm, .wmv");
		
		c.setCopyAsDefault(false);
		
		Repository.saveEntity(c);
	}
	

	public static void addServiceFieldMaps()
	{
		saveServiceFieldMap("MovieName", "mn", "title", null);
		saveServiceFieldMap("ReleaseDate", "rd", "releaseDate", null);
		
		saveServiceFieldMap("SeriesName", "srn", null, "seriesName");
		saveServiceFieldMap("SeasonNumber", "sn", null, "seasonNumber");
		saveServiceFieldMap("EpisodeNumber", "en", null, "episodeNumber");
		saveServiceFieldMap("EpisodeName", "enm", null, "episodeName");
		
		
	}
	
	
	private static void saveServiceFieldMap(String appName, String abreviation, String tmbdField, String tvdbField)
	{
		ServiceFieldMap map;
		map = new ServiceFieldMap();
		map.setAppField(appName);
		map.setAppFieldAbbreviation(abreviation);
		map.setTmdbField(tmbdField);
		map.setTvdbField(tvdbField);
		Repository.saveEntity(map);
	}

	
	
}























