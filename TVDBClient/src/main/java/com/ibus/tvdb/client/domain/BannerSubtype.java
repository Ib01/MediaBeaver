package com.ibus.tvdb.client.domain;

public enum BannerSubtype
{
	Text("text"),
	Graphical("graphical"),
	Blank("Series "),
	Season ("Season"),
	Seasonwide ("Season"),
	Twelve80x720("1280x720"),
	Nineteen20x1080 ("1920x1080"),
	Six80x1000 ("680x1000");

	private String name; 
	
    private BannerSubtype(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
