package com.ibus.mediabeaver.server.viewmodel;


public class SelectMediaViewModel 
{
	public static enum SelectedMediaType
	{
		Movie("A movie which may or may not span multiple files"),            
		Tv("1 or more Tv Episodes that are a part of the same Tv Series");
		private String name; 
		
	    private SelectedMediaType(String name) { 
	        this.name = name; 
	    } 
	    
	    @Override 
	    public String toString(){ 
	        return name; 
	    } 
	}
	
	private String selectedMediaType;
	
	public String getSelectedMediaType() {
		return selectedMediaType;
	}
	public void setSelectedMediaType(String selectedMediaType) {
		this.selectedMediaType = selectedMediaType;
	}

}
