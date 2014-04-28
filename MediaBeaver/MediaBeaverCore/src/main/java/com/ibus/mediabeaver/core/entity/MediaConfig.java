package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name = "Media_Config")
public class MediaConfig extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String description;

	@Column
	private TransformAction action;

	@Column
	private String sourceDirectory;

	@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentConfig", orphanRemoval = true)
	@Cascade(
	{ CascadeType.ALL })
	private Set<ConfigVariable> configVariables = new HashSet<ConfigVariable>();

	@Column
	private boolean useOpenSubtitlesThumbprintService;

	@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentConfig", orphanRemoval = true)
	@Cascade(
	{ CascadeType.ALL })
	private Set<OpenSubtitlesFieldMap> openSubtitlesFieldMaps = new HashSet<OpenSubtitlesFieldMap>();

	@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentConfig", orphanRemoval = true)
	@Cascade(
	{ CascadeType.ALL })
	private Set<RegExSelector> regExSelectors = new HashSet<RegExSelector>();

	@Column
	private String extensionsSelector;

	@Column
	private boolean selectAllFiles;

	@Column
	private boolean selectAllFolders;

	@Column
	private boolean selectAllEmptyFolders;

	@Column
	private String destinationRoot;

	@Column
	private String relativeDestinationPath;

	/*
	 * to implement
	 * 
	 * confirm selection with media service +TMDB -map variables to required
	 * fields +TVD -map variables to required fields
	 */

	// Properties //////////////////////////////////////////////////

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

	public Set<ConfigVariable> getConfigVariables()
	{
		return configVariables;
	}
	
	/*used by the web framework*/ 
	/*public void setConfigVariables(Set<ConfigVariable> vars)
	{
		configVariables = vars;
	}*/

	public void addConfigVariable(ConfigVariable variable)
	{
		configVariables.add(variable);
		variable.setParentConfig(this);
	}

	public void removeConfigVariable(ConfigVariable variable)
	{
		configVariables.remove(variable);
		variable.setParentConfig(null);
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

	public Set<OpenSubtitlesFieldMap> getOpenSubtitlesFieldMaps()
	{
		return openSubtitlesFieldMaps;
	}

	public void addOpenSubtitlesFieldMap(OpenSubtitlesFieldMap map)
	{
		openSubtitlesFieldMaps.add(map);
		map.setParentConfig(this);
	}

	public void removeOpenSubtitlesFieldMap(OpenSubtitlesFieldMap map)
	{
		openSubtitlesFieldMaps.remove(map);
		map.setParentConfig(null);
	}

	public Set<RegExSelector> getRegExSelectors()
	{
		return regExSelectors;
	}

	public void addRegExSelector(RegExSelector regex)
	{
		regExSelectors.add(regex);
		regex.setParentConfig(this);
	}

	public void removeRegExSelector(RegExSelector regex)
	{
		regExSelectors.remove(regex);
		regex.setParentConfig(null);
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

	// utilities ///////////////////////////////////////////

	/*public ConfigVariable getConfigVariable(int index)
	{
		ConfigVariable[] vars = (ConfigVariable[])configVariables.toArray();
		
		if(index >= 0 && index < vars.length)
			return (ConfigVariable)vars[index];
			
		return null;
		
	}
	
	public RegExSelector getRegExSelector(int index)
	{
		RegExSelector[] vars = (RegExSelector[])regExSelectors.toArray();
		
		if(index >= 0 && index < vars.length)
			return (RegExSelector)vars[index];
			
		return null;
	}
	
	public OpenSubtitlesFieldMap getOpenSubtitlesFieldMap(int index)
	{
		OpenSubtitlesFieldMap[] vars = (OpenSubtitlesFieldMap[])openSubtitlesFieldMaps.toArray();
		
		if(index >= 0 && index < vars.length)
			return (OpenSubtitlesFieldMap)vars[index];
			
		return null;
	}
*/
}



















