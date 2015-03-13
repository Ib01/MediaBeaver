package com.ibus.mediabeaver.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.MediaMover;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.Platform;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.viewmodel.ActivityViewModel;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController
{
	public static String LastDateSessionKey = "ActivityController_LastDate";
	
	@RequestMapping
	public ModelAndView showEvents(HttpServletRequest request)
	{
		Data data = new Data(request);
		Date lastDate = (Date) request.getSession().getAttribute(LastDateSessionKey);
		
		if(lastDate == null)
		{
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			lastDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
			
			request.getSession().setAttribute(LastDateSessionKey, lastDate);
		}
		
		List<Activity> activities = data.getActivities(lastDate);
		ActivityViewModel vm = new ActivityViewModel();
		vm.setActivities(activities);
		vm.setEarlistDate(lastDate);
		
		return new ModelAndView("Activity","activity", vm);
	}
	
	
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public ModelAndView filter(@ModelAttribute("activity") @Validated ActivityViewModel viewModel, BindingResult result,  HttpServletRequest request)
	{
		return getFilteredViewModel(request, viewModel);
		/*Data data = new Data(request);
		
		List<Activity> activities;
		if(viewModel.getEarlistDate() == null)
			activities = data.getActivities();
		else
			activities = data.getActivities(viewModel.getEarlistDate());
		viewModel.setActivities(activities);
		
		request.getSession().setAttribute(LastDateSessionKey, viewModel.getEarlistDate());
		
		return new ModelAndView("Activity","activity", viewModel);*/
	}
	
	@RequestMapping(value = "/match", method = RequestMethod.POST)
	public ModelAndView matchFile(@Validated ActivityViewModel viewModel, HttpServletRequest request)
	{
		List<String> paths = new ArrayList<String>();
		paths.add(viewModel.getSelectedPath());
		
		request.getSession().setAttribute(MediaMatcherController.FilesToMoveSessionKey, paths);
		request.getSession().setAttribute(MediaMatcherController.ReffererSessionKey, "/activity");
		
		return new ModelAndView("redirect:/mediaMatcher/");
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView moveFile(@ModelAttribute("activity") @Validated ActivityViewModel viewModel, BindingResult result,  HttpServletRequest request)
	{
		Configuration config = Repository.getFirstEntity(Configuration.class);
		
		MediaMover mm = Factory.getMediaMover(Platform.Web, config);
		boolean success = mm.moveFile(viewModel.getSelectedPath());
		//model.setOperationSuccess(success);
		
		//return model;
		return getFilteredViewModel(request, viewModel);
	}
	
	
	private ModelAndView getFilteredViewModel(HttpServletRequest request, ActivityViewModel viewModel)
	{
		Data data = new Data(request);
		
		List<Activity> activities;
		if(viewModel.getEarlistDate() == null)
			activities = data.getActivities();
		else
			activities = data.getActivities(viewModel.getEarlistDate());
		viewModel.setActivities(activities);
		
		request.getSession().setAttribute(LastDateSessionKey, viewModel.getEarlistDate());
		
		return new ModelAndView("Activity","activity", viewModel);
	}
	
	
}




















