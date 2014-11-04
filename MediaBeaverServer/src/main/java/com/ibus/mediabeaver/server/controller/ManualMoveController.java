package com.ibus.mediabeaver.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.ManualMoveViewModel;

@Controller
@RequestMapping(value = "/manualmove")
public class ManualMoveController
{
	@RequestMapping
	public ModelAndView manualMove(HttpServletRequest request)
	{
		ManualMoveViewModel vm = new ManualMoveViewModel();
		addMediaRoots(vm, request);
		
		vm.setReferingPage("tetts");
		vm.setSourceFile("/somefile.mkv");
		
		return new ModelAndView("ManualMove","movedata", vm);
	}
	
	
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView move(@Validated ManualMoveViewModel viewModel, HttpServletRequest request)
	{
		addMediaRoots(viewModel, request);
		
		return new ModelAndView("ManualMove","movedata", viewModel);
	}
	
	
	private ManualMoveViewModel addMediaRoots(ManualMoveViewModel vm, HttpServletRequest request)
	{
		Data data = new Data(request);
		ConfigurationViewModel config = data.getConfiguration();
		
		vm.getRootPaths().add(config.getMovieRootDirectory());
		vm.getRootPaths().add(config.getTvRootDirectory());
		
		return vm;
	}
	

}





