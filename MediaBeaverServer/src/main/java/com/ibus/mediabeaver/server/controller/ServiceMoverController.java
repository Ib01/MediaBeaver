package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/servicemover")
public class ServiceMoverController 
{
	@RequestMapping
	public ModelAndView serviceMove(HttpServletRequest request) throws Exception
	{
		/*mediatype
		
		if movie
		   show title and year field
		 
		if tv series
			show series field
			when they select series show season field
			when season selected show episodes*/
			
			
			
			
		
	
		
		
		return new ModelAndView("ManualMove","movedata", new ServiceMoverController());
	}
	
}
