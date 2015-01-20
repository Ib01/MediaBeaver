package com.ibus.mediabeaver.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MediaRemover;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.Platform;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;

@Controller
@RequestMapping(value = "/source")
public class SourceDirectoryController 
{
	
	@RequestMapping
	public ModelAndView viewDirectory(HttpServletRequest request)
	{
		Data data = new Data(request);
		ConfigurationViewModel config = data.getConfigurationViewModel();
		FileViewModel filevm = data.getFileViewModel(config.getSourceDirectory());
		
		return new ModelAndView("SourceDirectory","directory", filevm);
	}

	/*@RequestMapping(value="/matchMedia", method = RequestMethod.POST)
	public ModelAndView matchMedia(@ModelAttribute("directory") @Validated FileViewModel viewModel, BindingResult result, HttpServletRequest request) throws IOException, XmlRpcException
	{
		List<String> files = viewModel.getSelectedPaths(true);
		request.getSession().setAttribute(MediaMatcherController.FilesToMoveSessionKey, files);
		
		return new ModelAndView("redirect:/mediaMatcher");
	}*/
	
	@RequestMapping(value="/matchMedia", method = RequestMethod.POST)
	public @ResponseBody FileViewModel matchMedia(@RequestBody List<FileViewModel> viewModelList, HttpServletRequest request) 
	{
		List<String> paths = new ArrayList<String>();
		for(FileViewModel vm :  viewModelList)
		{
			paths.add(vm.getPath());
		}
		request.getSession().setAttribute(MediaMatcherController.FilesToMoveSessionKey, paths);
		
		//becasuse of this stupid fucking spring framework we have to return a model object even though we dont need it. grrr
		return new FileViewModel();
	}
	
	
	
	@RequestMapping(value="/deleteFile", method = RequestMethod.POST)
	public @ResponseBody FileViewModel deleteFile(@RequestBody FileViewModel model, HttpServletRequest request) 
	{
		MediaRemover mr = new MediaRemover();
		boolean success = mr.deleteFile(model.getPath());
		model.setOperationSuccess(success);
		
		return model;
	}
	
	@RequestMapping(value="/moveFile", method = RequestMethod.POST)
	public @ResponseBody FileViewModel moveFile(@RequestBody FileViewModel model, HttpServletRequest request) 
	{
		Configuration config = Repository.getFirstEntity(Configuration.class);
		
		MediaMover mm = Factory.getMediaMover(Platform.Web, config);
		//MediaMover mm = new MediaMover(Platform.Web, config);
		
		boolean success = mm.moveFile(model.getPath());
		model.setOperationSuccess(success);
		
		return model;
	}
	
	
	@RequestMapping(value="/openFolder", method = RequestMethod.POST)
	public @ResponseBody FileViewModel openFolder(@RequestBody FileViewModel model, HttpServletRequest request) 
	{
		Data data = new Data(request);
		FileViewModel filevm = data.getFileViewModel(model.getPath());
		filevm.setOpen(true);
		return filevm;
	}
	
}

















