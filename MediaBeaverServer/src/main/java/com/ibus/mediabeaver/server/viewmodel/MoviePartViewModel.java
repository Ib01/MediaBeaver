package com.ibus.mediabeaver.server.viewmodel;

public class MoviePartViewModel 
{
	private String path;
	private String partNumber;

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public MoviePartViewModel(){}
	
	public MoviePartViewModel(String path){	
		this.path = path;
	}
}
