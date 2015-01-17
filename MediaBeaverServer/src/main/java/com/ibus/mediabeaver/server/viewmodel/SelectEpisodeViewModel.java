package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Episode;

public class SelectEpisodeViewModel 
{
	private List<EpisodeViewModel> episodes = new ArrayList<EpisodeViewModel>();
	private int selectedEpisodeNumber;
	private String selectedFile;
	
	
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
	public int getSelectedEpisodeNumber() {
		return selectedEpisodeNumber;
	}
	public void setSelectedEpisodeNumber(int selectedEpisodeNumber) {
		this.selectedEpisodeNumber = selectedEpisodeNumber;
	}
	
	
}
