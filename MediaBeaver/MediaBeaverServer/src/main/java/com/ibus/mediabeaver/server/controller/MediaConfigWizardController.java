package com.ibus.mediabeaver.server.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;

@Controller
@RequestMapping(value = "/configWizard")
@SessionAttributes({"config"})
public class MediaConfigWizardController
{
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) 
	{
		//TODO check what format we are storing in the db
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute("actions")
	public TransformAction[] actions()
	{
		return TransformAction.values();
	}
	
	@ModelAttribute("config")
	public MediaConfigViewModel config(HttpServletRequest request)
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
	
	/*@ModelAttribute("selectedRegExSelector")
	public RegExSelectorViewModel selectedRegExSelector(HttpServletRequest request)
	{
		RegExSelectorViewModel sel = new RegExSelectorViewModel();
		String id = request.getParameter("id");
		
		if(id != null && id.length() > 0)
		{
		}
		return sel;
	}*/
	
	
	
	@RequestMapping
	public String addOrUpdateConfig(HttpServletRequest request)
	{
		//return new ModelAndView("MediaConfig","config", vm);
		return "ConfigWizard_Config";
	}
	
	
	@RequestMapping("configCancel")
	public String configCancel(@ModelAttribute("config")MediaConfigViewModel config, SessionStatus status)
	{
		status.setComplete();
		
		//TODO: REDIRECT TO ?
		return "ConfigWizard_Config";
	}
	
	
	@RequestMapping("configNext")
	public String configNext(@ModelAttribute("config")MediaConfigViewModel config)
	{
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorsPrevious")
	public String regExSelectorsPrevious()
	{
		return "ConfigWizard_Config";
	}
	
	
	@RequestMapping("regExSelectorsNext")
	public String regExSelectorsNext()
	{
		//TODO: REDIRECT TO ?
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorsAdd")
	public String regExSelectorsAdd(HttpSession session)
	{
		return "ConfigWizard_AddRegExSelector";
	}
	
	@RequestMapping("addRegExSelectorSave")
	public String addRegExSelectorSave(@ModelAttribute("config")MediaConfigViewModel config)
	{
		//SelectedRegExSelector will either be as new instance or point to an instance in our collection
		if(config.getId().length() > 0)
		{
			config.addRegExSelector(config.getSelectedRegExSelector());
		}
 
		return "ConfigWizard_RegExSelectors";
	}
	
	
}




















