package com.ibus.mediabeaver.server.viewmodel;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

import com.ibus.mediabeaver.core.entity.ConfigVariable;

public class RegExVariableViewModel extends ViewModel
{
	private ConfigVariableViewModel configVariable;

	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	private String replaceExpression;
	
	private String replaceWithCharacter;

	
	public ConfigVariableViewModel getConfigVariable()
	{
		return configVariable;
	}

	public void setConfigVariable(ConfigVariableViewModel configVariable)
	{
		this.configVariable = configVariable;
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
