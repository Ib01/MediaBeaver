package com.ibus.mediabeaver.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	/*@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated ConfigurationViewModel configViewModel)
	{
		Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveOrUpdate(config);
		
		return "redirect:/configuration/";
	}
	*/
	

}
