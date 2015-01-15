package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class MatchFilesViewModel 
{
	public class FileMatchViewModel{
		private String file;
		private EpisodeViewModel episode;
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		public EpisodeViewModel getEpisode() {
			return episode;
		}
		public void setEpisode(EpisodeViewModel episode) {
			this.episode = episode;
		}
	}
	
	private List<FileMatchViewModel> matches = new ArrayList<FileMatchViewModel>();
	private String selectedFile; 

	public List<FileMatchViewModel> getMatches() {
		return matches;
	}

	public void setMatches(List<FileMatchViewModel> matches) {
		this.matches = matches;
	}

	public String getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(String selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	public void addMatch(String path, EpisodeViewModel episode)
	{
		FileMatchViewModel fm = new FileMatchViewModel();
		fm.setFile(path);
		fm.setEpisode(episode);
		
		matches.add(fm);
	}

	public void setFileEpisode(String path, EpisodeViewModel episode)
	{
		for(FileMatchViewModel fmvm : matches)
		{
			if(fmvm.getFile().equals(path))
			{
				fmvm.setEpisode(episode);
			}
		}
	}
}
