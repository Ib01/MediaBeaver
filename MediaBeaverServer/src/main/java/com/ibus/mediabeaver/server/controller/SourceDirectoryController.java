package com.ibus.mediabeaver.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.filesystem.MediaMover.Platform;
import com.ibus.mediabeaver.core.filesystem.MediaRemover;
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
		request.setAttribute("test", "tetstoing");
		
		return new ModelAndView("SourceDirectory","directory", getFileViewModel(new FileViewModel(),request));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView openCloseDirectories(@ModelAttribute("directory") @Validated FileViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		return new ModelAndView("SourceDirectory","directory", getFileViewModel(viewModel,request));
	}
	
	
	@RequestMapping(value="/move", method = RequestMethod.POST)
	public ModelAndView moveFiles(@ModelAttribute("directory") @Validated FileViewModel viewModel, BindingResult result, HttpServletRequest request) throws IOException, XmlRpcException
	{
		Configuration config = Repository.getFirstEntity(Configuration.class);
		
		//move files
		List<String> files = viewModel.getSelectedPaths(true);
		MediaMover mover = new MediaMover(Platform.Web,config);
		mover.moveFiles(files);
		
		FileViewModel vm = getFileViewModel(viewModel,request);

		vm.setAction("moved");
		vm.setSuccesses(mover.getMovedMedia());
		vm.setFailures(mover.getUnmovedMedia());
		
		//show files
		return new ModelAndView("SourceDirectory","directory", vm);
	}
	

	@RequestMapping(value="/serviceMove", method = RequestMethod.POST)
	public ModelAndView serviceMove(@ModelAttribute("directory") @Validated FileViewModel viewModel, BindingResult result, HttpServletRequest request) throws IOException, XmlRpcException
	{
		List<String> files = viewModel.getSelectedPaths(true);
		request.getSession().setAttribute(ServiceMoverController.FilesToMoveSessionKey, files);
		
		return new ModelAndView("redirect:/serviceMover");
	}
	
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public ModelAndView deleteFiles(@ModelAttribute("directory") @Validated FileViewModel viewModel, BindingResult result, HttpServletRequest request) throws IOException
	{
		MediaRemover mr = new MediaRemover();
		List<String> paths = viewModel.getSelectedPaths(false);
		mr.deleteFiles(paths);
		
		FileViewModel vm = getFileViewModel(viewModel,request);
		vm.setAction("deleted");
		vm.setSuccesses(mr.getDeleted());
		vm.setFailures(mr.getNotDeleted());
		
		return new ModelAndView("SourceDirectory","directory", vm);
	}
	
	
	@RequestMapping(value="/delete2", method = RequestMethod.POST)
	public @ResponseBody FileViewModel deleteFiles2(@RequestBody FileViewModel model, HttpServletRequest request) 
	{
		return model;
	}
	
	
	
	
	
	
	
	private FileViewModel getFileViewModel(FileViewModel viewModel, HttpServletRequest request)
	{
		Data data = new Data(request);
		
		ConfigurationViewModel config = data.getConfiguration();
		FileViewModel filevm = data.getFileViewModel(config.getSourceDirectory(), viewModel);
		
		return filevm;
		
		
	}
	
	
}

















