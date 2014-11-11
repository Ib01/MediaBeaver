package com.ibus.mediabeaver.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.filesystem.FileSystem;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;
import com.ibus.mediabeaver.server.viewmodel.ManualMoveViewModel;

@Controller
@RequestMapping(value = "/manualmove")
public class ManualMoveController
{
	public static String SourcePathSessionKey ="ManualMove.SourcePath";
	public static String ReferrerSessionKey ="ManualMove.Referrer";
	FileSystem fileSys = new FileSystem();
	
	@RequestMapping
	public ModelAndView manualMove(HttpServletRequest request) throws Exception
	{
		String sourcePath = (String) request.getSession().getAttribute(SourcePathSessionKey);
		if(sourcePath == null || sourcePath.trim().length() == 0){
			throw new Exception("no source path provided");
			
			//TODO: redirect to home page when it is created
			//return new ModelAndView("redirect://");
		}
		
		String referrer = (String) request.getSession().getAttribute(ReferrerSessionKey);
		if(referrer == null || referrer.trim().length() == 0){
			throw new Exception("no referrer provided");
			
			//TODO: redirect to home page when it is created
			//return new ModelAndView("redirect://");
		}
		
		//set session variables to null so this move cannot be repeated without coming from another page
		request.getSession().setAttribute(SourcePathSessionKey, null);
		request.getSession().setAttribute(ReferrerSessionKey, null);
		
		
		ManualMoveViewModel vm = new ManualMoveViewModel();
		addMediaRoots(vm, request);
		
		vm.setReferingPage(referrer);
		vm.setSourceFile(sourcePath);
		
		return new ModelAndView("ManualMove","movedata", vm);
	}
	
	//un-fucking-beleivably the BindingResult parameter must directly follow the @Validated view model parameter or you get an error
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView move(@ModelAttribute("movedata") @Validated ManualMoveViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		if(result.hasErrors())
		{
			addMediaRoots(viewModel, request);
			return new ModelAndView("ManualMove","movedata", viewModel);
		}
		
		
		try
		{
			moveFile(viewModel.getSourceFile(), viewModel.getSelectedRoot(), viewModel.getPathEnd(), request);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:" + viewModel.getReferingPage());
	}
	
	
	private void moveFile(String source, String destinationRoot, String destinationEnd, HttpServletRequest request) throws IOException, DuplicateFileException
	{
		Data data = new Data(request);
		ConfigurationViewModel config = data.getConfiguration();
		
		if(config.isCopyAsDefault())
			fileSys.copyFile(source, destinationRoot, destinationEnd);
		else
			fileSys.moveFile(source, destinationRoot, destinationEnd);
	}
	
	
	private ManualMoveViewModel addMediaRoots(ManualMoveViewModel vm, HttpServletRequest request)
	{
		Data data = new Data(request);
		ConfigurationViewModel config = data.getConfiguration();
		
		vm.getRootPaths().add(config.getMovieRootDirectory());
		vm.getRootPaths().add(config.getTvRootDirectory());
		
		return vm;
	}
	
}





