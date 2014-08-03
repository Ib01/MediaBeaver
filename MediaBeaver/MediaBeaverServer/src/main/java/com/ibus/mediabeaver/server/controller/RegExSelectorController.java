package com.ibus.mediabeaver.server.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.util.RegExHelper;
import com.ibus.mediabeaver.server.propertyeditor.ConfigVariableViewModelEditor;
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableSetterViewModel;

/**
 * @author ikr
 *
 *TODO:
 *
 *1) REMOVE TO_ADD REG EX FIELDS AND SIMPLY ADD A BUTTON WHICH WILL ADD A NEW REG EX.
 *2) INSTEAD OF HAVING TEST RESULTS FIELDS SIMPLY HAVE A TEXT AREA WHICH SHOWS THE RESULTS WITH SOMETHING LIKE:
 *
 * -- 
 *Test failed: no match for expression found in specified file name
 *...
 *
 *-- 
 *Test succeeded: a match for expression was found in specified file name
 *
 *the following variables were populated from groups found: 
 *name: ...
 *year: ...
 */
@Controller
@RequestMapping(value = "/regExSelector")
//@SessionAttributes({"configVariables"})
public class RegExSelectorController
{
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		//bind a converter to convert ConfigVariableView id to object. useful when binding dropdown values 
		binder.registerCustomEditor(
				ConfigVariableViewModel.class, new ConfigVariableViewModelEditor(c.getConfigVariables())
		);
	}
	
	@ModelAttribute("configVariables")
	public List<ConfigVariableViewModel> getConfigVariables(HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		return c.getConfigVariables();
	}

	@RequestMapping
	public ModelAndView addSelector(HttpServletRequest request, SessionStatus sessionStatus)
	{
		return new ModelAndView("RegExSelector","regExSelector", new RegExSelectorViewModel());
	}
	
	@RequestMapping(value = "/{index}")
	public ModelAndView editSelector(@PathVariable int index, HttpServletRequest request)
	{
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		RegExSelectorViewModel sel = new RegExSelectorViewModel();
		if(index >= 0 && index < c.getRegExSelectors().size()){
			sel = c.getRegExSelectors().get(index);
			sel.setIndex(index);
		}
		return new ModelAndView("RegExSelector","regExSelector", sel);
	}
	

	//@ModelAttribute("regExSelector") cannot use because system does merge with session state data very stupidly
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSelector(RegExSelectorViewModel selector, BindingResult result, HttpServletRequest request)
	{
		removeNullRegExVariables(selector.getVariableSetters());
		
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		if(selector.getIndex() > -1)
			c.getRegExSelectors().set(selector.getIndex(), selector);
		else
			c.getRegExSelectors().add(selector);
		
		return "redirect:/config";
	}
	
	

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody RegExSelectorViewModel testRegEx(@Valid @RequestBody RegExSelectorViewModel selector, BindingResult result, HttpServletRequest request)
	{
		RegExHelper regExHelper = new RegExHelper();
		List<String> captures = regExHelper.captureStrings(selector.getExpression(), selector.getTestFileName());
		
		if(captures.size() > 0)
		{
			for(RegExVariableSetterViewModel regExVar :  selector.getVariableSetters())
			{
				String cleanedVar = regExHelper.assembleRegExVariable(captures, regExVar.getGroupAssembly());
				
				if(regExVar.getReplaceExpression() != null && regExVar.getReplaceExpression().length() > 0)
					cleanedVar = regExHelper.cleanString(cleanedVar, regExVar.getReplaceExpression(), regExVar.getReplaceWithCharacter());
				
				ConfigVariableViewModel cv = new ConfigVariableViewModel();
				cv.setId(regExVar.getSelectedConfigVariable());
				cv.setValue(cleanedVar);
				
				selector.getTestVariables().add(cv);
			}
		}
		
		return selector;
	}
	
	
	//a hack to deal with the ridiculous dynamic list problem.
	private void removeNullRegExVariables(List<RegExVariableSetterViewModel> variables)
	{
		Iterator<RegExVariableSetterViewModel> i = variables.iterator();
		
		while (i.hasNext()) 
		{
			RegExVariableSetterViewModel v = i.next();
			
			/*if(v.getConfigVariable() == null)
				i.remove();*/
		}
	}
	
	
	
	

	/*
	, SessionStatus sessionStatus
	
	
	@RequestMapping(value = "/Test", method = RequestMethod.POST)
	public @ResponseBody MovieRegEx testRegEx(@Valid @RequestBody MovieRegEx model,
			BindingResult result)
	{
		RegExHelper reg = new RegExHelper();
		List<String> caps = reg.captureStrings(model.getExpression(),
				model.getTestFileName());

		if (addErrors(reg, model, result))
			return model;

		String name = reg.assembleRegExVariable(caps, model.getNameParser()
				.getAssembledItem());
		String year = reg.assembleRegExVariable(caps, model.getYearParser()
				.getAssembledItem());

		if (model.getNameParser().getCleaningRegEx() != null
				&& model.getNameParser().getCleaningRegEx().length() > 0)
			name = reg.cleanStringRegEx(model.getNameParser()
					.getCleaningRegEx(), name, " ");

		if (model.getYearParser().getCleaningRegEx() != null
				&& model.getYearParser().getCleaningRegEx().length() > 0)
			year = reg.cleanStringRegEx(model.getYearParser()
					.getCleaningRegEx(), name, " ");

		model.setGeneratedName(name);
		model.setGeneratedYear(year);

		return model;
	}*/

	/*
	 * public static void WriteFile() { PrintWriter writer; try { writer = new
	 * PrintWriter("C:\\DeleteMe.txt", "UTF-8");
	 * writer.println("The first line"); writer.println("The second line");
	 * writer.close();
	 * 
	 * } catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (UnsupportedEncodingException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	/*private boolean addErrors(RegExHelper reg, MovieRegEx model,
			BindingResult result)
	{
		boolean ret = false;

		List<FieldError> errors = result.getFieldErrors();

		for (FieldError error : errors)
		{
			model.getErrors().add(
					new JsonError(error.getField(), error.getDefaultMessage()));
			ret = true;
		}

		// throw error if assembled name does not contain a group number
		if (!model.fieldHasError("nameParser.assembledItem")
				&& !reg.containsCaptureGroup(model.getNameParser()
						.getAssembledItem()))
		{
			model.getErrors()
					.add(new JsonError(
							"nameParser.assembledItem",
							"This field must contain at least 1 group number in the format {n} where n is a number"));
			ret = true;
		}

		// throw error if assembled name does not contain a group number
		if (!model.fieldHasError("yearParser.assembledItem")
				&& !reg.containsCaptureGroup(model.getYearParser()
						.getAssembledItem()))
		{
			model.getErrors()
					.add(new JsonError(
							"yearParser.assembledItem",
							"This field must contain at least 1 group number in the format {n} where n is a number"));
			ret = true;
		}

		
		 * if(caps.size() == 0) { model.getErrors().add(new
		 * JsonError("expression",
		 * "This field must contain 2 or more capture groups to capture the movie name and year"
		 * )); ret = true; }
		 

		return ret;
	}*/

	

}
