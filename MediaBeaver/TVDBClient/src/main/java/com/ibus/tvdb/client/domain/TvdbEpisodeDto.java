package com.ibus.tvdb.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Episode")
public class TvdbEpisodeDto
{
	private Long id;
	
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
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getCombinedEpisodeNumber()
	{
		return combinedEpisodeNumber;
	}

	public void setCombinedEpisodeNumber(String combinedEpisodeNumber)
	{
		this.combinedEpisodeNumber = combinedEpisodeNumber;
	}

	public String getDvdChapter()
	{
		return dvdChapter;
	}

	public void setDvdChapter(String dvdChapter)
	{
		this.dvdChapter = dvdChapter;
	}

	public String getCombinedSeason()
	{
		return combinedSeason;
	}

	public void setCombinedSeason(String combinedSeason)
	{
		this.combinedSeason = combinedSeason;
	}

	public String getDvdDiscId()
	{
		return dvdDiscId;
	}

	public void setDvdDiscId(String dvdDiscId)
	{
		this.dvdDiscId = dvdDiscId;
	}

	public String getDvdEpisodenumber()
	{
		return dvdEpisodenumber;
	}

	public void setDvdEpisodenumber(String dvdEpisodenumber)
	{
		this.dvdEpisodenumber = dvdEpisodenumber;
	}

	public String getDVDSeason()
	{
		return DVDSeason;
	}

	public void setDVDSeason(String dVDSeason)
	{
		DVDSeason = dVDSeason;
	}

	public String getDirector()
	{
		return director;
	}

	public void setDirector(String director)
	{
		this.director = director;
	}

	public String getEpImgFlag()
	{
		return epImgFlag;
	}

	public void setEpImgFlag(String epImgFlag)
	{
		this.epImgFlag = epImgFlag;
	}

	public String getEpisodeName()
	{
		return episodeName;
	}

	public void setEpisodeName(String episodeName)
	{
		this.episodeName = episodeName;
	}

	public String getEpisodeNumber()
	{
		return episodeNumber;
	}

	public void setEpisodeNumber(String episodeNumber)
	{
		this.episodeNumber = episodeNumber;
	}

	public String getFirstAired()
	{
		return firstAired;
	}

	public void setFirstAired(String firstAired)
	{
		this.firstAired = firstAired;
	}

	public String getGuestStars()
	{
		return guestStars;
	}

	public void setGuestStars(String guestStars)
	{
		this.guestStars = guestStars;
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

	public String getOverview()
	{
		return overview;
	}

	public void setOverview(String overview)
	{
		this.overview = overview;
	}

	public String getProductionCode()
	{
		return productionCode;
	}

	public void setProductionCode(String productionCode)
	{
		this.productionCode = productionCode;
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

	public String getSeasonNumber()
	{
		return seasonNumber;
	}

	public void setSeasonNumber(String seasonNumber)
	{
		this.seasonNumber = seasonNumber;
	}

	public String getWriter()
	{
		return writer;
	}

	public void setWriter(String writer)
	{
		this.writer = writer;
	}

	public String getAbsoluteNumber()
	{
		return absoluteNumber;
	}

	public void setAbsoluteNumber(String absoluteNumber)
	{
		this.absoluteNumber = absoluteNumber;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public String getSeasonId()
	{
		return seasonId;
	}

	public void setSeasonId(String seasonId)
	{
		this.seasonId = seasonId;
	}

	public String getSeriesId()
	{
		return seriesId;
	}

	public void setSeriesId(String seriesId)
	{
		this.seriesId = seriesId;
	}

	public String getThumbAdded()
	{
		return thumbAdded;
	}

	public void setThumbAdded(String thumbAdded)
	{
		this.thumbAdded = thumbAdded;
	}

	public String getThumbHeight()
	{
		return thumbHeight;
	}

	public void setThumbHeight(String thumbHeight)
	{
		this.thumbHeight = thumbHeight;
	}

	public String getThumbWidth()
	{
		return thumbWidth;
	}

	public void setThumbWidth(String thumbWidth)
	{
		this.thumbWidth = thumbWidth;
	}
}
