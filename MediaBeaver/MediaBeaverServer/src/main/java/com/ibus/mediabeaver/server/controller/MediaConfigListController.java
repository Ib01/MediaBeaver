package com.ibus.mediabeaver.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

@Controller
@RequestMapping(value = "/configList")
@SessionAttributes({"configList"})
public class MediaConfigListController
{
	@ModelAttribute("configList")
	public List<MediaConfigViewModel> getModel(HttpServletRequest request)
	{		
		List<MediaConfig> entities =  Repository.getAllMediaConfig();
	
		ModelMapper modelMapper = Mapper.getMapper();
		List<MediaConfigViewModel> viewModels = modelMapper.map(entities, new TypeToken<List<MediaConfigViewModel>>() {}.getType());
		
		return viewModels;
	}
	
	

	@RequestMapping
	public String viewConfigs(HttpServletRequest request)
	{
		return "MediaConfigList";
	}
}
