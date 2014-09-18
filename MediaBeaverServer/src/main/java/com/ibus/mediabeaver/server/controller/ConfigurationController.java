package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController
{
	@RequestMapping
	public ModelAndView updateConfig(HttpServletRequest request)
	{
		//requestx.sendRedirect(arg0); HttpServletResponse requestx
		Configuration configs = Repository.getFirstEntity(Configuration.class);
		ConfigurationViewModel vm = Mapper.getMapper().map(configs, ConfigurationViewModel.class);
		
		return new ModelAndView("Configuration","configuration", vm);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated ConfigurationViewModel configViewModel)
	{
		Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveOrUpdate(config);
		
		return "redirect:/configuration/";
	}
	
	

}
