package com.ibus.mediabeaver.server.viewmodel;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

public class RegExVariableViewModel
{
	private String variableName;

	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	private String replaceExpression;
	
	private String replaceWithCharacter;

	
	public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
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
