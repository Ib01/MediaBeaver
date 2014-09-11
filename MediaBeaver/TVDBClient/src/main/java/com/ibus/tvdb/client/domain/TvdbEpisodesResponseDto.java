//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.30 at 09:14:47 PM EST 
//

package com.ibus.tvdb.client.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Data")
public class TvdbEpisodesResponseDto 
{
	@XmlElement(name="Series")
	protected TvdbSeriesDto series = new TvdbSeriesDto();

	//XmlElementWrapper(name="Episodes")
    @XmlElement(name="Episode")
	protected List<TvdbEpisodeDto> episodes= new ArrayList<TvdbEpisodeDto>();
	

	public List<TvdbEpisodeDto> getEpisodes()
	{
        return this.episodes;
	}

	public void setEpisodes(List<TvdbEpisodeDto> episodes)
	{
		this.episodes = episodes;
	}

	public TvdbEpisodeDto getEpisode(String seasonNumber, String episodeNumber)
	{
		for(TvdbEpisodeDto episode : episodes)
		{
			if(episode.getSeasonNumber().equals(seasonNumber) && episode.getEpisodeNumber().equals(episodeNumber))
			{
				return episode;
			}
		}
		
		return null;
	}
    
  

}