package com.ibus.mediabeaver.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.Persistable;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.DirectoryExplorerViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;

public class Data
{
	public static ConfigurationViewModel getConfiguration()
	{
		Configuration configs = Repository.getFirstEntity(Configuration.class);
		ConfigurationViewModel vm = Mapper.getMapper().map(configs, ConfigurationViewModel.class);
		
		return vm;
	}
	
	public static void saveOrUpdateConfig(ConfigurationViewModel model)
	{
		Configuration config = Mapper.getMapper().map(model, Configuration.class);
		Repository.saveOrUpdate(config);
	}
	
	public static void saveConfig(ConfigurationViewModel model)
	{
		Configuration config = Mapper.getMapper().map(model, Configuration.class);
		Repository.saveEntity(config);
	}
	
	public static FileViewModel getFileViewModel(String path)
	{
		File file = new File(path);
		
		FileViewModel filevm  = getFileVM(file);
		filevm.setFiles(getChildren(file));
		
		return filevm;
	}
	
	private static List<FileViewModel> getChildren(File rootFile)
	{
		List<File> subFiles = Arrays.asList(rootFile.listFiles());
		List<FileViewModel> filevms = new ArrayList<FileViewModel>();
		
		for(File file : subFiles)
		{
			FileViewModel filevm  = getFileVM(file);
			if(file.isDirectory())
				filevm.setFiles(getChildren(file));
			
			filevms.add(filevm);
		}
		
		return filevms;
	}
	
	private static FileViewModel getFileVM(File file)
	{
		FileViewModel filevm = new FileViewModel();
		
		filevm.setFile(file.isFile());
		filevm.setName(file.getName());
		filevm.setPath(file.getAbsolutePath());
		filevm.setSelected(false);
		
		return filevm;
	}
	
}

















