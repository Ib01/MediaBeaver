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

import com.ibus.mediabeaver.core.entity.RegExVariableSetter;

public class RegExSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	//Data properties////////////////////////////////////////
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private List<RegExVariableSetterViewModel> variables = new ArrayList<RegExVariableSetterViewModel>();
	
	
	//View properties////////////////////////////////////////
	
	private String testFileName;
	private List<ConfigVariableViewModel> testVariables = new ArrayList<ConfigVariableViewModel>();
	private int index = -1;
	
	
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
	
	public List<RegExVariableSetterViewModel> getVariables()
	{
		return variables;
	}

	public void setVariables(List<RegExVariableSetterViewModel> variables)
	{
		this.variables = variables;
	}

	public String getTestFileName()
	{
		return testFileName;
	}

	public void setTestFileName(String testFileName)
	{
		this.testFileName = testFileName;
	}

	public void deleteRegExVariableViewModel(String index)
	{
		variables.remove(Integer.parseInt(index));
	}

	public List<ConfigVariableViewModel> getTestVariables()
	{
		return testVariables;
	}

	public void setTestVariables(List<ConfigVariableViewModel> testVariables)
	{
		this.testVariables = testVariables;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}
	
	
}















