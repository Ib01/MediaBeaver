package com.ibus.mediabeaver.server.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibus.mediabeaver.server.propertyeditor.ConfigVariableViewModelEditor;
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

@Controller
@RequestMapping(value = "/regExSelector")
@SessionAttributes({"configVariables"})
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
		//Clear session so to ensure that getModel is called.
		//sessionStatus.setComplete();
		
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		RegExSelectorViewModel sel =  c.getRegExSelectors().iterator().next();
		
		ModelAndView model = new ModelAndView("RegExSelector");
		return model.addObject("regExSelector", sel);
		
		/*RegExSelectorViewModel vm = new RegExSelectorViewModel();
		
		String id = request.getParameter("id");
		if(id != null && id.length() > 0)
		{
			MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
			vm = c.getRegExSelector(id);
		}
		
		return vm;*/
	}

	//@ModelAttribute("regExSelector") cannot use because system does merge with session state data very stupidly
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveSelector(RegExSelectorViewModel selector, BindingResult result, 
			HttpServletRequest request,  SessionStatus sessionStatus, RedirectAttributes ra)
	{
		removeNullRegExVariables(selector.getVariables());
		
		ModelAndView model = new ModelAndView("RegExSelector");
		model.addObject("regExSelector", selector);
		
		return model;
		
		
		/*RegExSelectorViewModel s23 = (RegExSelectorViewModel) request.getSession().getAttribute("regExSelector");
		
		//selector.setExpression("changed");
		
		//completely ovride the stored object.
		request.getSession().setAttribute("regExSelector", selector);

		RegExSelectorViewModel ddd = (RegExSelectorViewModel) request.getSession().getAttribute("regExSelector");
		
		ModelAndView model = new ModelAndView("RegExSelector");
		model.addObject("regExSelector", selector);
		
		return model;*/
		//return "RegExSelector";
	}
	
	
	//a hack to deal with the ridiculous dynamic list problem.
	private void removeNullRegExVariables(List<RegExVariableViewModel> variables)
	{
		Iterator<RegExVariableViewModel> i = variables.iterator();
		
		while (i.hasNext()) 
		{
			RegExVariableViewModel v = i.next();
			
			if(v.getConfigVariable() == null)
				i.remove();
		}
	}
	
	
	
	/*@RequestMapping(value = "/addRegExVariable", method = RequestMethod.POST)
	public @ResponseBody RegExSelectorViewModel addRegExVariableViewModel(@Valid @RequestBody RegExVariableViewModel regExVaraible, BindingResult result, HttpServletRequest request)
	{
		RegExSelectorViewModel selector = (RegExSelectorViewModel) request.getSession().getAttribute("regExSelector");
		ConfigVariableViewModel configVaraible =  selector.getConfigVariableForId(regExVaraible.getSelectedConfigVariable());
		
		regExVaraible.setConfigVariable(configVaraible);
		
		selector.getVariables().add(regExVaraible);
		
		return selector;
	}
	
	
	
	
	@RequestMapping(value = "/deleteRegExVariable", method = RequestMethod.GET)
	public @ResponseBody String deleteRegExVariableViewModel(@RequestParam(value="index") String index, HttpServletRequest request)
	{
		RegExSelectorViewModel selector = (RegExSelectorViewModel) request.getSession().getAttribute("regExSelector");
		selector.deleteRegExVariableViewModel(index);
		return index;
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@RequestMapping(value = "/Test", method = RequestMethod.POST)
	public @ResponseBody RegExVariableViewModel testRegEx(@Valid @RequestBody RegExVariableViewModel model, BindingResult result)
	{
		
	}*/
	
	
	
	
	
	/*@RequestMapping(value="/{id}")
	public String updateSelector(HttpServletRequest request, @PathVariable String id)
	{		
		RegExSelectorViewModel model = mapper.getMapper().map(sel, RegExSelectorViewModel.class);
		
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		return "RegExSelector";
	}
	*/
	
	/*
	 * @RequestMapping(value="/user.json") public @ResponseBody
	 * ValidationResponse processForm (Model model, @Valid User user,
	 * BindingResult result ) { ValidationResponse res = new
	 * ValidationResponse(); if(!result.hasErrors()){ res.setStatus("SUCCESS");
	 * } // � return res; }
	 */

	// @RequestBody

	/*@RequestMapping(value = "/Save", method = RequestMethod.POST)
	public @ResponseBody MovieRegEx saveRegEx(@Valid @RequestBody MovieRegEx model, BindingResult result)
	{
		RegExHelper reg = new RegExHelper();

		if (addErrors(reg, model, result))
			return model;

		Repository r = new Repository();
		r.addEntity(model);

		return model;
	}

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

	// this method response to POST request
	// http://localhost/spring-mvc-json/rest/cont/person
	// receives json data sent by client --> map it to Person object
	// return Person object as json
	/*
	 * @RequestMapping(value="/Test2", method = RequestMethod.GET) public
	 * 
	 * @ResponseBody MovieRegEx post(HttpServletResponse res) {
	 * 
	 * 
	 * 
	 * 
	 * //return new TestObj();
	 * 
	 * MovieRegEx re = new MovieRegEx(); re.setExpression("1");
	 * re.getNameParser().setAssembledItem("2");
	 * re.getNameParser().setRecursiveRegEx("3");
	 * re.getNameParser().setRemoveCharacters("4");
	 * re.getYearParser().setAssembledItem("5");
	 * re.getYearParser().setRecursiveRegEx("6");
	 * re.getYearParser().setRemoveCharacters("7"); re.setTestFileName("8");
	 * re.setGeneratedName("9"); re.setGeneratedYear("10");
	 * 
	 * return re;
	 * 
	 * 
	 * }
	 */

}
