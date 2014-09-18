package com.ibus.mediabeaver.core.entity;

public enum ResultType
{
	Failed("Failed"),
	Succeeded("Succeeded");

	private String name; 
	
    private ResultType(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
