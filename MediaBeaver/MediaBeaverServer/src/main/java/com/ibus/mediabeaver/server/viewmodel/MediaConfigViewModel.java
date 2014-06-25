package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.OpenSubtitlesFieldMap;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class MediaConfigViewModel extends ViewModel 
{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private TransformAction action;
	private String sourceDirectory;
	private List<ConfigVariableViewModel> configVariables = new ArrayList<ConfigVariableViewModel>();
	private boolean useOpenSubtitlesThumbprintService;
	private List<OpenSubtitlesFieldMapViewModel> openSubtitlesFieldMaps = new ArrayList<OpenSubtitlesFieldMapViewModel>();
	private List<RegExSelectorViewModel> regExSelectors = new ArrayList<RegExSelectorViewModel>();
	private String extensionsSelector;
	private boolean selectAllFiles;
	private boolean selectAllFolders;
	private boolean selectAllEmptyFolders;
	private String destinationRoot;
	private String relativeDestinationPath;
	
	private int selectedRegExSelectorIndex;

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

	public boolean isUseOpenSubtitlesThumbprintService()
	{
		return useOpenSubtitlesThumbprintService;
	}

	public void setUseOpenSubtitlesThumbprintService(
			boolean useOpenSubtitlesThumbprintService)
	{
		this.useOpenSubtitlesThumbprintService = useOpenSubtitlesThumbprintService;
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
	
	public String getExtensionsSelector()
	{
		return extensionsSelector;
	}

	public void setExtensionsSelector(String extensionsSelector)
	{
		this.extensionsSelector = extensionsSelector;
	}

	public boolean isSelectAllFiles()
	{
		return selectAllFiles;
	}

	public void setSelectAllFiles(boolean selectAllFiles)
	{
		this.selectAllFiles = selectAllFiles;
	}

	public boolean isSelectAllFolders()
	{
		return selectAllFolders;
	}

	public void setSelectAllFolders(boolean selectAllFolders)
	{
		this.selectAllFolders = selectAllFolders;
	}

	public boolean isSelectAllEmptyFolders()
	{
		return selectAllEmptyFolders;
	}

	public void setSelectAllEmptyFolders(boolean selectAllEmptyFolders)
	{
		this.selectAllEmptyFolders = selectAllEmptyFolders;
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

	public void setSelectedRegExSelectorIndex(int selectedRegExSelectorIndex)
	{
		this.selectedRegExSelectorIndex = selectedRegExSelectorIndex;
	}

	/*public RegExSelectorViewModel getRegExSelector(int index)
	{
		for(RegExSelectorViewModel s : regExSelectors)
		{
			if(s.getId().equals(id))
				return s;
		}
		
		return null;
	}*/
	
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

	
	
	/*public boolean updateRegExSelector(RegExSelectorViewModel selector)
	{
		if(selector != null &&selector.getId() != null && selector.getId().length() > 0)
		{
			for(RegExSelectorViewModel s : regExSelectors)
			{
				if(s.getId().equals(selector.getId()))
				{
					regExSelectors.set(regExSelectors.indexOf(s), selector);
					return true;
				}
			}
		}
		return false;
	}*/
	
}

























