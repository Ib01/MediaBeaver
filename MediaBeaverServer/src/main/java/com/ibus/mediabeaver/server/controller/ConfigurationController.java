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
import com.ibus.mediabeaver.server.viewmodel.ManualMoveViewModel;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController
{
	
	public ConfigurationController()
	{
	}
	
	@RequestMapping
	public ModelAndView viewConfig(HttpServletRequest request)
	{
		Data data = new Data(request);
		return new ModelAndView("Configuration","configuration", data.getConfiguration());
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("configuration") @Validated ConfigurationViewModel configViewModel, BindingResult result, HttpServletRequest request)
	{
		if(!result.hasErrors())
		{
			Data data = new Data(request);
			
			data.mergeConfig(configViewModel);
			return new ModelAndView("redirect:/configuration/");
		}
		
		return new ModelAndView("Configuration","configuration", configViewModel);
	}
	
	@RequestMapping(value = "/welcome")
	public ModelAndView welcome()
	{
		return new ModelAndView("Welcome","configuration", new ConfigurationViewModel());
		
	}
	
	
	@RequestMapping(value = "/initialise", method = RequestMethod.POST)
	public ModelAndView initialise(@ModelAttribute("configuration") @Validated ConfigurationViewModel configViewModel, BindingResult result, HttpServletRequest request)
	{
		if(!result.hasErrors())
		{
			Data data = new Data(request);
			
			data.saveConfig(addDefaultConfigValues(configViewModel));
			return new ModelAndView("redirect:/configuration/");
		}
		
		return new ModelAndView("Welcome","configuration", configViewModel);
	}
	
	
	private ConfigurationViewModel addDefaultConfigValues(ConfigurationViewModel configViewModel)
	{
		String seperator = java.nio.file.FileSystems.getDefault().getSeparator();
		
		configViewModel.setEpisodeFormatPath("{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()"+seperator+"Season {SeasonNumber}"+seperator+"{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace() S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		configViewModel.setMovieFormatPath("{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))"+seperator+"{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))");
		configViewModel.setVideoExtensionFilter(".3g2, .3gp, .asf, .avi, .drc, .flv, .flv, .m4v, .mkv, .mng, .mov, .qt, .mp4, .m4p, .m4v, .mpg, .mp2, .mpeg, .mpg, .mpe, .mpv, .mpg, .mpeg, .m2, .mxf, .nsv, .ogv, .ogg, .rm, .rmvb, .roq, .svi, .webm, .wmv");
		configViewModel.setCopyAsDefault(false);
		
		return configViewModel;
	}

}
