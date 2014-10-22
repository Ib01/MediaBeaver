package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FileViewModel
{
	private String name;
	private String path;
	private boolean isFile;
	private boolean isSelected;
	private boolean isOpen;
	private List<FileViewModel> files = new ArrayList<FileViewModel>();
	
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
	
	

}
