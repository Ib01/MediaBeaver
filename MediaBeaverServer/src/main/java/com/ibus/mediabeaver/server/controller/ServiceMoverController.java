package com.ibus.mediabeaver.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.server.viewmodel.FileViewModel;
import com.ibus.mediabeaver.server.viewmodel.ServiceMoverViewModel;

@Controller
@RequestMapping(value = "/serviceMover")
public class ServiceMoverController 
{
	public static final String FilesToMoveSessionKey = "ServiceMover.FilesToMove";
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView serviceMove(HttpServletRequest request) throws Exception
	{
		/*mediatype
		
		if movie
		   show title and year field
		 
		if tv series
			show series field
			when they select series show season field
			when season selected show episodes*/

		List<String> paths = (List<String>) request.getSession().getAttribute(FilesToMoveSessionKey);	
		
		ServiceMoverViewModel vm = new ServiceMoverViewModel();
		vm.setFilesToResolve(paths);
		
		
		return new ModelAndView("SelectMediaType","ViewModel", vm);
	}
	
	
	@RequestMapping(value="/selectMediaTypeNext", method = RequestMethod.POST)
	public ModelAndView selectMediaTypeNext(@ModelAttribute("ViewModel") @Validated ServiceMoverViewModel viewModel, BindingResult result, HttpServletRequest request) 
	{
		
		
		return new ModelAndView("SelectMediaType","ViewModel", viewModel);		
	}
	
	
}
