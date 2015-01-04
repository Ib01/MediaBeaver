package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Episode;

public class SelectEpisodeViewModel 
{
	private List<Episode> episodes = new ArrayList<Episode>();
	private int selectedEpisodeId;
	
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	public int getSelectedEpisodeId() {
		return selectedEpisodeId;
	}
	public void setSelectedEpisodeId(int selectedEpisodeId) {
		this.selectedEpisodeId = selectedEpisodeId;
	}
	
	
}
