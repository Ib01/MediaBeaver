package com.ibus.mediabeaver.server.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

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
	public ModelAndView viewConfig(HttpServletRequest request)
	{
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
	
	@RequestMapping(value = "/welcome")
	public ModelAndView welcome()
	{
		return new ModelAndView("Welcome","configuration", new ConfigurationViewModel());
		
	}
	
	@RequestMapping(value = "/initialise", method = RequestMethod.POST)
	public String initialise(@Validated ConfigurationViewModel configViewModel)
	{
		java.nio.file.FileSystems.getDefault().getSeparator();
		
		
		/*Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveEntity(config);*/
		
		return "redirect:/configuration/";
	}
	
	
	

}
