package com.ibus.tvdb.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Banner")
public class Banner
{
	private Long id;
	
	@XmlElement(name = "BannerPath")
	private String bannerPath;
	
	@XmlElement(name = "BannerType")
	private String bannerType;
	
	@XmlElement(name = "BannerType2")
	private String bannerType2;
	
	@XmlElement(name = "Colors")
	private String colors;
	
	@XmlElement(name = "Language")
	private String language;
	
	@XmlElement(name = "Rating")
	private String rating;
	
	@XmlElement(name = "RatingCount")
	private String ratingCount;
	
	@XmlElement(name = "SeriesName")
	private String seriesName;
	
	@XmlElement(name = "ThumbnailPath")
	private String thumbnailPath;
	
	@XmlElement(name = "VignettePath")
	private String vignettePath;
	
	@XmlElement(name = "Season")
	private String season;

	
	/**
	 * Append this path to http://www.thetvdb.com/banners/ to get the banner image.  
	 * example: http://www.thetvdb.com/banners/seasons/121361-4.jpg where seasons/121361-4.jpg is the bannerPath
	 */
	public String getBannerPath() {
		return bannerPath;
	}
	/**
	 * Append this path to http://www.thetvdb.com/banners/ to get the banner image.  
	 * example: http://www.thetvdb.com/banners/seasons/121361-4.jpg where seasons/121361-4.jpg is the bannerPath
	 */
	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}
	
	/**
	 *This can be poster, fanart, series or season.
	 */
	public String getBannerType() {
		return bannerType;
	}
	/**
	 *This can be poster, fanart, series or season.
	 */
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}
	
	/**
	 	For series banners it can be text, graphical, or blank. For season banners it can be season or seasonwide. For fanart it can be 1280x720 or 1920x1080. 
	 	For poster it will always be 680x1000.

		Blank banners will leave the title and show logo off the banner. Text banners will show the series name as plain text in an Arial font. Graphical banners will 
		show the series name in the show's official font or will display the actual logo for the show. Season banners are the standard DVD cover format while wide season 
		banners will be the same dimensions as the series banners.
	 */
	public String getBannerType2() {
		return bannerType2;
	}
	
	/**
 	For series banners it can be text, graphical, or blank. For season banners it can be season or seasonwide. For fanart it can be 1280x720 or 1920x1080. 
 	For poster it will always be 680x1000.

	Blank banners will leave the title and show logo off the banner. Text banners will show the series name as plain text in an Arial font. Graphical banners will 
	show the series name in the show's official font or will display the actual logo for the show. Season banners are the standard DVD cover format while wide season 
	banners will be the same dimensions as the series banners.
	 */
	public void setBannerType2(String bannerType2) {
		this.bannerType2 = bannerType2;
	}
	
	
	/**
	 * Returns either null or three RGB colors in decimal format and pipe delimited. These are colors the artist picked that go well with the image. In order 
	 * they are Light Accent Color, Dark Accent Color and Neutral Midtone Color. It's meant to be used if you want to write something over the image, it gives you a 
	 * good idea of what colors may work and show up well. Only shows if BannerType is fanart.
	 */
	public String getColors() {
		return colors;
	}
	/**
	 * Returns either null or three RGB colors in decimal format and pipe delimited. These are colors the artist picked that go well with the image. In order 
	 * they are Light Accent Color, Dark Accent Color and Neutral Midtone Color. It's meant to be used if you want to write something over the image, it gives you a 
	 * good idea of what colors may work and show up well. Only shows if BannerType is fanart.
	 */
	public void setColors(String colors) {
		this.colors = colors;
	}
	
	/**
	 * Some banners list the series name in a foreign language. The language abbreviation will be listed here.
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * Some banners list the series name in a foreign language. The language abbreviation will be listed here.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * Returns either null or a decimal with four decimal places. The rating the banner currently has on the site.
	 */
	public String getRating() {
		return rating;
	}
	/**
	 * Returns either null or a decimal with four decimal places. The rating the banner currently has on the site.
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	
	/**
	 * Always returns an unsigned integer. Number of people who have rated the image.
	 */
	public String getRatingCount() {
		return ratingCount;
	}
	/**
	 * Always returns an unsigned integer. Number of people who have rated the image.
	 */
	public void setRatingCount(String ratingCount) {
		this.ratingCount = ratingCount;
	}
	
	
	/**
	 * This can be true or false. Only shows if BannerType is fanart. Indicates if the seriesname is included in the image or not.
	 */
	public String getSeriesName() {
		return seriesName;
	}
	/**
	 * This can be true or false. Only shows if BannerType is fanart. Indicates if the seriesname is included in the image or not.
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	/**
	 * Used exactly the same way as BannerPath, only shows if BannerType is fanart.
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	/**
	 * Used exactly the same way as BannerPath, only shows if BannerType is fanart.
	 */
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	
	/**
	 * Used exactly the same way as BannerPath, only shows if BannerType is fanart.
	 */
	public String getVignettePath() {
		return vignettePath;
	}
	/**
	 * Used exactly the same way as BannerPath, only shows if BannerType is fanart.
	 */
	public void setVignettePath(String vignettePath) {
		this.vignettePath = vignettePath;
	}
	
	/**
	 * If the banner is for a specific season, that season number will be listed here.
	 */
	public String getSeason() {
		return season;
	}
	/**
	 * If the banner is for a specific season, that season number will be listed here.
	 */
	public void setSeason(String season) {
		this.season = season;
	}

}
