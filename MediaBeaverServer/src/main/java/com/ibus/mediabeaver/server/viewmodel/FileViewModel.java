package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileViewModel
{
	public String OpenAllAction = "open all";
	public String SelectAllAction = "select all";
	
	private String name;
	private String path;
	private boolean isFile;
	private boolean isSelected;
	private boolean isOpen;
	private List<FileViewModel> files = new ArrayList<FileViewModel>();
	
	private Map<String, FileViewModel> cache = null;
	private boolean openAll;
	private boolean selectAll;
	private String action = "";
	
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isFile()
	{
		return isFile;
	}
	public void setFile(boolean isFile)
	{
		this.isFile = isFile;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	public void setSelected(boolean selected)
	{
		this.isSelected = selected;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public List<FileViewModel> getFiles() {
		return files;
	}
	public void setFiles(List<FileViewModel> files) {
		this.files = files;
	}
	public boolean isOpenAll()
	{
		return openAll;
	}
	public void setOpenAll(boolean openAll)
	{
		this.openAll = openAll;
	}
	public boolean isSelectAll()
	{
		return selectAll;
	}
	public void setSelectAll(boolean selectAll)
	{
		this.selectAll = selectAll;
	}
	
	
	
	public boolean isOpen(String path)
	{
		cacheFiles();
		
		FileViewModel vm = cache.get(path);
		if(vm == null)
			return false;
		
		return vm.isOpen();
	}
	
	public boolean isSelected(String path)
	{
		cacheFiles();
		
		FileViewModel vm = cache.get(path);
		if(vm == null)
			return false;
	
		return vm.isSelected();		
	}
	
	public Map<String, FileViewModel>  getFilesAsMap()
	{
		cacheFiles();
		return cache;
	}
	
	public List<String> getSelectedPaths(boolean getFilesOnly)
	{
		cacheFiles();
		List<String> l = new ArrayList<String>();
		
		for(String key : cache.keySet())
		{
			if(cache.get(key).isSelected() && (!getFilesOnly || cache.get(key).isFile()))
			{
				l.add(cache.get(key).getPath());
			}
		}
		
		return l;
	}
	
	
	private void cacheFiles()
	{
		if(cache == null)
		{
			cache = new HashMap<String, FileViewModel>();	
			addFilesToCache(this);
		}
	}
	private void addFilesToCache(FileViewModel file)
	{
		for(FileViewModel f : file.getFiles())
		{
			if(f.getFiles().size() > 0)
				addFilesToCache(f);
			
			cache.put(f.getPath(), f);
		}
	}
	
	
	
	
	
	
}













