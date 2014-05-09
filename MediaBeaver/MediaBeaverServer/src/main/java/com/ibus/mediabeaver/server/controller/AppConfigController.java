package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/config")
@SessionAttributes({"config"})
public class AppConfigController
{
	@ModelAttribute("config")
	public MediaConfigViewModel getInitialMediaConfigViewModel(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		
		MediaConfig mc = Repository.getAllMediaConfig().get(0);
		MediaConfigViewModel vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
		
		return vm;
	}
	
	
	
	
	@RequestMapping(value = "/addRegExSelector", method = RequestMethod.POST)
	public String addRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, 
			HttpServletRequest request,  SessionStatus sessionStatus, RedirectAttributes ra)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		/*MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		sessionStatus.setComplete();*/
		//request.getSession().setAttribute("MediaConfigViewModelModel", config);

		return "redirect:/regExSelector";
	}
	
	
	/*@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public @ResponseBody MediaConfigViewModel updateRegExSelector(@Valid @RequestBody MediaConfigViewModel model, BindingResult result,HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		return model;
	}*/
	
	
	
	@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public String updateRegExSelector(@Validated @ModelAttribute("config") MediaConfigViewModel config, 
			BindingResult result, Model model, HttpServletRequest request,  SessionStatus sessionStatus)
	{
		
		
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		/*MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		sessionStatus.setComplete();*/
		//request.getSession().setAttribute("MediaConfigViewModelModel", config);

		return "redirect:/regExSelector/";
	}	
	
	
	
	
	@ModelAttribute("actions")
	public TransformAction[] getActions()
	{
		return TransformAction.values();
	}
	
	

	@RequestMapping(method = RequestMethod.GET)
	public String config(HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		return "AppConfig";
	}
	
	
	/*
	@RequestMapping(value = "/updateRegExSelector", method = RequestMethod.POST)
	public @ResponseBody MediaConfigViewModel updateRegExSelector(@Valid @RequestBody MediaConfigViewModel model, BindingResult result,HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		return model;
	}*/	
	
	
	

	@RequestMapping(value = "/{configId}", method = RequestMethod.GET)
	public String editConfig(@PathVariable int configId, Model model)
	{
		return "AppConfig";

		/*
		 * Owner owner = ownerService.findOwner(ownerId);
		 * model.addAttribute("owner", owner); return "displayOwner";
		 * 
		 * 
		 * UriComponents uriComponents = UriComponentsBuilder.fromUriString(
		 * "http://example.com/hotels/{hotel}/bookings/{booking}").build();
		 * 
		 * URI uri = uriComponents.expand("42", "21").encode().toUri();
		 */
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveConfig(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, Model model, HttpServletRequest request,  SessionStatus sessionStatus)
	{
	
		
		/*MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		sessionStatus.setComplete();*/
		//request.getSession().setAttribute("MediaConfigViewModelModel", config);

		return "AppConfig";
	}

	
	
	
	
	
	
	@RequestMapping(value = "/ajaxTest", method = RequestMethod.GET)
	public @ResponseBody MediaConfigViewModel ajaxTest()
	{
		MediaConfigViewModel config = new MediaConfigViewModel();
		config.setDescription("Move movie files");
		config.getConfigVariables().add(new ConfigVariableViewModel("MovieName"));
		config.getConfigVariables().add(new ConfigVariableViewModel("MovieYear"));
		
		
		return config;
	}
	
	
	

	/*
	 * @RequestMapping("/greeting") public String
	 * greeting(@RequestParam(value="name", required=false, defaultValue="")
	 * String name, Model model) { model.addAttribute("name", name); return
	 * "greeting"; }
	 */

	/*
	 * @RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
	 * public String findOwner(@PathVariable String ownerId, Model model) {
	 * Owner owner = ownerService.findOwner(ownerId);
	 * model.addAttribute("owner", owner); return "displayOwner";
	 * 
	 * 
	 * UriComponents uriComponents = UriComponentsBuilder.fromUriString(
	 * "http://example.com/hotels/{hotel}/bookings/{booking}").build();
	 * 
	 * URI uri = uriComponents.expand("42", "21").encode().toUri(); }
	 */
	/*
	 * @RequestMapping(value="/owners/{ownerId}/pets/{petId}/edit", method =
	 * RequestMethod.POST) public String processSubmit(@Validated
	 * 
	 * @ModelAttribute("pet") Pet pet, BindingResult result) {
	 * 
	 * if (result.hasErrors()) { return "petForm"; }
	 * 
	 * // ...
	 * 
	 * }
	 */

}
