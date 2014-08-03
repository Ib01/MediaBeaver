package com.ibus.mediabeaver.server.viewmodel;

import org.hibernate.validator.constraints.NotEmpty;

public class RegExVariableSetterViewModel extends ViewModel
{
	public RegExVariableSetterViewModel(){}
	public RegExVariableSetterViewModel(String variableName){
		this.variableName = variableName;
	}
	
	
	private static final long serialVersionUID = 1L;
	
	//Data properties ///////////////////////////////////////////////
	private String variableName;

	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	private String replaceExpression;
	
	private String replaceWithCharacter;

	
	// View properties ///////////////////////////////////////////////
	private String selectedConfigVariable;
	
	
	public RegExVariableSetterViewModel copy()
	{
		RegExVariableSetterViewModel setter = new RegExVariableSetterViewModel();
		setter.setGroupAssembly(groupAssembly);
		setter.setId(id);
		setter.setLastUpdate(lastUpdate);
		setter.setReplaceExpression(replaceExpression);
		setter.setReplaceWithCharacter(replaceWithCharacter);
		setter.setSelectedConfigVariable(selectedConfigVariable);
		setter.setVariableName(variableName);
		
		return setter;
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

	public String getSelectedConfigVariable()
	{
		return selectedConfigVariable;
	}

	public void setSelectedConfigVariable(String selectedConfigVariable)
	{
		this.selectedConfigVariable = selectedConfigVariable;
	}

	public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
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
