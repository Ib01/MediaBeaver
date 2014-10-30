package com.ibus.mediabeaver.server.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.filesystem.FileSystem;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController
{
	@RequestMapping
	public ModelAndView viewConfig(HttpServletRequest request)
	{
		return new ModelAndView("Configuration","configuration", Data.getConfiguration());
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated ConfigurationViewModel configViewModel)
	{
		Data.saveOrUpdateConfig(configViewModel);
		return "redirect:/configuration/";
	}
	
	@RequestMapping(value = "/welcome")
	public ModelAndView welcome()
	{
		return new ModelAndView("Welcome","configuration", new ConfigurationViewModel());
		
	}
	
	@RequestMapping(value = "/initialise", method = RequestMethod.POST)
	public ModelAndView initialise(@ModelAttribute("configuration") @Validated ConfigurationViewModel configViewModel, BindingResult result)
	{
		String seperator = java.nio.file.FileSystems.getDefault().getSeparator();
		
		if(pathSeperatorsValid(seperator, configViewModel, result) && pathsExist(configViewModel, result))
		{
			Data.saveConfig(addDefaultConfigValues(configViewModel, seperator));
			return new ModelAndView("redirect:/configuration/");
		}
		
		return new ModelAndView("Welcome","configuration", configViewModel);
	}
	
	
	private boolean pathSeperatorsValid(String seperator, ConfigurationViewModel configViewModel, BindingResult result)
	{
		boolean formValid = true;
		
		if((seperator.equals("\\") && configViewModel.getSourceDirectory().contains("/")) ||
				(seperator.equals("/") && configViewModel.getSourceDirectory().contains("\\")))
		{
			result.rejectValue("sourceDirectory", "error.configuration", "The seperators in this path are incorrect for your environment");
			formValid = false;
		}
		
		if((seperator.equals("\\") && configViewModel.getTvRootDirectory().contains("/")) ||
				(seperator.equals("/") && configViewModel.getTvRootDirectory().contains("\\")))
		{
			result.rejectValue("tvRootDirectory", "error.configuration", "The seperators in this path are incorrect for your environment");
			formValid = false;
		}
		
		if((seperator.equals("\\") && configViewModel.getMovieRootDirectory().contains("/")) ||
				(seperator.equals("/") && configViewModel.getMovieRootDirectory().contains("\\")))
		{
			result.rejectValue("movieRootDirectory", "error.configuration", "The seperators in this path are incorrect for your environment");
			formValid = false;
		}
	
		return formValid;
	}
	
	private boolean pathsExist(ConfigurationViewModel configViewModel, BindingResult result)
	{
		String msg = "This directory does not exist";
		FileSystem fs = new FileSystem();
		boolean formValid = true;
		
		if(!fs.pathExists(configViewModel.getSourceDirectory()))
		{
			result.rejectValue("sourceDirectory", "error.configuration", msg);
			formValid =  false;
		}
		if(!fs.pathExists(configViewModel.getTvRootDirectory()))
		{
			result.rejectValue("tvRootDirectory", "error.configuration", msg);
			formValid =  false;
		}
		if(!fs.pathExists(configViewModel.getMovieRootDirectory()))
		{
			result.rejectValue("movieRootDirectory", "error.configuration", msg);
			formValid =  false;
		}
		
		return formValid;
	}
	
	
	private ConfigurationViewModel addDefaultConfigValues(ConfigurationViewModel configViewModel, String seperator)
	{
		configViewModel.setEpisodePath("{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()"+seperator+"Season {SeasonNumber}"+seperator+"{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace() S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		configViewModel.setMoviePath("{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))"+seperator+"{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))");
		configViewModel.setVideoExtensionFilter(".3g2, .3gp, .asf, .avi, .drc, .flv, .flv, .m4v, .mkv, .mng, .mov, .qt, .mp4, .m4p, .m4v, .mpg, .mp2, .mpeg, .mpg, .mpe, .mpv, .mpg, .mpeg, .m2, .mxf, .nsv, .ogv, .ogg, .rm, .rmvb, .roq, .svi, .webm, .wmv");
		configViewModel.setCopyAsDefault(false);
		
		return configViewModel;
	}
	

}
