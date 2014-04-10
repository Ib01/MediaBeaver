package com.ibus.mediaBeaverServer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediaBeaverServer.data.Repository;
import com.ibus.mediaBeaverServer.entity.MediaTransformConfig;
import com.ibus.mediaBeaverServer.entity.RenamingService;

@Controller
@RequestMapping(value = "/config")
public class AppConfigController 
{
	@ModelAttribute("config")
    public MediaTransformConfig getInitialisedModel() 
	{
		MediaTransformConfig config =  new MediaTransformConfig();
		config.setName("asdf");
		return config;
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String config() 
	{
		return "AppConfig";
	}
	

	
	
	@RequestMapping(value="/{configId}", method=RequestMethod.GET)
	public String editConfig(@PathVariable int configId, Model model) 
	{
		return "AppConfig";
		
	    /*Owner owner = ownerService.findOwner(ownerId);
	    model.addAttribute("owner", owner);
	    return "displayOwner";
	    
	    
	    UriComponents uriComponents = UriComponentsBuilder.fromUriString(
	            "http://example.com/hotels/{hotel}/bookings/{booking}").build();

	    URI uri = uriComponents.expand("42", "21").encode().toUri();*/
	}
	
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String saveConfig(@Validated @ModelAttribute("config") MediaTransformConfig config, BindingResult result, Model model) 
	{
		
		
		
		model.addAttribute("config", config);
	
		//addMediaTransformConfig();
		
		return "AppConfig";
		
		
		//return new ModelAndView("AppConfig", "command", config);
	}
	
	/*public int addMediaTransformConfig()
	{
		MediaTransformConfig it = new MediaTransformConfig();
		it.setName("ello");
		it.setProcessOrder(1);
		it.getSelectExpressions().add("exp one");
		it.getSelectExpressions().add("exp two");
		it.getSelectExtensions().add("ext one");
		it.setSelectAllContent(true);
		it.setSelectEmptyFolders(true);
		it.setDeleteSelection(true);
		it.setRenamingService(RenamingService.TMDB);
		
		return Repository.addMediaTransformConfig(it);
	}*/
	
	
	
	
	
	/*@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="") String name, Model model) 
	{
        model.addAttribute("name", name);
        return "greeting";
    }*/

	/*@RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
	public String findOwner(@PathVariable String ownerId, Model model) 
	{
	    Owner owner = ownerService.findOwner(ownerId);
	    model.addAttribute("owner", owner);
	    return "displayOwner";
	    
	    
	    UriComponents uriComponents = UriComponentsBuilder.fromUriString(
	            "http://example.com/hotels/{hotel}/bookings/{booking}").build();

	    URI uri = uriComponents.expand("42", "21").encode().toUri();
	}
	*/
	/*@RequestMapping(value="/owners/{ownerId}/pets/{petId}/edit", method = RequestMethod.POST)
	public String processSubmit(@Validated @ModelAttribute("pet") Pet pet, BindingResult result) {

	    if (result.hasErrors()) {
	        return "petForm";
	    }

	    // ...

	}*/
	
}
