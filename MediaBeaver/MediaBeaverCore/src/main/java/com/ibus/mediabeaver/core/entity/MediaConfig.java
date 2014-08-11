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

	/*@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private Set<ConfigVariable> configVariables = new HashSet<ConfigVariable>();*/

	/*@Column
	private boolean useOpenSubtitlesThumbprintService;*/

	@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private Set<OpenSubtitlesSelector> openSubtitlesSelectors = new HashSet<OpenSubtitlesSelector>();

	@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private Set<RegExSelector> regExSelectors = new HashSet<RegExSelector>();
	
/*	@Column
	private String extensionsSelector;

	@Column
	private boolean selectAllFiles;

	@Column
	private boolean selectAllFolders;*/

	/*@Column
	private boolean selectAllEmptyFolders;*/

	@Column
	private String sourceDirectory;
	
	@Column
	private String destinationRoot;

	@Column
	private String relativeDestinationPath;

	@Column
	private int sorOrder;
	
	/*
	 * to implement
	 * 
	 * confirm selection with media service +TMDB -map variables to required
	 * fields +TVD -map variables to required fields
	 */

	// Properties //////////////////////////////////////////////////

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

	public Set<OpenSubtitlesSelector> getOpenSubtitlesSelectors()
	{
		return openSubtitlesSelectors;
	}
	
	public void setOpenSubtitlesSelectors(Set<OpenSubtitlesSelector> selectors)
	{
		this.openSubtitlesSelectors = selectors;
	}

	public void addOpenSubtitlesSelector(OpenSubtitlesSelector selector)
	{
		getOpenSubtitlesSelectors().add(selector);
	}
	
	public Set<RegExSelector> getRegExSelectors()
	{
		return regExSelectors;
	}

	public void setRegExSelectors(Set<RegExSelector> regExSelectors)
	{
		this.regExSelectors = regExSelectors;
	}
	
	public void addRegExSelector(RegExSelector regex)
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

	// utilities ///////////////////////////////////////////

	/*
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



















