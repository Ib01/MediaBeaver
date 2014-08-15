package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class RegExSelectorViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	//Data properties////////////////////////////////////////
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private List<RegExPathTokenSetterViewModel> pathTokenSetters = new ArrayList<RegExPathTokenSetterViewModel>();
	
	private int sorOrder = -1;
	
	//View properties////////////////////////////////////////
	
	private String testFileName;
	private String testResult;
	private int index = -1;
	//private int[] reorderList;
	private int newSorOrder = -1;
	
	
	public RegExSelectorViewModel(int sortOrder)
	{
		setSorOrder(sortOrder);
	}
	
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
		selector.setSorOrder(sorOrder);
		
		//selector.setTestVariables(testVariables);
		
		for(RegExPathTokenSetterViewModel setter : pathTokenSetters)
		{
			selector.getPathTokenSetters().add(setter.copy());
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
	
	public List<RegExPathTokenSetterViewModel> getPathTokenSetters()
	{
		return pathTokenSetters;
	}

	public void setPathTokenSetters(List<RegExPathTokenSetterViewModel> setters)
	{
		this.pathTokenSetters = setters;
	}
	
	public int getSorOrder()
	{
		return sorOrder;
	}

	public void setSorOrder(int sorOrder)
	{
		this.sorOrder = sorOrder;
	}
	

	public String getTestFileName()
	{
		return testFileName;
	}

	public void setTestFileName(String testFileName)
	{
		this.testFileName = testFileName;
	}

	public void deletePathTokenSetterViewModel(String index)
	{
		pathTokenSetters.remove(Integer.parseInt(index));
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
	
	/*public int[] getReorderList()
	{
		return reorderList;
	}

	public void setReorderList(int[] reorderList)
	{
		this.reorderList = reorderList;
	}*/
	public int getNewSorOrder()
	{
		return newSorOrder;
	}

	public void setNewSorOrder(int newSorOrder)
	{
		this.newSorOrder = newSorOrder;
	}

	
	
	public void createVariableSetters(List<String> variableNames)
	{
		//add setters for new variables
		for(String name : variableNames)
		{
			boolean found = false;
			for(RegExPathTokenSetterViewModel set : pathTokenSetters)
			{
				if(set.getPathTokenName().equals(name))
					found = true;
			}
			
			if(!found)
			{
				pathTokenSetters.add(new RegExPathTokenSetterViewModel(name));
			}
		}
		
		//remove setters for variables that no longer exist
		Iterator<RegExPathTokenSetterViewModel> it = pathTokenSetters.iterator();
		while(it.hasNext())
		{
			RegExPathTokenSetterViewModel setter = it.next();

			boolean found = false;
			for(String name : variableNames)
			{
				if(name.equals(setter.getPathTokenName()))
					found = true;
			}
			
			if(!found)
				it.remove(); 
		}
				
	}

	
	

	
	
	
}















