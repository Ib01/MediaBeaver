package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.OpenSubtitlesFieldMap;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class MediaConfigViewModel extends ViewModel 
{
	private String description;
	private TransformAction action;
	private String sourceDirectory;
	private Set<ConfigVariableViewModel> configVariables = new HashSet<ConfigVariableViewModel>();
	private boolean useOpenSubtitlesThumbprintService;
	private Set<OpenSubtitlesFieldMapViewModel> openSubtitlesFieldMaps = new HashSet<OpenSubtitlesFieldMapViewModel>();
	private Set<RegExSelectorViewModel> regExSelectors = new HashSet<RegExSelectorViewModel>();
	private String extensionsSelector;
	private boolean selectAllFiles;
	private boolean selectAllFolders;
	private boolean selectAllEmptyFolders;
	private String destinationRoot;
	private String relativeDestinationPath;
	
	private String selectedRegExSelectorId;

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

	public Set<ConfigVariableViewModel> getConfigVariables()
	{
		return configVariables;
	}
	
	/*required by jsp and jstl*/ 
	public void setConfigVariables(Set<ConfigVariableViewModel> vars)
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

	public Set<OpenSubtitlesFieldMapViewModel> getOpenSubtitlesFieldMaps()
	{
		return openSubtitlesFieldMaps;
	}
	
	public void setOpenSubtitlesFieldMaps(Set<OpenSubtitlesFieldMapViewModel> openSubtitlesFieldMaps)
	{
		this.openSubtitlesFieldMaps = openSubtitlesFieldMaps;
	}

	public void addOpenSubtitlesFieldMap(OpenSubtitlesFieldMapViewModel map)
	{
		getOpenSubtitlesFieldMaps().add(map);
	}
	
	public Set<RegExSelectorViewModel> getRegExSelectors()
	{
		return regExSelectors;
	}

	public void setRegExSelectors(Set<RegExSelectorViewModel> regExSelectors)
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

	public String getSelectedRegExSelectorId()
	{
		return selectedRegExSelectorId;
	}

	public void setSelectedRegExSelectorId(String selectedRegExSelectorId)
	{
		this.selectedRegExSelectorId = selectedRegExSelectorId;
	}

}
