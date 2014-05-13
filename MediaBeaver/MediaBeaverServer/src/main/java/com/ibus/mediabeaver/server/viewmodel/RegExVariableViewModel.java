package com.ibus.mediabeaver.server.viewmodel;

import java.io.IOException;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ibus.mediabeaver.core.entity.ConfigVariable;

public class RegExVariableViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	private ConfigVariableViewModel configVariable;

	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	private String replaceExpression;
	
	private String replaceWithCharacter;

	public ConfigVariableViewModel getConfigVariable()
	{
		return configVariable;
	}

	//@JsonDeserialize(using=ConfigVariableIdDeserializer.class)
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
	
	
	
	
/*	public class ConfigVariableIdDeserializer extends StdDeserializer<ConfigVariableViewModel> 
	{
		private static final long serialVersionUID = 1L;

		public ConfigVariableIdDeserializer(){
	        super(ConfigVariableViewModel.class);
	    }
		
		@Override
		public ConfigVariableViewModel deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException
		{
			return new ConfigVariableViewModel();
		}
		
		
	}*/
	
}
