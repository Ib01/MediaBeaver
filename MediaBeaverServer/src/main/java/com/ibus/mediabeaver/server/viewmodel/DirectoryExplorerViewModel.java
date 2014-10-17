package com.ibus.mediabeaver.server.viewmodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryExplorerViewModel
{
	private File rootDirectory;
	//private String rootDirectory;
	private String clickedDirectory;
	private boolean openningDirectory; 
	//private List<File> files = new ArrayList<File>();
	
	public String getClickedDirectory()
	{
		return clickedDirectory;
	}
	
	public void setClickedDirectory(String directory)
	{
		this.clickedDirectory = directory;
	}
	
	/*public List<File> getFiles()
	{
		return files;
	}
	public void setFiles(List<File> files)
	{
		this.files = files;
	}*/

	public boolean isOpenningDirectory() {
		return openningDirectory;
	}

	public void setOpenningDirectory(boolean openDirectory) {
		this.openningDirectory = openDirectory;
	}

	public File getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/*public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}*/
	
	
}
