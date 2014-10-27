package com.ibus.mediabeaver.server.controller;

import java.io.File;
import java.io.IOException;
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
import com.ibus.mediabeaver.core.filesystem.MediaMover;
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
		ConfigurationViewModel config = Data.getConfiguration();
		FileViewModel filevm = Data.getFileViewModel(config.getSourceDirectory());
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView openCloseDirectories(FileViewModel viewModel, HttpServletRequest request)
	{
		ConfigurationViewModel config = Data.getConfiguration();
		FileViewModel filevm = Data.getFileViewModel(config.getSourceDirectory(), viewModel);
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
	
	
	@RequestMapping(value="move", method = RequestMethod.POST)
	public ModelAndView moveFiles(FileViewModel viewModel, HttpServletRequest request) throws IOException
	{
		//move files
		List<File> files = Data.getSelectedFiles(viewModel);
		MediaMover mover = new MediaMover();
		mover.processFiles(files);
		
		//show files
		ConfigurationViewModel config = Data.getConfiguration();
		FileViewModel filevm = Data.getFileViewModel(config.getSourceDirectory(), viewModel);
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
	
	
}
