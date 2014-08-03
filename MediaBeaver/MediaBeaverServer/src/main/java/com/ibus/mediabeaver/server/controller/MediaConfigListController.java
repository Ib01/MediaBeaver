package com.ibus.mediabeaver.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/configList")
public class MediaConfigListController
{
	private @Autowired HttpServletRequest request;

	@RequestMapping
	public ModelAndView viewConfigs(HttpServletRequest request)
	{
		return new ModelAndView("MediaConfigList","configList", getModel());
	}
	
	
	@RequestMapping("/delete/{id}")
	public ModelAndView regExSelectorsDelete(@PathVariable String id)
	{
		MediaConfig c = Repository.getEntity(MediaConfig.class, id);
		Repository.deleteEntity(c);
		
		return new ModelAndView("MediaConfigList","configList", getModel());
	}
	
	
	
	
	public List<MediaConfigViewModel> getModel()
	{		
		List<MediaConfig> entities =  Repository.getAllMediaConfig();
	
		ModelMapper modelMapper = Mapper.getMapper();
		List<MediaConfigViewModel> viewModels = modelMapper.map(entities, new TypeToken<List<MediaConfigViewModel>>() {}.getType());
		
		return viewModels;
	}
	
	
	
	
	
}



































