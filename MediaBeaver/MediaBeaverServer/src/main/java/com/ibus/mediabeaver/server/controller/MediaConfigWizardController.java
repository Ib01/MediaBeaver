package com.ibus.mediabeaver.server.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableSetterViewModel;

@Controller
@RequestMapping(value = "/configWizard")
@SessionAttributes({"config"})
public class MediaConfigWizardController
{
	/*TODO
	 *Add sort / processing order to selectors 
	 *add validation
	 * */
	private @Autowired HttpServletRequest request;
	private @Autowired HttpSession session;
	//private @Autowired HttpServletResponse response;
	//private @Autowired ServletWebRequest response;
	
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
	
	
	@RequestMapping
	public ModelAndView configLoad()
	{
		return new ModelAndView("ConfigWizard_Config", "config", new MediaConfigViewModel());
	}
	
	@RequestMapping("/{id}")
	public ModelAndView configLoad(@PathVariable String id)
	{
		MediaConfigViewModel vm = new MediaConfigViewModel();
		
		if(id != null && id.length() > 0)
		{
			MediaConfig mc = Repository.getEntity(MediaConfig.class, id);
			vm = Mapper.getMapper().map(mc, MediaConfigViewModel.class);
		}
		
		return new ModelAndView("ConfigWizard_Config", "config", vm);
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
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		Repository.saveOrUpdate(mc);
		
		return "redirect:/configList";	
	}
	
	
	@RequestMapping(value = "/validateRegExSelectors", method = RequestMethod.GET)
	public @ResponseBody Integer[] deleteConfig()
	{
		//the relative destination path might have changed so we need to reset all setters in all reg ex selectors 
		resetAllSetters();
		return getIndexsOfInvalidSelectors();
	}
	
	
	@RequestMapping("regExSelectorsDelete/{index}")
	public String regExSelectorsDelete(@PathVariable String index)
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		config.getRegExSelectors().remove(Integer.parseInt(index));
		
		//TODO: REDIRECT TO ?
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorsUpdate/{index}")
	public ModelAndView regExSelectorsUpdate(@PathVariable String index)
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		
		//get a copy of the stored selector so we do not modify the selector until we save
		RegExSelectorViewModel sel = config.getRegExSelectors().get(Integer.parseInt(index)).copy();
		resetSetters(sel, config);
		sel.setIndex(Integer.parseInt(index));
		
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", sel);
	}
	
	@RequestMapping("regExSelectorsAdd")
	public ModelAndView regExSelectorsAdd()
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		RegExSelectorViewModel sel = new RegExSelectorViewModel();

		resetSetters(sel, config);
		
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", sel);
	}
	
	
	@RequestMapping("regExSelectorSave")
	public String regExSelectorSave(RegExSelectorViewModel selector)
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		
		if(selector.getIndex() > -1 )
		{
			config.getRegExSelectors().remove(selector.getIndex());
		}
		
		config.getRegExSelectors().add(selector);	
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorCancel")
	public String regExSelectorCancel()
	{
		return "ConfigWizard_RegExSelectors";
	}
	
	
	@RequestMapping("regExSelectorTest")
	public ModelAndView regExSelectorTest(RegExSelectorViewModel selector)
	{
		String result = "";
		RegExHelper regExHelper = new RegExHelper();
		
		if(!regExHelper.matchFound(selector.getExpression(), selector.getTestFileName()))
		{
			result ="A pattern matching the supplied expression WAS NOT found in the test file name";
		}
		else
		{
			result = "A pattern matching the supplied expression WAS found in the test file name \r\n" +
					 "Variables were set as follows: \r\n";
			 
			List<String> captures = regExHelper.captureStrings(selector.getExpression(), selector.getTestFileName());
			
			if(captures.size() > 0)
			{
				for(RegExVariableSetterViewModel variableSetter :  selector.getVariableSetters())
				{	
					String variableValue = regExHelper.assembleRegExVariable(captures, variableSetter.getGroupAssembly());
					
					if(variableSetter.getReplaceExpression() != null && variableSetter.getReplaceExpression().length() > 0)
						variableValue = regExHelper.cleanString(variableValue, variableSetter.getReplaceExpression(), variableSetter.getReplaceWithCharacter());
					
					result += String.format("%s = %s \r\n", variableSetter.getVariableName(), variableValue);
				}
			}
		}
		
		selector.setTestResult(result);
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", selector);
	}
	
	
	
	private void resetAllSetters()
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		RegExHelper rexHelper = new RegExHelper();
		List<String> variableNames = rexHelper.getVariableNames(config.getRelativeDestinationPath());
		
		//add new setters and remove deleted setters from the setter collections of all selectors before proceeding further
		for(RegExSelectorViewModel selector :  config.getRegExSelectors())
		{
			selector.createVariableSetters(variableNames);	
		}
	}

	private void resetSetters(RegExSelectorViewModel sel, MediaConfigViewModel config)
	{
		//set  ore reset the variableSetters we will use
		RegExHelper rexHelper = new RegExHelper();
		List<String> variableNames = rexHelper.getVariableNames(config.getRelativeDestinationPath());
		sel.createVariableSetters(variableNames);
	}
	
	
	private Integer[] getIndexsOfInvalidSelectors()
	{
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		List<Integer> invalidList = new ArrayList<Integer>();
		
		
		//add new setters and remove deleted setters from the setter collections of all selectors before proceeding further
		for(int selIndx = 0; selIndx < config.getRegExSelectors().size(); selIndx++)
		{
			RegExSelectorViewModel selector = config.getRegExSelectors().get(selIndx);
			for(int setIdx = 0; setIdx < selector.getVariableSetters().size(); setIdx++)
			{
				if(selector.getVariableSetters().get(setIdx).getGroupAssembly() == null || 
						selector.getVariableSetters().get(setIdx).getGroupAssembly().trim().length() == 0){
					invalidList.add(selIndx);
					break;
				}
			}
		}
		
		Integer[] invalidArray = invalidList.toArray(new Integer[0]);
		return invalidArray;
		
	}
	
	
	
	private MediaConfigViewModel getSotredMediaConfigViewModel()
	{
		MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		/*if(config == null)
		{
			try
			{
				response.getResponse().sendRedirect("/configWizard");
				//response.sendRedirect("/configWizard");
			} catch (IOException e){}
		}*/
		
		return config;
	}
	
	
}




















