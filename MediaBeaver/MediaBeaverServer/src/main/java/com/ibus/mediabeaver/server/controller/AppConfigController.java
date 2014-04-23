package com.ibus.mediabeaver.server.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;

@Controller
@RequestMapping(value = "/config")
public class AppConfigController
{
	@ModelAttribute("config")
	public MediaConfig getInitialisedModel()
	{
		MediaConfig config = new MediaConfig();
		config.setDescription("Move movie files");
		config.addConfigVariable(new ConfigVariable("MovieName"));
		config.addConfigVariable(new ConfigVariable("MovieYear"));
		
		//config.getAction().
		
		return config;
	}
	
	@ModelAttribute("actions")
	public TransformAction[] getActions()
	{
		return TransformAction.values();
	}
	
	

	@RequestMapping(method = RequestMethod.GET)
	public String config()
	{
		return "AppConfig";
	}

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
	public String saveConfig(@Validated @ModelAttribute("config") MediaConfig config, BindingResult result, Model model)
	{

		model.addAttribute("config", config);

		// addMediaTransformConfig();

		return "AppConfig";

		// return new ModelAndView("AppConfig", "command", config);
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
