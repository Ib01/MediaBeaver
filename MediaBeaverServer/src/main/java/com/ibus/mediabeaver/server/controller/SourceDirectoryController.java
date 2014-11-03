package com.ibus.mediabeaver.server.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.FileSystem;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MediaRemover;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.DirectoryExplorerViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;
import com.ibus.mediabeaver.core.util.*;

@Controller
@RequestMapping(value = "/source")
public class SourceDirectoryController 
{
	
	@RequestMapping
	public ModelAndView viewDirectory(HttpServletRequest request)
	{
		return getFileViewModel(new FileViewModel(),request);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView openCloseDirectories(FileViewModel viewModel, HttpServletRequest request)
	{
		return getFileViewModel(viewModel,request);
	}
	
	
	@RequestMapping(value="/move", method = RequestMethod.POST)
	public ModelAndView moveFiles(FileViewModel viewModel, HttpServletRequest request) throws IOException, XmlRpcException
	{
		Configuration config = Repository.getFirstEntity(Configuration.class);
		
		//move files
		List<String> files = viewModel.getSelectedPaths(true);
		MediaMover mover = new MediaMover();
		mover.processFiles(config, files);
		
		//show files
		return getFileViewModel(viewModel,request);
	}
	
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public ModelAndView deleteFiles(FileViewModel viewModel, HttpServletRequest request) throws IOException
	{
		MediaRemover mr = new MediaRemover();
		List<String> paths = viewModel.getSelectedPaths(false);
		mr.deleteFiles(paths);
		
		return getFileViewModel(viewModel,request);
	}
	
	
	
	
	private ModelAndView getFileViewModel(FileViewModel viewModel, HttpServletRequest request)
	{
		Data data = new Data(request);
		
		ConfigurationViewModel config = data.getConfiguration();
		FileViewModel filevm = data.getFileViewModel(config.getSourceDirectory(), viewModel);
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}
	
	
}

















