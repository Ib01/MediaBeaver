package com.ibus.mediabeaver.server.util;

import java.io.File;
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
	
	public static DirectoryExplorerViewModel getDirectoryExplorer(String path)
	{
		DirectoryExplorerViewModel dirExpModel = new DirectoryExplorerViewModel();
		
		File directory =  new File(path);
		dirExpModel.setCurrentPath(directory.getAbsolutePath());
		
		List<File> files = Arrays.asList(directory.listFiles());
		for(File f : files)
		{
			FileViewModel filevm = new FileViewModel();
			filevm.setFile(f.isFile());
			filevm.setName(f.getName());
			filevm.setSelected(false);
			
			dirExpModel.getFiles().add(filevm);
		}
		
		return dirExpModel;
	}
	
	
	
	
}
