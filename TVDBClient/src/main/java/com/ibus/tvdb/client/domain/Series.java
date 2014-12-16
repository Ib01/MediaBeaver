package com.ibus.tvdb.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Series")
public class Series
{
	private Long id;
	//private static String SeriesBannerPrefixUrl = "http://www.thetvdb.com/banners/";
	
	@XmlElement(name = "Combined_episodensumber")
	private String combinedEpisodenumber;

	@XmlElement(name = "Actors")
	private String actors;
	
	@XmlElement(name = "Airs_DayOfWeek")
	private String airsDayOfWeek;
	
	@XmlElement(name = "Airs_Time")
	private String airsTime;
	
	@XmlElement(name = "ContentRating")
	private String contentRating;
	
	@XmlElement(name = "FirstAired")
	private String firstAired;
	
	@XmlElement(name = "Genre")
	private String genre;
	
	@XmlElement(name = "IMDB_ID")
	private String imdbId;
	
	@XmlElement(name = "Language")
	private String language;
	
	@XmlElement(name = "Network")
	private String network;
	
	@XmlElement(name = "NetworkID")
	private String networkID;
	
	@XmlElement(name = "Overview")
	private String overview;
	
	@XmlElement(name = "Rating")
	private String rating;
	
	@XmlElement(name = "RatingCount")
	private String ratingCount;
	
	@XmlElement(name = "Runtime")
	private String runtime;
	
	@XmlElement(name = "SeriesID")
	private String seriesID;
	
	@XmlElement(name = "SeriesName")
	private String seriesName;
	
	@XmlElement(name = "Status")
	private String status;
	
	@XmlElement(name = "added")
	private String added;
	
	@XmlElement(name = "addedBy")
	private String addedBy;
	
	@XmlElement(name = "banner")
	private String banner;
	
	@XmlElement(name = "fanart")
	private String fanart;
	
	@XmlElement(name = "lastupdated")
	private String lastUpdated;
	
	@XmlElement(name = "poster")
	private String poster;
	
	@XmlElement(name = "tms_wanted_old")
	private String tmsWantedOld;
	
	@XmlElement(name = "zap2it_id")
	private String zap2itId;
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCombinedEpisodenumber()
	{
		return combinedEpisodenumber;
	}

	public void setCombinedEpisodenumber(String combinedEpisodenumber)
	{
		this.combinedEpisodenumber = combinedEpisodenumber;
	}

	public String getActors()
	{
		return actors;
	}

	public void setActors(String actors)
	{
		this.actors = actors;
	}

	public String getAirsDayOfWeek()
	{
		return airsDayOfWeek;
	}

	public void setAirsDayOfWeek(String airsDayOfWeek)
	{
		this.airsDayOfWeek = airsDayOfWeek;
	}

	public String getAirsTime()
	{
		return airsTime;
	}

	public void setAirsTime(String airsTime)
	{
		this.airsTime = airsTime;
	}

	public String getContentRating()
	{
		return contentRating;
	}

	public void setContentRating(String contentRating)
	{
		this.contentRating = contentRating;
	}

	public String getFirstAired()
	{
		return firstAired;
	}

	public void setFirstAired(String firstAired)
	{
		this.firstAired = firstAired;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getImdbId()
	{
		return imdbId;
	}

	public void setImdbId(String imdbId)
	{
		this.imdbId = imdbId;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getNetwork()
	{
		return network;
	}

	public void setNetwork(String network)
	{
		this.network = network;
	}

	public String getNetworkID()
	{
		return networkID;
	}

	public void setNetworkID(String networkID)
	{
		this.networkID = networkID;
	}

	public String getOverview()
	{
		return overview;
	}

	public void setOverview(String overview)
	{
		this.overview = overview;
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating(String rating)
	{
		this.rating = rating;
	}

	public String getRatingCount()
	{
		return ratingCount;
	}

	public void setRatingCount(String ratingCount)
	{
		this.ratingCount = ratingCount;
	}

	public String getRuntime()
	{
		return runtime;
	}

	public void setRuntime(String runtime)
	{
		this.runtime = runtime;
	}

	public String getSeriesID()
	{
		return seriesID;
	}

	public void setSeriesID(String seriesID)
	{
		this.seriesID = seriesID;
	}

	public String getSeriesName()
	{
		return seriesName;
	}

	public void setSeriesName(String seriesName)
	{
		this.seriesName = seriesName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getAdded()
	{
		return added;
	}

	public void setAdded(String added)
	{
		this.added = added;
	}

	public String getAddedBy()
	{
		return addedBy;
	}

	public void setAddedBy(String addedBy)
	{
		this.addedBy = addedBy;
	}

	public String getBanner()
	{
		return banner;
	}

	public void setBanner(String banner)
	{
		this.banner = banner;
	}

	public String getFanart()
	{
		return fanart;
	}

	public void setFanart(String fanart)
	{
		this.fanart = fanart;
	}

	public String getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public String getPoster()
	{
		return poster;
	}

	public void setPoster(String poster)
	{
		this.poster = poster;
	}

	public String getTmsWantedOld()
	{
		return tmsWantedOld;
	}

	public void setTmsWantedOld(String tmsWantedOld)
	{
		this.tmsWantedOld = tmsWantedOld;
	}

	public String getZap2itId()
	{
		return zap2itId;
	}

	public void setZap2itId(String zap2itId)
	{
		this.zap2itId = zap2itId;
	}
	
	
	/*public String getSeriesBannerUrl()
	{
		SeriesBannerPrefixUrl.
	}*/
}
