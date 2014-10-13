package com.ibus.mediabeaver.server.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigurationViewModel;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController
{
	@RequestMapping
	public ModelAndView viewConfig(HttpServletRequest request)
	{
		Configuration configs = Repository.getFirstEntity(Configuration.class);
		ConfigurationViewModel vm = Mapper.getMapper().map(configs, ConfigurationViewModel.class);
		
		return new ModelAndView("Configuration","configuration", vm);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated ConfigurationViewModel configViewModel)
	{
		Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveOrUpdate(config);
		
		return "redirect:/configuration/";
	}
	
	@RequestMapping(value = "/welcome")
	public ModelAndView welcome()
	{
		return new ModelAndView("Welcome","configuration", new ConfigurationViewModel());
		
	}
	
	@RequestMapping(value = "/initialise", method = RequestMethod.POST)
	public String initialise(@Validated ConfigurationViewModel configViewModel)
	{
		String seperator = java.nio.file.FileSystems.getDefault().getSeparator();
		
		configViewModel.setEpisodePath("{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()"+seperator+"Season {SeasonNumber}"+seperator+"{SeriesName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace() S{SeasonNumber}.leftPad(\"2\",\"0\")E{EpisodeNumber}.leftPad(\"2\",\"0\")");
		configViewModel.setMoviePath("{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))"+seperator+"{MovieName}.replaceAll(\"[/\\?<>\\\\:\\*\\|\\\"\\^]\", \" \").normalizeSpace()({ReleaseDate}.substring(\"0\",\"4\"))");
		configViewModel.setVideoExtensionFilter(".3g2, .3gp, .asf, .avi, .drc, .flv, .flv, .m4v, .mkv, .mng, .mov, .qt, .mp4, .m4p, .m4v, .mpg, .mp2, .mpeg, .mpg, .mpe, .mpv, .mpg, .mpeg, .m2, .mxf, .nsv, .ogv, .ogg, .rm, .rmvb, .roq, .svi, .webm, .wmv");
		configViewModel.setCopyAsDefault(false);
		
		Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveEntity(config);
		
		/*Configuration config = Mapper.getMapper().map(configViewModel, Configuration.class);
		Repository.saveEntity(config);*/
		
		return "redirect:/configuration/";
	}
	
	
	@RequestMapping(value="/validateInitialise", method = RequestMethod.POST)
	public @ResponseBody Object saveRegEx(@Valid @RequestBody ConfigurationViewModel configViewModel) 
	{  
		
		return null;
	}
	
	
	

}
