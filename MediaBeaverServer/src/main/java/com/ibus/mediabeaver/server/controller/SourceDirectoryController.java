package com.ibus.mediabeaver.server.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.DirectoryExplorerViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;

@Controller
@RequestMapping(value = "/source")
public class SourceDirectoryController 
{
	
	
	@RequestMapping
	public ModelAndView viewDirectory(HttpServletRequest request)
	{
		//request.getSession().setAttribute("openSourceFolders", new HashMap<String, String>());
		
		
		
		ConfigurationViewModel config = Data.getConfiguration();
		FileViewModel filevm = Data.getFileViewModel(config.getSourceDirectory());
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
	
	//value = "/changeDirectory",
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView changeDirectory(FileViewModel viewModel, HttpServletRequest request)
	{
		//@SuppressWarnings("unchecked")
		//Map<String, String> map = (Map<String, String>) request.getSession().getAttribute("openSourceFolders");
		//map.put(viewModel.getClickedDirectory(), viewModel.getClickedDirectory());
		
		
		ConfigurationViewModel config = Data.getConfiguration();
		FileViewModel filevm = Data.getFileViewModel(config.getSourceDirectory());
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
}