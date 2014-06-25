package com.ibus.mediabeaver.server.controller;

import java.util.Iterator;
import java.util.List;

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
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

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
	}
	
	// @ModelAttribute("config") 
	@RequestMapping(value = "/addRegExSelector", method = RequestMethod.POST)
	public String addRegExSelector(@Validated MediaConfigViewModel config, BindingResult result, HttpServletRequest request)
	{
		request.getSession().setAttribute("config", config);
		return "redirect:/regExSelector";
	}
	
	@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public String updateRegExSelector(@Validated MediaConfigViewModel config, BindingResult result, HttpServletRequest request)
	{
		request.getSession().setAttribute("config", config);
		
		int index = config.getSelectedRegExSelectorIndex();
		return "redirect:/regExSelector/" + Integer.toString(index);
	}	
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated MediaConfigViewModel config, BindingResult result, Model model, HttpServletRequest request)
	{
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		
		Repository.saveOrUpdate(mc);
		
		request.getSession().removeAttribute("config");
		return "redirect:/configList";
	}
	
	
	@RequestMapping(value = "/cancel")
	public String cancel(HttpServletRequest request)
	{
		request.getSession().removeAttribute("config");
		return "redirect:/configList";
	}
	
	public void mergeWithStoredConfig(MediaConfigViewModel config, HttpServletRequest request)
	{
		MediaConfigViewModel storedConfig = (MediaConfigViewModel)request.getSession().getAttribute("config");
		config.setRegExSelectors(storedConfig.getRegExSelectors());
		request.getSession().setAttribute("config", config);
	}
	
	

}
