package com.ibus.mediabeaver.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;

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
	
	
	@RequestMapping("sortUp/{index}")
	public ModelAndView configSortUp(@PathVariable String index)
	{
		List<MediaConfigViewModel> models = getModel();
		
		int idx = Integer.parseInt(index);
		if(idx > 0)
		{
			//swap sort orders with item proceeding the item that we are sorting up
			int swapSort =  models.get(idx-1).getSorOrder();
			models.get(idx-1).setSorOrder(models.get(idx).getSorOrder());
			models.get(idx).setSorOrder(swapSort);
			
			//update the db
			MediaConfig mc;
			mc = Mapper.getMapper().map(models.get(idx-1), MediaConfig.class);
			Repository.mergeEntity(mc);
			mc = Mapper.getMapper().map(models.get(idx), MediaConfig.class);
			Repository.mergeEntity(mc);
			
			//resort the collection for display
			Collections.sort(models, new MediaConfigViewModelComparator());
		}
		
		return new ModelAndView("MediaConfigList","configList", models);
	}
	
	
	@RequestMapping("sortDown/{index}")
	public ModelAndView configSortDown(@PathVariable String index)
	{
		List<MediaConfigViewModel> models = getModel();
		
		int idx = Integer.parseInt(index);
		if(idx < models.size() -1)
		{
			//swap sort orders with item proceeding the item that we are sorting up
			int swapSort =  models.get(idx+1).getSorOrder();
			models.get(idx+1).setSorOrder(models.get(idx).getSorOrder());
			models.get(idx).setSorOrder(swapSort);
			
			//update the db
			MediaConfig mc;
			mc = Mapper.getMapper().map(models.get(idx+1), MediaConfig.class);
			Repository.mergeEntity(mc);
			mc = Mapper.getMapper().map(models.get(idx), MediaConfig.class);
			Repository.mergeEntity(mc);
			
			//resort the collection for display
			Collections.sort(models, new MediaConfigViewModelComparator());
		}
		
		return new ModelAndView("MediaConfigList","configList", models);
	}
	
	
	
	
	
	
	/*@RequestMapping("regExSelectorsUpdate/{index}")
	public ModelAndView regExSelectorsUpdate(@PathVariable String index)*/
	
	
	
	public List<MediaConfigViewModel> getModel()
	{		
		List<MediaConfig> entities =  Repository.getAllMediaConfig();
	
		ModelMapper modelMapper = Mapper.getMapper();
		List<MediaConfigViewModel> viewModels = modelMapper.map(entities, new TypeToken<List<MediaConfigViewModel>>() {}.getType());
		
		Collections.sort(viewModels, new MediaConfigViewModelComparator());
		
		return viewModels;
	}
	
	
	private class MediaConfigViewModelComparator implements Comparator<MediaConfigViewModel> 
	{
		public int compare(MediaConfigViewModel selector1, MediaConfigViewModel selector2)
		{
			return Integer.compare(selector1.getSorOrder(), selector2.getSorOrder());
		}
	}
	
	
	
}



































