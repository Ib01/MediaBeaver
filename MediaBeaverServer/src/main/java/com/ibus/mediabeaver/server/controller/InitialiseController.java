package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;

@Controller
@RequestMapping(value = "/initialise")
public class InitialiseController
{
	@RequestMapping
	public ModelAndView initialise(HttpServletRequest request)
	{
		Configuration config = new Configuration();
		
		return new ModelAndView("Initialise","configuration", config);
	}
}
