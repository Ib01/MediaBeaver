package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.List;
import com.ibus.mediabeaver.server.validation.EnvironmentPath;
import com.ibus.mediabeaver.server.validation.PathExists;

//need to put this validator on the class because it seems the only way to access the relevant fields from within the validator
//@PathExists(message="This path does not exist under destination root", rootPathField = "selectedRoot", pathField = "pathEnd")
public class ManualMoveViewModel
{
	private String referingPage;
	private String sourceFile;
	List<String> rootPaths = new ArrayList<String>();
	private String selectedRoot;

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
