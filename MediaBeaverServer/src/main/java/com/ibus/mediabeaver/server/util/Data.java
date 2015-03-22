package com.ibus.mediabeaver.server.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.MediaType;
import com.ibus.mediabeaver.core.util.FileSysUtil;
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
	
	
	public Configuration getConfiguration()
	{
		Configuration config = Repository.getFirstEntity(Configuration.class);
		return config;
	}
	
	public ConfigurationViewModel getConfigurationViewModel()
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
	
	
	//-Files  --------------------------------------------------------------------//
	
	
	public FileViewModel getFileViewModel(String path)
	{
		File file = new File(path);
		FileViewModel filevm  = getFileVM(file);
		return filevm;
	}
	
	
	public List<FileViewModel> getChildFileViewmodels(String path)
	{
		File rootFile = new File(path);
		
		File[] subFilesArray = rootFile.listFiles();
		Arrays.sort(subFilesArray);
		List<File> subFiles = Arrays.asList(subFilesArray);
		
		List<FileViewModel> filevms = new ArrayList<FileViewModel>();
		
		for(File file : subFiles)
		{
			FileViewModel filevm  = getFileVM(file);
			filevms.add(filevm);
		}
		
		return filevms;
	}
	
	private FileViewModel getFileVM(File file)
	{
		ConfigurationViewModel config = getConfigurationViewModel();
		FileViewModel filevm = new FileViewModel();
		
		filevm.setFile(file.isFile());
		filevm.setName(file.getName());
		filevm.setPath(file.getAbsolutePath());
		
		if(FileSysUtil.isVideoExtension(FileSysUtil.getExtension(file.getAbsolutePath()), config.getVideoExtensionFilter()))
			filevm.setMediaType(MediaType.Video);
		
		return filevm;
	}
	
	
	//-Activities --------------------------------------------------------------------//
	
	public List<Activity> getActivities(Date date)
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Criteria criteria = s.createCriteria(Activity.class);
		
		@SuppressWarnings("unchecked")
		List<Activity> results = criteria.add(Restrictions.ge("eventTime", date)).addOrder(Order.desc("eventTime")).list();
		
		return results;
	}
	
	public List<Activity> getActivities()
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Criteria criteria = s.createCriteria(Activity.class);
		
		@SuppressWarnings("unchecked")
		List<Activity> results = criteria.addOrder(Order.desc("eventTime")).list();
		
		return results;
	}
	
	
}





























