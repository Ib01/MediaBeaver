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
public class AppConfigController
{
	@ModelAttribute("config")
	public MediaConfigViewModel getInitialMediaConfigViewModel(HttpServletRequest request)
	{
		/*String id = request.getParameter("id");
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = s.beginTransaction();
		
		MediaConfig mc = Repository.getAllMediaConfig().get(0);

		tx.commit();
		
		MediaConfigViewModel vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
						
		return vm;*/
		
		
		MediaConfig mc = Repository.getAllMediaConfig().get(0);
		MediaConfigViewModel vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
		return vm;
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
	public String addConfig(HttpServletRequest request)
	{
		return "AppConfig";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String editConfig(@PathVariable int id, Model model)
	{
		return "AppConfig";
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
		
		return "AppConfig";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveConfig(@Validated @ModelAttribute("config") MediaConfigViewModel config, BindingResult result, Model model, HttpServletRequest request,  SessionStatus sessionStatus)
	{
		
		/*Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = s.beginTransaction();
		
		MediaConfig mc = Repository.getAllMediaConfig().get(0);

		tx.commit();

		
		MediaConfigViewModel vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
		MediaConfig mc2 = Mapper.getMapper().map(vm, MediaConfig.class);
		mc2.setDescription("adfaf");
		
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		tx = s.beginTransaction();
		
		Repository.updateEntity(mc2);
		
		tx.commit();

		*/
		
		//MediaConfig mc1 = Repository.getAllMediaConfig().get(0);
		
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		Repository.updateEntity(mc);
		
		//config.setVersion(config.getVersion() + 1);
		
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
