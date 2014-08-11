package com.ibus.mediabeaver.server.viewmodel;

import org.hibernate.validator.constraints.NotEmpty;

public class RegExPathTokenSetterViewModel extends ViewModel
{
	public RegExPathTokenSetterViewModel(){}
	public RegExPathTokenSetterViewModel(String tokenName){
		this.pathTokenName = tokenName;
	}
	
	
	private static final long serialVersionUID = 1L;
	
	//Data properties ///////////////////////////////////////////////
	private String pathTokenName;

	@NotEmpty(message = "This field cannot be left empty")
	private String groupAssembly;

	private String replaceExpression;
	
	private String replaceWithCharacter;

	
	// View properties ///////////////////////////////////////////////
	private String selectedConfigVariable;
	
	
	public RegExPathTokenSetterViewModel copy()
	{
		RegExPathTokenSetterViewModel setter = new RegExPathTokenSetterViewModel();
		setter.setGroupAssembly(groupAssembly);
		setter.setId(id);
		setter.setLastUpdate(lastUpdate);
		setter.setReplaceExpression(replaceExpression);
		setter.setReplaceWithCharacter(replaceWithCharacter);
		setter.setSelectedConfigVariable(selectedConfigVariable);
		setter.setPathTokenName(pathTokenName);
		
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

	public String getPathTokenName()
	{
		return pathTokenName;
	}

	public void setPathTokenName(String pathTokenName)
	{
		this.pathTokenName = pathTokenName;
	}
	
	
	
	
}
