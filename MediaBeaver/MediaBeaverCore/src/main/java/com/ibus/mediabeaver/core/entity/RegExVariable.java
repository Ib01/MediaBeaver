package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "RegEx_Variable")
public class RegExVariable extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private ConfigVariable configVariable;
	/*
	@Column
	private String variableName;*/

	/**
	 * the item assembled form regex groups found in parent regex expression
	 */
	@Column
	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	/**
	 * these characters will be removed from assembledItem
	 */
	@Column
	private String replaceExpression;

	/**
	 * this reg ex will be applied recursively over assembledItem
	 */
	@Column
	private String replaceWithCharacter;
	

	/*@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private RegExSelector regExSelector;*/

	/*public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variable)
	{
		this.variableName = variable;
	}*/

	public String getGroupAssembly()
	{
		return groupAssembly;
	}

	public void setGroupAssembly(String groupAssembly)
	{
		this.groupAssembly = groupAssembly;
	}

	public String getRreplaceExpression()
	{
		return replaceExpression;
	}

	public void setReplaceExpression(String replaceExpression)
	{
		this.replaceExpression = replaceExpression;
	}

	public String getReplaceWithCharacter()
	{
		return replaceWithCharacter;
	}

	public void setReplaceWithCharacter(String replaceWithCharacter)
	{
		this.replaceWithCharacter = replaceWithCharacter;
	}

	public ConfigVariable getConfigVariable()
	{
		return configVariable;
	}

	public void setConfigVariable(ConfigVariable configVariable)
	{
		this.configVariable = configVariable;
	}

}
