package com.ibus.mediabeaver.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;

public class Data
{
	HttpServletRequest request;
	private String ConfigurationSessionKey = "configuration";
	
	public Data(HttpServletRequest request)
	{
		this.request = request;
	}
	
	//-configuration --------------------------------------------------------------------//
	
	
	public ConfigurationViewModel getConfiguration()
	{
		ConfigurationViewModel configvm =  (ConfigurationViewModel) request.getAttribute(ConfigurationSessionKey);	
		if(configvm == null)
		{
			Configuration config = Repository.getFirstEntity(Configuration.class);
			if(config == null)
				return null;
			
			configvm = Mapper.getMapper().map(config, ConfigurationViewModel.class);
			request.setAttribute(ConfigurationSessionKey, configvm);
		}
		
		return configvm;
	}
	
	public void saveOrUpdateConfig(ConfigurationViewModel model)
	{
		Configuration config = Mapper.getMapper().map(model, Configuration.class);
		Repository.saveOrUpdate(config);
		request.setAttribute(ConfigurationSessionKey, model);
	}
	
	public void saveConfig(ConfigurationViewModel model)
	{
		Configuration config = Mapper.getMapper().map(model, Configuration.class);
		Repository.saveEntity(config);
		request.setAttribute(ConfigurationSessionKey, model);
	}
	
	public void mergeConfig(ConfigurationViewModel model)
	{
		Configuration config = Mapper.getMapper().map(model, Configuration.class);
		Repository.mergeEntity(config);
		request.setAttribute(ConfigurationSessionKey, model);
	}
	
	
	//-Events --------------------------------------------------------------------//
	
	
	
	
	//-Files  --------------------------------------------------------------------//
	
	public FileViewModel getFileViewModel(String path)
	{
		return getFileViewModel(path, new FileViewModel());
	}
	
	public FileViewModel getFileViewModel(String path, FileViewModel postedModel)
	{
		File file = new File(path);
		
		FileViewModel filevm  = getFileVM(file, postedModel);
		filevm.setFiles(getChildren(file,postedModel));
		
		return filevm;
	}
	
	
	private List<FileViewModel> getChildren(File rootFile, FileViewModel postedModel)
	{
		List<File> subFiles = Arrays.asList(rootFile.listFiles());
		List<FileViewModel> filevms = new ArrayList<FileViewModel>();
		
		for(File file : subFiles)
		{
			FileViewModel filevm  = getFileVM(file,postedModel);
			
			if(!postedModel.isCloseAll() && !filevm.isFile() && (filevm.isOpen() || postedModel.isOpenAll()))
				filevm.setFiles(getChildren(file,postedModel));
			
			filevms.add(filevm);
		}
		
		return filevms;
	}
	
	
	private FileViewModel getFileVM(File file, FileViewModel postedModel)
	{
		FileViewModel filevm = new FileViewModel();
		
		filevm.setFile(file.isFile());
		filevm.setName(file.getName());
		filevm.setPath(file.getAbsolutePath());
		
		filevm.setSelected(postedModel.isSelected(filevm.getPath()));
		
		if(postedModel.isOpenAll())
		{
			filevm.setOpen(true);	
		}
		else if(postedModel.isCloseAll())
		{
			filevm.setOpen(false);	
		}
		else
		{
			filevm.setOpen(postedModel.isOpen(filevm.getPath()));
		}
		
		return filevm;
	}
	
	
	
}

















