package com.ibus.mediabeaver.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.JsonError;
import com.ibus.mediabeaver.core.entity.MovieRegEx;
import com.ibus.mediabeaver.core.util.RegExGenerator;

@Controller
@RequestMapping(value = "/movieRegEx")
public class MovieRegExController {
	@ModelAttribute("regEx")
	public MovieRegEx getInitialisedModel() {
		MovieRegEx re = new MovieRegEx();
		re.setExpression("expression");
		re.getNameParser().setAssembledItem("assembledItem");
		return re;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String movieRegEx() {
		return "MovieRegEx";
	}

	/*
	 * @RequestMapping(value="/user.json") public @ResponseBody
	 * ValidationResponse processForm (Model model, @Valid User user,
	 * BindingResult result ) { ValidationResponse res = new
	 * ValidationResponse(); if(!result.hasErrors()){ res.setStatus("SUCCESS");
	 * } // � return res; }
	 */

	// @RequestBody

	@RequestMapping(value = "/Save", method = RequestMethod.POST)
	public @ResponseBody
	MovieRegEx saveRegEx(@Valid @RequestBody MovieRegEx model,
			BindingResult result) {
		RegExGenerator reg = new RegExGenerator();

		if (addErrors(reg, model, result))
			return model;

		Repository r = new Repository();
		r.addEntity(model);

		return model;
	}

	@RequestMapping(value = "/Test", method = RequestMethod.POST)
	public @ResponseBody
	MovieRegEx testRegEx(@Valid @RequestBody MovieRegEx model,
			BindingResult result) {
		RegExGenerator reg = new RegExGenerator();
		List<String> caps = reg.captureStrings(model.getExpression(),
				model.getTestFileName());

		if (addErrors(reg, model, result))
			return model;

		String name = reg.assembleString(caps, model.getNameParser()
				.getAssembledItem());
		String year = reg.assembleString(caps, model.getYearParser()
				.getAssembledItem());

		if (model.getNameParser().getCleaningRegEx() != null
				&& model.getNameParser().getCleaningRegEx().length() > 0)
			name = reg.cleanString(model.getNameParser().getCleaningRegEx(),
					name, " ");

		if (model.getYearParser().getCleaningRegEx() != null
				&& model.getYearParser().getCleaningRegEx().length() > 0)
			year = reg.cleanString(model.getYearParser().getCleaningRegEx(),
					name, " ");

		model.setGeneratedName(name);
		model.setGeneratedYear(year);

		return model;
	}

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

	private boolean addErrors(RegExGenerator reg, MovieRegEx model,
			BindingResult result) {
		boolean ret = false;

		List<FieldError> errors = result.getFieldErrors();

		for (FieldError error : errors) {
			model.getErrors().add(
					new JsonError(error.getField(), error.getDefaultMessage()));
			ret = true;
		}

		// throw error if assembled name does not contain a group number
		if (!model.fieldHasError("nameParser.assembledItem")
				&& !reg.containsCaptureGroup(model.getNameParser()
						.getAssembledItem())) {
			model.getErrors()
					.add(new JsonError(
							"nameParser.assembledItem",
							"This field must contain at least 1 group number in the format {n} where n is a number"));
			ret = true;
		}

		// throw error if assembled name does not contain a group number
		if (!model.fieldHasError("yearParser.assembledItem")
				&& !reg.containsCaptureGroup(model.getYearParser()
						.getAssembledItem())) {
			model.getErrors()
					.add(new JsonError(
							"yearParser.assembledItem",
							"This field must contain at least 1 group number in the format {n} where n is a number"));
			ret = true;
		}

		/*
		 * if(caps.size() == 0) { model.getErrors().add(new
		 * JsonError("expression",
		 * "This field must contain 2 or more capture groups to capture the movie name and year"
		 * )); ret = true; }
		 */

		return ret;
	}

	// this method response to POST request
	// http://localhost/spring-mvc-json/rest/cont/person
	// receives json data sent by client --> map it to Person object
	// return Person object as json
	/*
	 * @RequestMapping(value="/Test2", method = RequestMethod.GET) public
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
