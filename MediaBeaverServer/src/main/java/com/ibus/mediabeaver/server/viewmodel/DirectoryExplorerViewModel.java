package com.ibus.mediabeaver.server.viewmodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryExplorerViewModel
{
	private String currentPath;
	private List<FileViewModel> files = new ArrayList<FileViewModel>();
	
	public String getCurrentPath()
	{
		return currentPath;
	}
	
	public void setCurrentPath(String currentPath)
	{
		this.currentPath = currentPath;
	}
	
	public List<FileViewModel> getFiles()
	{
		return files;
	}
	public void setFiles(List<FileViewModel> files)
	{
		this.files = files;
	}
	
	
}
