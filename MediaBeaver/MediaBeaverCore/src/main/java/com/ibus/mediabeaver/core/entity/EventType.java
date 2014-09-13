package com.ibus.mediabeaver.core.entity;

public enum EventType
{
	Move("Move File"),            
	Copy("Copy File"),
	Rename("Rename File");

	private String name; 
	
    private EventType(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
