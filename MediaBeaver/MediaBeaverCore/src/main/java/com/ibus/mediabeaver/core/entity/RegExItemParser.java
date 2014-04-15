package com.ibus.mediabeaver.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.*;

@Embeddable
public class RegExItemParser implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * the item assembled form regex groups found in parent regex expression   
	 */
	@NotEmpty(message="This field cannot be left empty")
	@Column
	private String assembledItem;
	
	/**
	 * these characters will be removed from assembledItem  
	 */
	@Column
	private String removeCharacters;
	
	/**
	 * this reg ex will be applied recursively over assembledItem 
	 */
	@Column
	private String cleaningRegEx;
	
	
	/**
	 * this reg ex will be applied recursively over assembledItem 
	 */
	@Column
	private String recursiveRegEx;
	

	public String getAssembledItem() {
		return assembledItem;
	}
	public void setAssembledItem(String assembledItem) {
		this.assembledItem = assembledItem;
	}
	public String getRemoveCharacters() {
		return removeCharacters;
	}
	public void setRemoveCharacters(String removeCharacters) {
		this.removeCharacters = removeCharacters;
	}
	public String getCleaningRegEx() {
		return cleaningRegEx;
	}
	public void setCleaningRegEx(String recursiveRegEx) {
		this.cleaningRegEx = recursiveRegEx;
	}
	public String getRecursiveRegEx() {
		return recursiveRegEx;
	}
	public void setRecursiveRegEx(String recursiveRegEx) {
		this.recursiveRegEx = recursiveRegEx;
	}
	
	public boolean hasCleaner()
	{
		return (cleaningRegEx != null && cleaningRegEx.length() > 0);
	}
	
}
