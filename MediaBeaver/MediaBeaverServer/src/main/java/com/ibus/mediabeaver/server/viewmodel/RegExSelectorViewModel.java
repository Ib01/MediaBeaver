package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.AutoPopulatingList;

import com.ibus.mediabeaver.core.entity.IdGenerator;
import com.ibus.mediabeaver.core.entity.RegExVariableSetter;

public class RegExSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	//Data properties////////////////////////////////////////
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private List<RegExVariableSetterViewModel> variableSetters = new ArrayList<RegExVariableSetterViewModel>();
	
	
	//View properties////////////////////////////////////////
	
	private String testFileName;
	//TODO: REMOVE?
	private List<ConfigVariableViewModel> testVariables = new ArrayList<ConfigVariableViewModel>();
	private String testResult;
	
	private int index = -1;
	
	public RegExSelectorViewModel()
	{
		//bit messy but allows us to reference objects in wizard without saving to db.
		//id = IdGenerator.createId();	
	}
	
	public RegExSelectorViewModel copy()
	{
		RegExSelectorViewModel selector = new RegExSelectorViewModel(); 
		
		selector.setDescription(description);
		selector.setExpression(expression);
		selector.setId(id);
		selector.setIndex(index);
		selector.setLastUpdate(lastUpdate);
		selector.setTestFileName(testFileName);
		//selector.setTestVariables(testVariables);
		
		for(RegExVariableSetterViewModel setter : variableSetters)
		{
			selector.getVariableSetters().add(setter.copy());
		}
		
		return selector;
	}
	
	
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
	
	public List<RegExVariableSetterViewModel> getVariableSetters()
	{
		return variableSetters;
	}

	public void setVariableSetters(List<RegExVariableSetterViewModel> setters)
	{
		this.variableSetters = setters;
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
		variableSetters.remove(Integer.parseInt(index));
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
	
	public String getTestResult()
	{
		return testResult;
	}

	public void setTestResult(String testResult)
	{
		this.testResult = testResult;
	}
	
	/*private String variablesValid = "";
	
	//the jsp framework may need this?
	public void setVariablesValid(String value)
	{
		variablesValid = value;
	}

	public String getVariablesValid()
	{
		boolean allHaveGroupAssembly = false;
		for(RegExVariableSetterViewModel set : variableSetters)
		{
			if(set.getGroupAssembly())
				found = true;
		}
		
		return variablesValid;
	}	*/
	
	
	public void createVariableSetters(List<String> variableNames)
	{
		//add setters for new variables
		for(String name : variableNames)
		{
			boolean found = false;
			for(RegExVariableSetterViewModel set : variableSetters)
			{
				if(set.getVariableName().equals(name))
					found = true;
			}
			
			if(!found)
			{
				variableSetters.add(new RegExVariableSetterViewModel(name));
			}
		}
		
		//remove setters for variables that no longer exist
		Iterator<RegExVariableSetterViewModel> it = variableSetters.iterator();
		while(it.hasNext())
		{
			RegExVariableSetterViewModel setter = it.next();

			boolean found = false;
			for(String name : variableNames)
			{
				if(name.equals(setter.getVariableName()))
					found = true;
			}
			
			if(!found)
				it.remove(); 
		}
				
	}

	
	
	
}















