package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.OpenSubtitlesFieldMap;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.TransformAction;


public class MediaConfigViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private TransformAction action;
	private List<ConfigVariableViewModel> configVariables = new ArrayList<ConfigVariableViewModel>();
	private List<OpenSubtitlesFieldMapViewModel> openSubtitlesFieldMaps = new ArrayList<OpenSubtitlesFieldMapViewModel>();
	private List<RegExSelectorViewModel> regExSelectors = new ArrayList<RegExSelectorViewModel>();
	private String sourceDirectory;
	private String destinationRoot;
	private String relativeDestinationPath;
	
	//TODO: REMOVE?
	private int selectedRegExSelectorIndex;
	//TODO: REMOVE?	
	private RegExSelectorViewModel selectedRegExSelector = new RegExSelectorViewModel();

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public TransformAction getAction()
	{
		return action;
	}

	public void setAction(TransformAction action)
	{
		this.action = action;
	}

	public String getSourceDirectory()
	{
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory)
	{
		this.sourceDirectory = sourceDirectory;
	}

	public List<ConfigVariableViewModel> getConfigVariables()
	{
		return configVariables;
	}
	
	/*required by jsp and jstl*/ 
	public void setConfigVariables(List<ConfigVariableViewModel> vars)
	{
		configVariables = vars;
	}

	public void addConfigVariable(ConfigVariableViewModel variable)
	{
		configVariables.add(variable);
	}

	
	public List<OpenSubtitlesFieldMapViewModel> getOpenSubtitlesFieldMaps()
	{
		return openSubtitlesFieldMaps;
	}
	
	public void setOpenSubtitlesFieldMaps(List<OpenSubtitlesFieldMapViewModel> openSubtitlesFieldMaps)
	{
		this.openSubtitlesFieldMaps = openSubtitlesFieldMaps;
	}

	public void addOpenSubtitlesFieldMap(OpenSubtitlesFieldMapViewModel map)
	{
		getOpenSubtitlesFieldMaps().add(map);
	}
	
	public List<RegExSelectorViewModel> getRegExSelectors()
	{
		return regExSelectors;
	}

	public void setRegExSelectors(List<RegExSelectorViewModel> regExSelectors)
	{
		this.regExSelectors = regExSelectors;
	}
	
	public void addRegExSelector(RegExSelectorViewModel regex)
	{
		regExSelectors.add(regex);
	}

	public String getRelativeDestinationPath()
	{
		return relativeDestinationPath;
	}

	public void setRelativeDestinationPath(String destinationPath)
	{
		this.relativeDestinationPath = destinationPath;
	}

	public String getDestinationRoot()
	{
		return destinationRoot;
	}

	public void setDestinationRoot(String destinationRoot)
	{
		this.destinationRoot = destinationRoot;
	}

	public int getSelectedRegExSelectorIndex()
	{
		return selectedRegExSelectorIndex;
	}
	
	public RegExSelectorViewModel getSelectedRegExSelector()
	{
		return selectedRegExSelector;
	}

	public void setSelectedRegExSelector(RegExSelectorViewModel selectedRegExSelector)
	{
		this.selectedRegExSelector = selectedRegExSelector;
	}
	
	

	public void setSelectedRegExSelectorIndex(int selectedRegExSelectorIndex)
	{
		this.selectedRegExSelectorIndex = selectedRegExSelectorIndex;
	}

	public ConfigVariableViewModel getConfigVariable(String configName)
	{
		for(ConfigVariableViewModel cv : configVariables)
		{
			if(cv.getName().equals(configName))
				return cv;
		}
		
		return null;
	} 
	
	
	//a hack to deal with the ridiculous dynamic list problem.
	public void removeNullConfigVariables()
	{
		Iterator<ConfigVariableViewModel> i = configVariables.iterator();
		
		while (i.hasNext()) 
		{
			ConfigVariableViewModel v = i.next();
			
			if(v == null)
				i.remove();
		}
	}

	//TODO: Used?
	public RegExSelectorViewModel getRegExSelector(String id)
	{
		RegExSelectorViewModel selector = new RegExSelectorViewModel();
		for(RegExSelectorViewModel sel : regExSelectors)
		{
			if(sel.getId().equals(id))
				selector = sel;
		}
		
		return  selector;
	}

	
	
	
	
	
	
	
	
	
	/*public RegExSelectorViewModel updateOrAddRegExSelector(RegExSelectorViewModel selector)
	{
		for(RegExSelectorViewModel sel : regExSelectors)
		{
			if(sel.getId().equals(selector.getId()))
			{
				sel.setDescription(selector.getDescription());
				sel.setExpression(selector.getExpression());
				
				for(RegExVariableSetterViewModel set : sel.getVariables())
				{
					
				}
			}
		}
		
	}*/
	
	
}

























