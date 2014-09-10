package com.ibus.mediabeaver.core.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.OpenSubtitlesField;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExPathTokenSetter;
import com.ibus.mediabeaver.core.entity.ServiceFieldMap;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class DataInitialiser 
{
	static Logger log = Logger.getLogger(DataInitialiser.class.getName());
	
	public static void addDefaultConfigs()
	{
		addConfiguration();
		
		/*addOpenSubtitlesFields();
		addMoveMoviesConfig();
		addMoveTVEpisodesConfig();*/
		//addServiceFieldMaps();
	}
	
	
	public static void addConfiguration()
	{
		Configuration c = new Configuration();
		c.setSourceDirectory("D:\\MediabeaverTests\\Source\\");
		c.setTvRootDirectory("D:\\MediabeaverTests\\Destination\\TV\\");
		c.setMovieRootDirectory("D:\\MediabeaverTests\\Destination\\Movies\\");
		
		c.setEpisodePath("{SeriesName}\\Season {SeasonNumber}\\{SeriesName} S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		c.setMoviePath("{MovieName}({ReleaseDate}.substring(\"0\",\"4\"))\\{MovieName}({ReleaseDate}.substring(\"0\",\"4\"))");
		
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
	
	/*
	tvdb
	@XmlElement(name = "Combined_episodenumber")
	private String combinedEpisodeNumber;
	
	@XmlElement(name = "Combined_season")
	private String combinedSeason;
	
	@XmlElement(name = "DVD_chapter")
	private String dvdChapter;
	
	@XmlElement(name = "DVD_discid")
	private String dvdDiscId;
	
	@XmlElement(name = "DVD_episodenumber")
	private String dvdEpisodenumber;
	
	@XmlElement(name = "DVD_season")
	private String DVDSeason;
	
	@XmlElement(name = "Director")
	private String director;
	
	@XmlElement(name = "EpImgFlag")
	private String epImgFlag;
	
	@XmlElement(name = "EpisodeName")
	private String episodeName;
	
	@XmlElement(name = "EpisodeNumber")
	private String episodeNumber;
	
	@XmlElement(name = "FirstAired")
	private String firstAired;
	
	@XmlElement(name = "GuestStars")
	private String guestStars;
	
	@XmlElement(name = "IMDB_ID")
	private String imdbId;
	
	@XmlElement(name = "Language")
	private String language;
	
	@XmlElement(name = "Overview")
	private String overview;
	
	@XmlElement(name = "ProductionCode")
	private String productionCode;
	
	@XmlElement(name = "Rating")
	private String rating;
	
	@XmlElement(name = "RatingCount")
	private String ratingCount;
	
	@XmlElement(name = "SeasonNumber")
	private String seasonNumber;
	
	@XmlElement(name = "Writer")
	private String writer;
	
	@XmlElement(name = "absolute_number")
	private String absoluteNumber;
	
	@XmlElement(name = "filename")
	private String fileName;
	
	@XmlElement(name = "lastupdated")
	private String lastUpdated;
	
	@XmlElement(name = "seasonid")
	private String seasonId;
	
	@XmlElement(name = "seriesid")
	private String seriesId;
	
	@XmlElement(name = "thumb_added")
	private String thumbAdded;
	
	@XmlElement(name = "thumb_height")
	private String thumbHeight;
	
	@XmlElement(name = "thumb_width")
	private String thumbWidth;
	
	tmdb
    private String title;
    private String originalTitle;
	private float popularity;
	private String backdropPath;
    private String posterPath;
	private String releaseDate;
    private boolean adult;
    private Collection belongsToCollection;
    private long budget;
    private List<Genre> genres;
    private String homepage;
	private String overview;
	private String imdbID;
	private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
	private long revenue;
    private int runtime;
	private List<Language> spokenLanguages;
	private String tagline;
	private float userRating;
    private float voteAverage;
    private int voteCount;
    private String status;
    private MoviesAlternativeTitles alternativeTitles;
    private Credits credits;
    private MovieImages images;
    private MovieKeywords keywords;
    private TmdbMovies.ReleaseInfoResults releases;
    private MovieTrailers trailers;
    private MovieTranslations translations;
    private ResultsPage<MovieDb> similarMovies;
    private ResultsPage<Reviews> reviews;
    private ResultsPage<MovieList> lists;*/
	
	
	
	
	
	/*Add OpenSubtitlesField lookups --------------------------------------------------------------*/	
	public static void addOpenSubtitlesFields()
	{
		addOpenSubtitlesField("UserNickName");
		addOpenSubtitlesField("SubFormat");
		addOpenSubtitlesField("SeriesIMDBParent");
		addOpenSubtitlesField("IDSubtitle");
		addOpenSubtitlesField("IDMovie");
		addOpenSubtitlesField("SubBad");
		addOpenSubtitlesField("UserID");
		addOpenSubtitlesField("ZipDownloadLink");
		addOpenSubtitlesField("SubSize");
		addOpenSubtitlesField("SubFileName");
		addOpenSubtitlesField("SubDownloadLink");
		addOpenSubtitlesField("MovieKind");
		addOpenSubtitlesField("UserRank");
		addOpenSubtitlesField("SubActualCD");
		addOpenSubtitlesField("MovieImdbRating");
		addOpenSubtitlesField("SubAuthorComment");
		addOpenSubtitlesField("SubRating");
		addOpenSubtitlesField("SeriesSeason");
		addOpenSubtitlesField("SubFeatured");
		addOpenSubtitlesField("SubtitlesLink");
		addOpenSubtitlesField("SubHearingImpaired");
		addOpenSubtitlesField("SubHash");
		addOpenSubtitlesField("IDSubMovieFile");
		addOpenSubtitlesField("ISO639");
		addOpenSubtitlesField("MovieFPS");
		addOpenSubtitlesField("SubDownloadsCnt");
		addOpenSubtitlesField("MovieHash");
		addOpenSubtitlesField("SubSumCD");
		addOpenSubtitlesField("SubComments");
		addOpenSubtitlesField("MovieByteSize");
		addOpenSubtitlesField("LanguageName");
		addOpenSubtitlesField("MovieYear");
		addOpenSubtitlesField("SubLanguageID");
		addOpenSubtitlesField("MovieReleaseName");
		addOpenSubtitlesField("SeriesEpisode");
		addOpenSubtitlesField("MovieName");
		addOpenSubtitlesField("MovieTimeMS");
		addOpenSubtitlesField("MatchedBy");
		addOpenSubtitlesField("SubHD");
		addOpenSubtitlesField("MovieNameEng");
		addOpenSubtitlesField("SubAddDate");
		addOpenSubtitlesField("IDMovieImdb");
		addOpenSubtitlesField("IDSubtitleFile");
	}
	
	
	public static void addOpenSubtitlesField(String name)
	{
		OpenSubtitlesField field = new  OpenSubtitlesField();
		field.setName(name);
		Repository.saveEntity(field);
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
		config.setRelativeDestinationPath("{{name}} ({{year}})\\{{name}} ({{year}}).{{extension}}");
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
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\TV");
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























