package com.ibus.mediabeaver.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Event;

@Controller
@RequestMapping(value = "/events")
public class EventsController
{
	@RequestMapping
	public ModelAndView showEvents(HttpServletRequest request)
	{
		List<Event> events = Repository.getAllEntities(Event.class, "eventTime");
		return new ModelAndView("Events","events", events);
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
