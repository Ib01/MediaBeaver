package com.ibus.mediabeaver.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;

@Controller
@RequestMapping(value = "/regExSelector")
@SessionAttributes({"regExSelector"})
public class RegExSelectorController
{
	Mapper mapper = new Mapper();
	
	@ModelAttribute("regExSelector")
	public RegExSelectorViewModel getModel(HttpServletRequest request)
	{
		RegExSelectorViewModel vm = new RegExSelectorViewModel();
		
		String id = request.getParameter("id");
		if(id != null && id.length() > 0)
		{
			RegExSelector sel = Repository.getEntity(RegExSelector.class, id);
			vm = mapper.getMapper().map(sel, RegExSelectorViewModel.class);	
		}
		
		return vm;
	}

	@RequestMapping
	public String addSelector(HttpServletRequest request, SessionStatus sessionStatus)
	{
		//Clear session so to ensure that getModel is called.
		sessionStatus.setComplete();
		
		MediaConfigViewModel c = (MediaConfigViewModel) request.getSession().getAttribute("config");
		
		return "RegExSelector";
	}

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
