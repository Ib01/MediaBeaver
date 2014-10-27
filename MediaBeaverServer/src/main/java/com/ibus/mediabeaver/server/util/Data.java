package com.ibus.mediabeaver.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
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
	
	
	//---------------------------------------------------------------------//
	
	public static FileViewModel getFileViewModel(String path)
	{
		return getFileViewModel(path, new FileViewModel());
	}
	
	public static FileViewModel getFileViewModel(String path, FileViewModel postedModel)
	{
		File file = new File(path);
		
		FileViewModel filevm  = getFileVM(file, postedModel);
		filevm.setFiles(getChildren(file,postedModel));
		
		return filevm;
	}
	
	public static List<File> getSelectedFiles(FileViewModel postedModel)
	{
		List<File> files = new ArrayList<File>();
		Map<String, FileViewModel> fileMap = postedModel.getFilesAsMap();
		
		for(String key : fileMap.keySet())
		{
			if(fileMap.get(key).isSelected())
			{
				File file = new File(fileMap.get(key).getPath());
				files.add(file);
			}
		}
		
		return files;
	}
	
	
	
	private static List<FileViewModel> getChildren(File rootFile, FileViewModel postedModel)
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
	
	
	private static FileViewModel getFileVM(File file, FileViewModel postedModel)
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

















