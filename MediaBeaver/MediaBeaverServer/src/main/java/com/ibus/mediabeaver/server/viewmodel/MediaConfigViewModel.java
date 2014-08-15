package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;

import com.ibus.mediabeaver.core.entity.TransformAction;


public class MediaConfigViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private TransformAction action;
	private List<OpenSubtitlesSelectorViewModel> openSubtitlesSelectors = new ArrayList<OpenSubtitlesSelectorViewModel>();
	private List<RegExSelectorViewModel> regExSelectors = new ArrayList<RegExSelectorViewModel>();
	private String sourceDirectory;
	private String destinationRoot;
	private String relativeDestinationPath;
	private int sorOrder;

	
	//view properties
	private int selectedRegExSelectorIndex;
	
	//TODO: REMOVE?	
	private RegExSelectorViewModel selectedRegExSelector = new RegExSelectorViewModel();

	
	public int getSorOrder()
	{
		return sorOrder;
	}

	public void setSorOrder(int sorOrder)
	{
		this.sorOrder = sorOrder;
	}
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

	public List<OpenSubtitlesSelectorViewModel> getOpenSubtitlesSelectors()
	{
		return openSubtitlesSelectors;
	}
	
	public void setOpenSubtitlesSelectors(List<OpenSubtitlesSelectorViewModel> selectors)
	{
		this.openSubtitlesSelectors = selectors;
	}

	public void addOpenSubtitlesSelector(OpenSubtitlesSelectorViewModel selector)
	{
		getOpenSubtitlesSelectors().add(selector);
	}
	
	public List<RegExSelectorViewModel> getRegExSelectors()
	{
		return regExSelectors;
	}

	public void setRegExSelectors(List<RegExSelectorViewModel> regExSelectors)
	{
		this.regExSelectors = regExSelectors;
		sortRegExSelectorViewModels();
	}
	
	public void addRegExSelector(RegExSelectorViewModel selector)
	{
		this.regExSelectors.add(selector);
		sortRegExSelectorViewModels();
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

	public int getNextRegExSelectorSortNumber()
	{
		if(getRegExSelectors().size() == 0)
			return 1;
		
		int highestSortNumber =getRegExSelectors().get(getRegExSelectors().size() -1).getSorOrder();
		return highestSortNumber + 1;
	}
	
	
	public void sortRegExSelectorViewModels()
	{
		Collections.sort(regExSelectors, new RegExSelectorViewModelComparator());
	}
	
	private class RegExSelectorViewModelComparator implements Comparator<RegExSelectorViewModel> 
	{
		public int compare(RegExSelectorViewModel selector1, RegExSelectorViewModel selector2)
		{
			return Integer.compare(selector1.getSorOrder(), selector2.getSorOrder());
		}
	}
	
}

























