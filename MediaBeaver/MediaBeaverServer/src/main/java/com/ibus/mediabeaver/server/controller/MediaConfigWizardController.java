package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/configWizard")
@SessionAttributes({"config"})
public class MediaConfigWizardController
{
	@ModelAttribute("actions")
	public TransformAction[] getActions()
	{
		return TransformAction.values();
	}
	
	@ModelAttribute("config")
	public MediaConfigViewModel getConfig(HttpServletRequest request)
	{
		MediaConfigViewModel vm = new MediaConfigViewModel();
		String id = request.getParameter("id");
		
		if(id != null && id.length() > 0)
		{
			MediaConfig mc = Repository.getEntity(MediaConfig.class, id);
			vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
		}
		
		return vm;
	}
	
	
	@RequestMapping
	public String addOrUpdateConfig(HttpServletRequest request)
	{
		//return new ModelAndView("MediaConfig","config", vm);
		return "ConfigWizard_Config";
	}
	
	@RequestMapping("configNext")
	public String configNext(@ModelAttribute("config")MediaConfigViewModel config)
	{
		return "ConfigWizard_Config";
	}
	
	
}




















