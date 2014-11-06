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
import com.ibus.mediabeaver.server.viewmodel.ActivityViewModel;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController
{
	@RequestMapping
	public ModelAndView showEvents(HttpServletRequest request)
	{
		List<Activity> events = Repository.getAllEntities(Activity.class, "eventTime");
		ActivityViewModel vm = new ActivityViewModel();
		vm.setActivities(events);
		
		return new ModelAndView("Activity","activity", vm);
	}
	
	@RequestMapping(value = "/manualMove", method = RequestMethod.POST)
	public ModelAndView manualMove(@Validated ActivityViewModel viewModel, HttpServletRequest request)
	{
		request.getSession().setAttribute(ManualMoveController.SourcePathSessionKey, viewModel.getSelectedPath());
		request.getSession().setAttribute(ManualMoveController.ReferrerSessionKey, "/activity");
		
		return new ModelAndView("redirect:/manualmove/");
	}
	
	

}
