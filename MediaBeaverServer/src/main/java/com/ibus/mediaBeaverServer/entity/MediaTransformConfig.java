package com.ibus.mediaBeaverServer.entity;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/*
Operation Description: e.g. move all movies
Target Directory: \bla\movies

Action
	- Delete
	- move

Media Type: 
	- Movie
	- Tv Episode
	- Song

if(delete)
{
	content selection:
		+ extension
		+ reg ex
		+ file 
		+ folder
		+ empty folder
}
else
{
	content selection:
		+ extension
		+ reg ex
	
	Naming Service (used to confirm media)  
		+TMDB
		+TVDB
	
	destination folder: eg {Tv Series}\Season {Season number} 
}
*/

@Entity(name="Media_Transform_Config")
public class MediaTransformConfig implements Serializable, com.ibus.mediaBeaverServer.entity.Entity
{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column
	String name;
	
	@Column
	private
	String targetDirectory;
	
	@Column
	private int processOrder; 
	
	@Column
	@Enumerated(EnumType.STRING)
	private TransformAction action;
	
	@Column
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;
	
	@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy= "parentConfig", orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	private List<MovieRegEx> selectExpressions = new ArrayList<MovieRegEx>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection()
	@CollectionTable(name="Select_Extension", joinColumns = @JoinColumn(name="Media_Transform_Config_Id"))  
	@Column
	private List<String> selectExtensions = new ArrayList<String>();
	
	@Column
	private boolean selectAllContent;
	
	@Column
	@Enumerated(EnumType.STRING)
	private RenamingService renamingService;
	
	@Column
	private String destinationFolder;
	
	
	public int getId() {
		return id;  
	}
	public void setId(int  id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean selectAllContent() {
		return selectAllContent;
	}
	public void setSelectAllContent(boolean selectAllContent) {
		this.selectAllContent = selectAllContent;
	}
	public RenamingService getRenamingService() {
		return renamingService;
	}
	public void setRenamingService(RenamingService renamingService) {
		this.renamingService = renamingService;
	}
	public String getDestinationFolder() {
		return destinationFolder;
	}
	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	public int getProcessOrder() {
		return processOrder;
	}
	public void setProcessOrder(int processOrder) {
		this.processOrder = processOrder;
	}
	public String getTargetDirectory() {
		return targetDirectory;
	}
	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}
	
	public void addSelectExpression(MovieRegEx expression)
	{
		selectExpressions.add(expression);
		expression.setParentConfig(this);
	}
	
	public void removeSelectExpression(MovieRegEx expression)
	{
		selectExpressions.remove(expression);
		expression.setParentConfig(null);
	}
	
	public List<MovieRegEx> getSelectExpressions() {
		return selectExpressions;
	}
	
	public TransformAction getAction() {
		return action;
	}
	public void setAction(TransformAction action) {
		this.action = action;
	}
	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	
	
}










