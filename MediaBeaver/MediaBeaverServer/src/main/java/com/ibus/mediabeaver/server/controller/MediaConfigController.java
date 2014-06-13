package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/config")
//@SessionAttributes({"config"})
public class MediaConfigController
{
	/*@ModelAttribute("config")
	public MediaConfigViewModel getInitialMediaConfigViewModel(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		if(id != null && id.length() > 0)
		{
			MediaConfig mc = Repository.getEntity(MediaConfig.class, id);
			MediaConfigViewModel vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
			return vm;
		}
		
		return new MediaConfigViewModel();
	}*/
	
	@ModelAttribute("actions")
	public TransformAction[] getActions()
	{
		return TransformAction.values();
	}
	
	@ModelAttribute("pageTitle")
	public String getPageTitle()
	{
		return "Media Configuration Item";
	}
	
	
	@RequestMapping
	public ModelAndView updateConfig(HttpServletRequest request)
	{
		MediaConfigViewModel storedConfig = (MediaConfigViewModel)request.getSession().getAttribute("config");
		if(storedConfig != null){
			return new ModelAndView("MediaConfig","config", storedConfig);
		}
		
		
		MediaConfigViewModel vm = new MediaConfigViewModel();
		String id = request.getParameter("id");
		
		if(id != null && id.length() > 0)
		{
			MediaConfig mc = Repository.getEntity(MediaConfig.class, id);
			vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
			request.getSession().setAttribute("config", vm);
		}
		
		return new ModelAndView("MediaConfig","config", vm);
		//return "MediaConfig";
	}
	
	// @ModelAttribute("config") 
	@RequestMapping(value = "/addRegExSelector", method = RequestMethod.POST)
	public String addRegExSelector(@Validated MediaConfigViewModel config, BindingResult result, HttpServletRequest request)
	{
		mergeWithStoredConfig(config, request);
		
		return "redirect:/regExSelector";
	}
	
	@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public String updateRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, HttpServletRequest request)
	{
		mergeWithStoredConfig(config, request);
		
		int index = config.getSelectedRegExSelectorIndex();
		return "redirect:/regExSelector/" + Integer.toString(index);
	}	
	
	
	
	
	
	/*@RequestMapping(value = "/deleteRegExSelector", method = RequestMethod.POST)
	public String deleteRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result)
	{
		int index = config.getSelectedRegExSelectorIndex();
		config.getRegExSelectors().remove(index);
		
		return "MediaConfig";
	}*/

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, Model model, HttpServletRequest request)
	{
		... mergeWithStoredConfig(MediaConfigViewModel config, HttpServletRequest request) here
		
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		
		if(config.getId() != null && config.getId().length()> 0)
		{
			Repository.updateEntity(mc);	
		}
		else
		{
			Repository.saveEntity(mc);
		}
		
		request.getSession().removeAttribute("config");
		//sessionStatus.setComplete();
		return "redirect:/configList";
	}
	
	@RequestMapping(value = "/cancel")
	public String cancel(HttpServletRequest request)
	{
		request.getSession().removeAttribute("config");
		//sessionStatus.setComplete();
		return "redirect:/configList";
	}
	
	public void mergeWithStoredConfig(MediaConfigViewModel config, HttpServletRequest request)
	{
		MediaConfigViewModel storedConfig = (MediaConfigViewModel)request.getSession().getAttribute("config");
		config.setRegExSelectors(storedConfig.getRegExSelectors());
		request.getSession().setAttribute("config", config);
	}
	
	
	

	/*@RequestMapping(value = "/ajaxTest", method = RequestMethod.GET)
	public @ResponseBody MediaConfigViewModel ajaxTest()
	{
		MediaConfigViewModel config = new MediaConfigViewModel();
		config.setDescription("Move movie files");
		config.getConfigVariables().add(new ConfigVariableViewModel("MovieName"));
		config.getConfigVariables().add(new ConfigVariableViewModel("MovieYear"));
		
		
		return config;
	}*/
	

}
