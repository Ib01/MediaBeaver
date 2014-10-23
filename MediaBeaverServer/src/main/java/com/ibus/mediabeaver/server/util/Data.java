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
		return getFileViewModel(path, null);
	}
	
	public static FileViewModel getFileViewModel(String path, FileViewModel postedModel)
	{
		File file = new File(path);
		
		FileViewModel filevm  = getFileVM(file,null);
		filevm.setFiles(getChildren(file,postedModel));
		
		return filevm;
	}
	
	private static List<FileViewModel> getChildren(File rootFile, FileViewModel postedModel)
	{
		List<File> subFiles = Arrays.asList(rootFile.listFiles());
		List<FileViewModel> filevms = new ArrayList<FileViewModel>();
		
		for(File file : subFiles)
		{
			FileViewModel filevm  = getFileVM(file,postedModel);
			
			if(!filevm.isFile() && filevm.isOpen())
				filevm.setFiles(getChildren(file,postedModel));
			
			filevms.add(filevm);
		}
		
		return filevms;
	}
	
	
	private static FileViewModel getFileVM(File file, FileViewModel postedModel)
	{
		FileViewModel filevm = new FileViewModel();
		
		filevm.setFile(file.isFile());
		filevm.setName(file.getName());
		filevm.setPath(file.getAbsolutePath());
		filevm.setSelected(false);
		filevm.setOpen(false);
		
		//Initialize the state of the file tree according to how the user last left it
		if(postedModel != null)
		{
			filevm.setSelected(postedModel.isSelected(filevm.getPath()));
			filevm.setOpen(postedModel.isOpen(filevm.getPath()));	
		}
		
		return filevm;
	}
	
}

















