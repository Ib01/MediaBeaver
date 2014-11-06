package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibus.mediabeaver.server.validation.EnvironmentPath;
import com.ibus.mediabeaver.server.validation.PathExists;

public class ManualMoveViewModel
{
	private String referingPage;
	private String sourceFile;
	List<String> rootPaths = new ArrayList<String>();
	private String selectedRoot;

	@PathExists(message="This path does not exist on the file system")
	@EnvironmentPath(message="This path contains invalid path seperators")
	String pathEnd;
	
	public List<String> getRootPaths()
	{
		return rootPaths;
	}
	public void setRootPaths(List<String> rootPaths)
	{
		this.rootPaths = rootPaths;
	}
	public String getPathEnd()
	{
		return pathEnd;
	}
	public void setPathEnd(String pathEnd)
	{
		this.pathEnd = pathEnd;
	}
	public String getSelectedRoot()
	{
		return selectedRoot;
	}
	public void setSelectedRoot(String selectedRoot)
	{
		this.selectedRoot = selectedRoot;
	}
	public String getSourceFile()
	{
		return sourceFile;
	}
	public void setSourceFile(String sourceFile)
	{
		this.sourceFile = sourceFile;
	}
	public String getReferingPage()
	{
		return referingPage;
	}
	public void setReferingPage(String referingPage)
	{
		this.referingPage = referingPage;
	}
}
