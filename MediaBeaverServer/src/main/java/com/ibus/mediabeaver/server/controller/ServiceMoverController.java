package com.ibus.mediabeaver.server.controller;

import java.io.IOException;
import java.net.URISyntaxException;
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

import com.ibus.mediabeaver.core.util.Services;
import com.ibus.mediabeaver.server.viewmodel.FileViewModel;
import com.ibus.mediabeaver.server.viewmodel.SearchSeriesViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectMediaViewModel;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.domain.wrapper.EpisodesXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesListXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesXmlWrapper;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

@Controller
@RequestMapping(value = "/serviceMover")
@SessionAttributes({"SelectMediaType", "SearchSeries"})
public class ServiceMoverController 
{
	public static final String FilesToMoveSessionKey = "filesToResolve";

	@RequestMapping
	public ModelAndView serviceMove(HttpServletRequest request) throws Exception
	{
		SelectMediaViewModel vm = getSelectMediaViewModel(request);
			
		ModelAndView mav = new ModelAndView("SelectMediaType","SelectMediaType", vm);
		mav = addSelectedFiles(mav, request);
		
		return mav;
	}
	
	@RequestMapping(value="/selectMedia", method = RequestMethod.POST)
	public ModelAndView selectMedia(@ModelAttribute("SelectMediaType") @Validated SelectMediaViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		if(viewModel.getSelectedMediaType().equals(SelectMediaViewModel.SelectedMediaType.Tv.toString()))
		{
			SearchSeriesViewModel vm = getSearchSeriesViewModel(request);
			ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", vm); 
			
			mav = addSelectedFiles(mav, request);
			return mav; 			
		}
		
		return null;
	}
	
	@RequestMapping(value="/searchSeries", method = RequestMethod.POST)
	public ModelAndView searchSeries(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		/*TODO:
		 * 
		 * PROCESS.
		 * 
		 * call http://www.thetvdb.com/api/FA86CE5B6769E616/series/{series Id}/banners.xml to get all banners for series
		 * 
		 * search for the right banner according to http://thetvdb.com/wiki/index.php?title=API:banners.xml
		 * 
		 * append BannerPath returned in banners.xml to <mirrorpath>/banners/ to get url of
		 * 
		 * 
		 * */
		
		
		try 
		{
			List<Series> tvDto = Services.getTvdbClient().getSeries(viewModel.getSearchText());
			viewModel.setSearchResults(tvDto);
			
			ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", viewModel); 
			
			return mav;
			
			//long tvdbSeriesId = tvDto.getSeries().getId();
			
			//TvdbEpisodesResponseDto contains detailed info about not only the series but all its seasons and episodes. pity there isn't a way to 
			//get single episode info for an episode imdb.
			/*TvdbEpisodesResponseDto tvdbEpisodes = client.getEpisodes(Long.toString(tvdbSeriesId));
			
			TvdbSeriesResponseDto g = tvDto;*/
		} 
		catch (TvdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TvdbConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	@RequestMapping(value="/selectSeries", method = RequestMethod.POST)
	public ModelAndView selectSeries(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		
		
		return null;
		
	}
	
	
	
	
	
	private SearchSeriesViewModel getSearchSeriesViewModel(HttpServletRequest request)
	{
		//we will have something loaded in session if we are coming from a previous button
		SearchSeriesViewModel vm = new SearchSeriesViewModel();
		if(request.getSession().getAttribute("SearchSeries") != null)
			vm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
		
		return vm;
	}
	
	
	private SelectMediaViewModel getSelectMediaViewModel(HttpServletRequest request)
	{
		//we will have something loaded in session if we are coming from a previous button
		SelectMediaViewModel vm = new SelectMediaViewModel();
		if(request.getSession().getAttribute("SelectMediaType") != null)
			vm = (SelectMediaViewModel) request.getSession().getAttribute("SelectMediaType");
		
		return vm;
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView addSelectedFiles(ModelAndView mav, HttpServletRequest request)
	{
		List<String> paths = (List<String>) request.getSession().getAttribute(FilesToMoveSessionKey);
		mav.addObject(FilesToMoveSessionKey, paths);
		return mav;
	}
}
























