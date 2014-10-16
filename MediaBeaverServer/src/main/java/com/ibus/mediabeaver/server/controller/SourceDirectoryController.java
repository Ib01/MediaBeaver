package com.ibus.mediabeaver.server.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.DirectoryExplorerViewModel;

@Controller
@RequestMapping(value = "/source")
public class SourceDirectoryController 
{
	
	@RequestMapping
	public ModelAndView viewDirectory(HttpServletRequest request)
	{
		ConfigurationViewModel config = Data.getConfiguration();
		DirectoryExplorerViewModel dirExpModel = Data.getDirectoryExplorer(config.getSourceDirectory());
		
		return new ModelAndView("SourceDirectory","directory", dirExpModel);
	}
	
	
}
