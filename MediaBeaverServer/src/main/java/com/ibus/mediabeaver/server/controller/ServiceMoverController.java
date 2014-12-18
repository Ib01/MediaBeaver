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
import com.ibus.mediabeaver.server.viewmodel.SelectEpisodeViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectMediaViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectSeasonViewModel;
import com.ibus.tvdb.client.TvdbClient;
import com.ibus.tvdb.client.domain.Banner;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
import com.ibus.tvdb.client.domain.wrapper.EpisodesXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesListXmlWrapper;
import com.ibus.tvdb.client.domain.wrapper.SeriesXmlWrapper;
import com.ibus.tvdb.client.exception.TvdbConnectionException;
import com.ibus.tvdb.client.exception.TvdbException;

@Controller
@RequestMapping(value = "/serviceMover")
@SessionAttributes({"SelectMediaType", "SearchSeries", "SelectSeason", "SelectEpisode"})
public class ServiceMoverController 
{
	public static final String FilesToMoveSessionKey = "filesToResolve";

	//---SelectMdeiaType View ------------------------------------------------------------------------//
	@RequestMapping
	public ModelAndView serviceMove(HttpServletRequest request)
	{
		SelectMediaViewModel vm = new SelectMediaViewModel();
		if(request.getSession().getAttribute("SelectMediaType") != null)
			vm = (SelectMediaViewModel) request.getSession().getAttribute("SelectMediaType");
		
		ModelAndView mav = new ModelAndView("SelectMediaType","SelectMediaType", vm);
		mav = addSelectedFiles(mav, request);
		
		return mav;
	}
	
	@RequestMapping(value="/serviceMover_Next", method = RequestMethod.POST)
	public ModelAndView selectMedia(@ModelAttribute("SelectMediaType") @Validated SelectMediaViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		if(viewModel.getSelectedMediaType().equals(SelectMediaViewModel.SelectedMediaType.Tv.toString()))
		{
			SearchSeriesViewModel vm = new SearchSeriesViewModel();
			//SearchSeriesViewModel vm = getSearchSeriesViewModel(request);
			ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", vm); 
			
			mav = addSelectedFiles(mav, request);
			return mav; 			
		}
		
		return null;
	}
	
	//---SearchTvSeries View ------------------------------------------------------------------------//
	
	
	@RequestMapping(value="/searchTvSeries_Search", method = RequestMethod.POST)
	public ModelAndView searchSeries(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		try 
		{
			List<Series> series = Services.getTvdbClient().getSeries(viewModel.getSearchText());
			viewModel.setSearchResults(series);
			ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", viewModel);
			mav = addSelectedFiles(mav, request);
			return mav;
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			/*TODO: connection problem?*/
		}
		
		return null;
	}
	
	@RequestMapping(value="/searchTvSeries_Next", method = RequestMethod.POST)
	public ModelAndView selectSeries(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		SelectSeasonViewModel vm =  new SelectSeasonViewModel();
		try 
		{
			Long seriesId = viewModel.getSelectedSeriesId();
			vm = getSelectSeasonViewModel(request, seriesId);
		} 
		catch (TvdbException | TvdbConnectionException e1) {
			/*TODO: connection problem?*/
		}
		
		ModelAndView mav =new ModelAndView("SelectSeason","SelectSeason", vm); 
		addSelectedFiles(mav, request);
		
		return mav;
	}
	
	//---SelectSeason View ------------------------------------------------------------------------//
	
	
	@RequestMapping(value="/selectSeason_Back")
	public ModelAndView backToServiceMover(HttpServletRequest request)
	{
		SearchSeriesViewModel vm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
		return new ModelAndView("SearchTvSeries","SearchSeries", vm);
	}
	
	
	@RequestMapping(value="/selectSeason_Next", method = RequestMethod.POST)
	public ModelAndView selectSeasonNext(@ModelAttribute("SelectSeason") @Validated SelectSeasonViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		SelectEpisodeViewModel epVm = new  SelectEpisodeViewModel();
		try 
		{
			SearchSeriesViewModel seriesVm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
			long seriesId = seriesVm.getSelectedSeriesId();
			List<Episode> episodes = Services.getTvdbClient().getEpisodes(seriesId, viewModel.getSelectedSeasonNumber());
			epVm.setEpisodes(episodes);
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			/*TODO: connection problem?*/
		}
		
		ModelAndView mav =new ModelAndView("SelectEpisode","SelectEpisode", epVm); 
		addSelectedFiles(mav, request);
		return mav;
	}
	
	
	//---SelectEpisode  ------------------------------------------------------------------------//
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------//
	
	private SelectSeasonViewModel getSelectSeasonViewModel(HttpServletRequest request, Long seriesId) throws TvdbException, TvdbConnectionException
	{
		SelectSeasonViewModel vm =  new SelectSeasonViewModel();
		
		int seasonCount = Services.getTvdbClient().getSeasonCount(seriesId);
		vm.setSeasonsCount(seasonCount);
		
		for(int i=1; i <= seasonCount; i++)
		{
			Banner banner = Services.getTvdbClient().getTopSeasonBanner(seriesId, i);
			
			// 
			if(banner != null && banner.getBannerPath() != null &&  banner.getBannerPath().trim().length() != 0)
			{
				vm.getBanners().add(banner);	
			}
			else
			{
				vm.getBanners().add(new Banner()); // a hack to display placeholder banner image
			}
		}	
		
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
























