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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ibus.mediabeaver.server.viewmodel.OpenSubtitlesFieldViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExPathTokenSetterViewModel;

@Controller
@RequestMapping(value = "/configWizard")
@SessionAttributes({"config"})
public class MediaConfigWizardController
{
	/*TODO
	 * 
	 * */
	private @Autowired HttpServletRequest request;
	private @Autowired HttpSession session;
	
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
	
	/*{
	    // Query is not required as of version 1.2.5
	    "query": "Unit",
	    "suggestions": [
	        { "value": "United Arab Emirates", "data": "AE" },
	        { "value": "United Kingdom",       "data": "UK" },
	        { "value": "United States",        "data": "US" }
	    ]
	}
	*/
	
	/*public class AutoCompleteSuggestion
	{
		public String value;
		public String data;
	}
	
	public class AutoComplete
	{
		public String query = "Unit";
		public AutoCompleteSuggestion[] suggestions;
	}
	
	
	@RequestMapping(value = "/testAjax", method = RequestMethod.GET)
	public @ResponseBody AutoComplete testRegEx()
	{
		AutoComplete c = new AutoComplete();
		
		AutoCompleteSuggestion s = new AutoCompleteSuggestion();
		s.data = "s1";
		s.value = "s1";
		c.suggestions = new AutoCompleteSuggestion[]{s};
		
		s = new AutoCompleteSuggestion();
		s.data = "s2";
		s.value = "s2";
		c.suggestions = new AutoCompleteSuggestion[]{s};
		
		AutoCompleteSuggestion[] d = new AutoCompleteSuggestion[]{s};
		
		return c;
	}*/
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ConfigWizard_Config ///////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * default method of wizard. called to create a new config item
	 * @return
	 */
	@RequestMapping
	public ModelAndView configLoad()
	{
		return new ModelAndView("ConfigWizard_Config", "config", new MediaConfigViewModel());
	}
	
	/**
	 * Edit an existing config item. sets up the wizard with the config item coresponding to the index passed 
	 * on the query string 
	 * @param id
	 * @return
	 */
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
	
	
	/**
	 * Go to next wizard step from the "config" step
	 * @param config
	 * @return
	 */
	@RequestMapping("configNext")
	public String configNext(@ModelAttribute("config")MediaConfigViewModel config)
	{
		/*
		 * TODO: CHECK THAT ALL PATHS WILL WORK FOR THE ENVIRONMENT ??
		 * */
		String h = System.getProperty("file.separator");
		
		
		config.sortRegExSelectorViewModels();
		
		//note: @ModelAttribute("config") ensures that incomming data is assigned to the session object stored 
		//under the "config" key. we dont need to return any object because the jsp page automatically gets data 
		//from "config" session state 
		return "ConfigWizard_OpenSubtitlesSelector";
	}
	
	
	
	
	/*@RequestMapping(value = "orderSelectors", method = RequestMethod.POST)
	public @ResponseBody RegExSelectorViewModel orderSelectors(@RequestBody RegExSelectorViewModel selector)
	{
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		
		//resort sort orders for all selectors according to the order presented in the UI
		for(int i=0; i < selector.getReorderList().length; i++)
		{
			config.getRegExSelectors().get(selector.getReorderList()[i]).setSorOrder(i);
		}
		
		//Need to explicitly call sort.  this is normally done internally in config object whenever properties are set. 
		config.sortRegExSelectorViewModels();
		
		return selector;
	}*/
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ConfigWizard_OpenSubtitlesSelectors ///////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Include or Exclude the open subtitles selector.
	 * @return
	 */
	@RequestMapping("openSubtitlesSelectorsEnable")
	public String openSubtitlesSelectorsEnable(@ModelAttribute("config")MediaConfigViewModel config)
	{
		config.getOpenSubtitlesSelectors().clear();
		if(config.isIncludeOpenSubtitles())
		{
			resetOpenSubtitlesSelectors(config);
		}
		
		return "ConfigWizard_OpenSubtitlesSelector";
	}
	
	
	/**
	 * previous step from regExSelectors step
	 * @return
	 */
	@RequestMapping("openSubtitlesSelectorsPrevious")
	public String openSubtitlesSelectorsPrevious(@ModelAttribute("config")MediaConfigViewModel config)
	{
		return "ConfigWizard_Config";
	}
	

	/**
	 * previous step from regExSelectors step
	 * @return
	 */
	@RequestMapping("openSubtitlesSelectorsNext")
	public String openSubtitlesSelectorsNext(@ModelAttribute("config")MediaConfigViewModel config)
	{
		return "ConfigWizard_RegExSelectors";
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ConfigWizard_RegExSelectors ///////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * validate RegExSelectors.  we need to make sure each selector has the required token setter data
	 * @return
	 */
	@RequestMapping(value = "/validateRegExSelectors", method = RequestMethod.GET)
	public @ResponseBody Integer[] validateRegExSelectors()
	{
		//the relative destination path might have changed so we need to reset all setters in all reg ex selectors 
		resetAllSetters();
		return getIndexsOfInvalidSelectors();
	}
	
	
	/**
	 * delete selector with respective index 
	 * @param index
	 * @return
	 */
	@RequestMapping("regExSelectorsDelete/{index}")
	public String regExSelectorsDelete(@PathVariable String index)
	{
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		config.getRegExSelectors().remove(Integer.parseInt(index));
		
		//TODO: REDIRECT TO ?
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorsUpdate")
	public ModelAndView regExSelectorsUpdate(@ModelAttribute("config")MediaConfigViewModel config)
	{
		//get a copy of the stored selector so we do not modify the selector until we save
		RegExSelectorViewModel sel = config.getRegExSelectors().get(config.getSelectedRegExSelectorIndex()).copy();
		resetSetters(sel, config);
		sel.setIndex(config.getSelectedRegExSelectorIndex());
		
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", sel);
	}
	
	@RequestMapping("regExSelectorsAdd")
	public ModelAndView regExSelectorsAdd(@ModelAttribute("config")MediaConfigViewModel config)
	{
		RegExSelectorViewModel sel = new RegExSelectorViewModel(config.getNextRegExSelectorSortNumber());
		resetSetters(sel, config);
		
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", sel);
	}
	
	
	/**
	 * previous step from regExSelectors step
	 * @return
	 */
	@RequestMapping("regExSelectorsPrevious")
	public String regExSelectorsPrevious(@ModelAttribute("config")MediaConfigViewModel config)
	{
		return "ConfigWizard_OpenSubtitlesSelector";
	}
	
	/**
	 * next step from regExSelectors step
	 * @return
	 */
	@RequestMapping("regExSelectorsNext")
	public String regExSelectorsNext(@ModelAttribute("config")MediaConfigViewModel config)
	{
		MediaConfig mc = Mapper.getMapper().map(config, MediaConfig.class);
		Repository.saveOrUpdate(mc);
		
		return "redirect:/configList";	
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ConfigWizard_RegExSelector ///////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("regExSelectorSave")
	public String regExSelectorSave(RegExSelectorViewModel selector)
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		
		if(selector.getIndex() > -1 )
		{
			config.getRegExSelectors().remove(selector.getIndex());
		}
		
		//this will also sort the selectors
		config.addRegExSelector(selector);
		return "ConfigWizard_RegExSelectors";
	}
	
	@RequestMapping("regExSelectorCancel")
	public String regExSelectorCancel()
	{
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		config.sortRegExSelectorViewModels();
		
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
				for(RegExPathTokenSetterViewModel variableSetter :  selector.getPathTokenSetters())
				{	
					String variableValue = regExHelper.assembleFileToken(captures, variableSetter.getGroupAssembly());
					
					if(variableSetter.getReplaceExpression() != null && variableSetter.getReplaceExpression().length() > 0)
						variableValue = regExHelper.cleanFileToken(variableValue, variableSetter.getReplaceExpression(), variableSetter.getReplaceWithCharacter());
					
					result += String.format("%s = %s \r\n", variableSetter.getPathTokenName(), variableValue);
				}
			}
		}
		
		selector.setTestResult(result);
		return new ModelAndView("ConfigWizard_RegExSelector","regExSelector", selector);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Utilities /////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void resetAllSetters()
	{
		//MediaConfigViewModel config = (MediaConfigViewModel)session.getAttribute("config");
		MediaConfigViewModel config = getSotredMediaConfigViewModel();
		RegExHelper rexHelper = new RegExHelper();
		List<String> variableNames = rexHelper.getFileTokenNames(config.getRelativeDestinationPath());
		
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
		List<String> variableNames = rexHelper.getFileTokenNames(config.getRelativeDestinationPath());
		sel.createVariableSetters(variableNames);
	}
	
	
	private void resetOpenSubtitlesSelectors(MediaConfigViewModel config)
	{
		//set  ore reset the variableSetters we will use
		RegExHelper rexHelper = new RegExHelper();
		List<String> names = rexHelper.getFileTokenNames(config.getRelativeDestinationPath());
		config.createOpenSubtitlesSelectors(names);
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
			for(int setIdx = 0; setIdx < selector.getPathTokenSetters().size(); setIdx++)
			{
				if(selector.getPathTokenSetters().get(setIdx).getGroupAssembly() == null || 
						selector.getPathTokenSetters().get(setIdx).getGroupAssembly().trim().length() == 0){
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
	
	

	/*@RequestMapping("regExSelectorsUpdate/{index}")
	public ModelAndView regExSelectorsUpdate(@PathVariable String index)*/
	
	
}




















