package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Episode;

public class SelectEpisodeViewModel 
{
	private List<EpisodeViewModel> episodes = new ArrayList<EpisodeViewModel>();
	private int selectedEpisodeId;
	private String selectedFile;
	
	public int getSelectedEpisodeId() {
		return selectedEpisodeId;
	}
	public void setSelectedEpisodeId(int selectedEpisodeId) {
		this.selectedEpisodeId = selectedEpisodeId;
	}
	public String getSelectedFile() {
		return selectedFile;
	}
	public void setSelectedFile(String selectedFile) {
		this.selectedFile = selectedFile;
	}
	public List<EpisodeViewModel> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<EpisodeViewModel> episodes) {
		this.episodes = episodes;
	}
	
	
}
