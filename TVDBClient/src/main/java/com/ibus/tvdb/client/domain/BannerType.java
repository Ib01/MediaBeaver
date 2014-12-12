package com.ibus.tvdb.client.domain;

public enum BannerType
{
	Poster("Poster"),
	Fanart("Fanart"),
	Series("Series "),
	Season("Season");
	

	private String name; 
	
    private BannerType(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
