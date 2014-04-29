package com.ibus.mediabeaver.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.MovieRegEx;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.RegExHelper;

@Controller
@RequestMapping(value = "/config")
@SessionAttributes("config")
public class AppConfigController
{
	@ModelAttribute("config")
	public MediaConfig getInitialMediaConfig(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		
		MediaConfig config = new MediaConfig();
		config.setDescription("Move movie files");
		config.addConfigVariable(new ConfigVariable("MovieName"));
		
		ConfigVariable v = new ConfigVariable("MovieYear");
		v.setRequired(true);
		config.addConfigVariable(v);
		
		RegExVariable rev = new RegExVariable();
		rev.setVariableName("var 1 name");
		
		RegExSelector res = new RegExSelector();
		res.setExpression("some expression");
		res.addRegExVariable(rev);
	
		config.addRegExSelector(res);
	
		
		
		
		return config;
	}
	
	@ModelAttribute("actions")
	public TransformAction[] getActions()
	{
		return TransformAction.values();
	}
	
	

	@RequestMapping(method = RequestMethod.GET)
	public String config(HttpServletRequest request)
	{
		MediaConfig c = (MediaConfig) request.getSession().getAttribute("config");
		
		return "AppConfig";
	}
	
	
	@RequestMapping(value = "/Save2", method = RequestMethod.POST)
	public @ResponseBody MediaConfig save(@Valid @RequestBody MediaConfig model, BindingResult result)
	{
		return model;
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
	public String saveConfig(@Validated @ModelAttribute("config") MediaConfig config, BindingResult result, Model model, HttpServletRequest request,  SessionStatus sessionStatus)
	{
	
		
		/*MediaConfig c = (MediaConfig) request.getSession().getAttribute("config");
		sessionStatus.setComplete();*/
		//request.getSession().setAttribute("MediaConfigModel", config);

		return "AppConfig";
	}

	
	
	
	
	
	
	@RequestMapping(value = "/ajaxTest", method = RequestMethod.GET)
	public @ResponseBody MediaConfig ajaxTest()
	{
		MediaConfig config = new MediaConfig();
		config.setDescription("Move movie files");
		config.addConfigVariable(new ConfigVariable("MovieName"));
		config.addConfigVariable(new ConfigVariable("MovieYear"));
		
		
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
