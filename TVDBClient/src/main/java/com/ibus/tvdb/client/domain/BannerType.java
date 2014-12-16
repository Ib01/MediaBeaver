package com.ibus.tvdb.client.domain;

public enum BannerType
{
	Poster("poster"),
	Fanart("fanart"),
	Series("series "),
	Season("season");
	

	private String name; 
	
    private BannerType(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
