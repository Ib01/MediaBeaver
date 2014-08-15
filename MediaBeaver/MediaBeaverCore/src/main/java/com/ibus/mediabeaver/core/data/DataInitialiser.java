package com.ibus.mediabeaver.core.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExPathTokenSetter;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class DataInitialiser 
{
	static Logger log = Logger.getLogger(DataInitialiser.class.getName());
	
	public static void addDefaultConfigs()
	{
		addMoveMoviesConfig();
		addMoveTVEpisodesConfig();
	}
	
	/*Move Movie files ----------------------------------------------------------------------------*/
	public static void addMoveMoviesConfig()
	{
		log.debug(String.format("Adding Move Movie Config"));
		
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath("{{name}} ({{year}})\\{{name}} ({{year}}).({{extension}})");
		config.setSorOrder(1);
		
		config.getRegExSelectors().add(getMovieWithYearAndAnyExtensionSetter());
		
		Repository.saveEntity(config);
	}
	
	private static RegExSelector getMovieWithYearAndAnyExtensionSetter()
	{
		RegExSelector selector = new RegExSelector();
		selector.setDescription("Movies with year and any extension");
		selector.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].*\\.([a-zA-Z]+)");
		selector.setSorOrder(1);
		
		RegExPathTokenSetter setter = new RegExPathTokenSetter();
		setter.setPathTokenName("name");
		setter.setGroupAssembly("{1}");
		setter.setReplaceExpression("[\\.-]+");
		setter.setReplaceWithCharacter(" ");
		
		selector.getPathTokenSetters().add(setter);
		
		setter = new RegExPathTokenSetter();
		setter.setPathTokenName("year");
		setter.setGroupAssembly("{2}");
		
		selector.getPathTokenSetters().add(setter);
		
		setter = new RegExPathTokenSetter();
		setter.setPathTokenName("extension");
		setter.setGroupAssembly("{3}");
		
		selector.getPathTokenSetters().add(setter);
		
		return selector;
	}
	
	/*Move Tv Episodes files ----------------------------------------------------------------------------*/
	
	
	public static void addMoveTVEpisodesConfig()
	{
		log.debug(String.format("Adding Move Tv Episodes Config"));
		
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Tv Episode files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Tv Series");
		config.setRelativeDestinationPath("{{SeriesName}}\\Season {{SeasonNumber}}\\{{SeriesName}} S{{SeasonNumber}}E{{EpisodeNumber}}.{{Extension}}");
		config.setSorOrder(2);
		
		config.getRegExSelectors().add(getTVEpisodeWithSeriesSeasonAndAnyExtensionSetter());
		
		Repository.saveEntity(config);
	}
	
	private static RegExSelector getTVEpisodeWithSeriesSeasonAndAnyExtensionSetter()
	{
		RegExSelector selector = new RegExSelector();
		selector.setDescription("Movies Tv Series with Name Season and Episode information");
		selector.setExpression("(.+)[Ss](\\d\\d)[Ee](\\d\\d).*\\.([a-zA-Z]+)");
		selector.setSorOrder(1);
		
		RegExPathTokenSetter setter = new RegExPathTokenSetter();
		setter.setPathTokenName("SeriesName");
		setter.setGroupAssembly("{1}");
		setter.setReplaceExpression("[\\.-]+");
		setter.setReplaceWithCharacter(" ");
		
		selector.getPathTokenSetters().add(setter);
		
		setter = new RegExPathTokenSetter();
		setter.setPathTokenName("SeasonNumber");
		setter.setGroupAssembly("{2}");
		
		selector.getPathTokenSetters().add(setter);
		
		setter = new RegExPathTokenSetter();
		setter.setPathTokenName("EpisodeNumber");
		setter.setGroupAssembly("{3}");
		
		selector.getPathTokenSetters().add(setter);
		
		setter = new RegExPathTokenSetter();
		setter.setPathTokenName("Extension");
		setter.setGroupAssembly("{4}");
		
		selector.getPathTokenSetters().add(setter);
		
		return selector;
	}
	
	
	
	
	
}























