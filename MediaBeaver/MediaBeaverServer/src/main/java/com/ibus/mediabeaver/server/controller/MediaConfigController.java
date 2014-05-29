package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/config")
@SessionAttributes({"config"})
public class MediaConfigController
{
	@ModelAttribute("config")
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
	}
	
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
	public String updateConfig(HttpServletRequest request)
	{
		return "MediaConfig";
	}
	
	
	@RequestMapping(value = "/addRegExSelector", method = RequestMethod.POST)
	public String addRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result)
	{
		return "redirect:/regExSelector";
	}
	
	@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public String updateRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result)
	{
		int index = config.getSelectedRegExSelectorIndex();
		return "redirect:/regExSelector/" + Integer.toString(index);
	}	
	
	@RequestMapping(value = "/deleteRegExSelector", method = RequestMethod.POST)
	public String deleteRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result)
	{
		int index = config.getSelectedRegExSelectorIndex();
		config.getRegExSelectors().remove(index);
		
		return "MediaConfig";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveConfig(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, Model model, HttpServletRequest request,  SessionStatus sessionStatus)
	{
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		Repository.updateEntity(mc);
		
		return "MediaConfig";
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
