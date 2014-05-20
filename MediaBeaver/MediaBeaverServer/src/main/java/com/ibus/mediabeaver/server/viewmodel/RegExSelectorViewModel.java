package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.AutoPopulatingList;

import com.ibus.mediabeaver.core.entity.RegExVariable;

public class RegExSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	//Data properties////////////////////////////////////////
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private List<RegExVariableViewModel> variables = new ArrayList<RegExVariableViewModel>();
	
	
	//View properties////////////////////////////////////////
	/*private RegExVariableViewModel toAddVariable = new RegExVariableViewModel();
	
	private List<ConfigVariableViewModel> configVariables =  new ArrayList<ConfigVariableViewModel>();*/
	
	private String testFileName;
	private String testName;
	private String testYear;
		
	public String getDescription()
	{
		return description; 
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}
	
	public List<RegExVariableViewModel> getVariables()
	{
		return variables;
	}

	public void setVariables(List<RegExVariableViewModel> variables)
	{
		this.variables = variables;
	}

	/*public RegExVariableViewModel getToAddVariable()
	{
		return toAddVariable;
	}

	public void setToAddVariable(RegExVariableViewModel toAddVariable)
	{
		this.toAddVariable = toAddVariable;
	}*/

	public String getTestFileName()
	{
		return testFileName;
	}

	public void setTestFileName(String testFileName)
	{
		this.testFileName = testFileName;
	}

	public String getTestName()
	{
		return testName;
	}

	public void setTestName(String testName)
	{
		this.testName = testName;
	}

	public String getTestYear()
	{
		return testYear;
	}

	public void setTestYear(String testYear)
	{
		this.testYear = testYear;
	}

	/*public List<ConfigVariableViewModel> getConfigVariables()
	{
		return configVariables;
	}

	public void setConfigVariables(List<ConfigVariableViewModel> configVariables)
	{
		this.configVariables = configVariables;
	}

	
	public ConfigVariableViewModel getConfigVariableForId(String id)
	{
		for(ConfigVariableViewModel c : configVariables)
		{
			if(c.getId().equals(id))
			{
				return c;
			}
		}
		
		return null;
	}*/
	
	public void deleteRegExVariableViewModel(String index)
	{
		variables.remove(Integer.parseInt(index));
	}
	
	
}















