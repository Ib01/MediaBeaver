package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ibus.tvdb.client.domain.Banner;

public class SelectSeasonViewModel 
{
	private List<Banner> banners = new ArrayList<Banner>();
	private int seasonsCount;
	private int selectedSeasonNumber;
	
	public int getSeasonsCount() {
		return seasonsCount;
	}
	public void setSeasonsCount(int seasonsCount) {
		this.seasonsCount = seasonsCount;
	}
	public List<Banner> getBanners() {
		return banners;
	}
	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}
	public int getSelectedSeasonNumber() {
		return selectedSeasonNumber;
	}
	public void setSelectedSeasonNumber(int selectedSeasonNumber) {
		this.selectedSeasonNumber = selectedSeasonNumber;
	}
	
}
