package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "RegEx_VariableSetter")
public class RegExPathTokenSetter extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String pathTokenName;

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

	public String getPathTokenName()
	{
		return pathTokenName;
	}

	public void setPathTokenName(String name)
	{
		this.pathTokenName = name;
	}

	public String getGroupAssembly()
	{
		return groupAssembly;
	}

	public void setGroupAssembly(String groupAssembly)
	{
		this.groupAssembly = groupAssembly;
	}

	public String getReplaceExpression()
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

	
}










