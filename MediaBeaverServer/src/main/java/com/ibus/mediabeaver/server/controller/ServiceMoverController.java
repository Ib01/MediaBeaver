package com.ibus.mediabeaver.server.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.EpisodeViewModel;
import com.ibus.mediabeaver.server.viewmodel.MatchFilesViewModel;
import com.ibus.mediabeaver.server.viewmodel.SearchSeriesViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectEpisodeViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectMediaViewModel;
import com.ibus.mediabeaver.server.viewmodel.SelectSeasonViewModel;
import com.ibus.tvdb.client.domain.Banner;
import com.ibus.tvdb.client.domain.Episode;
import com.ibus.tvdb.client.domain.Series;
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
	public ModelAndView SelectMediaType(HttpServletRequest request)
	{
		SelectMediaViewModel vm = new SelectMediaViewModel();
		if(request.getSession().getAttribute("SelectMediaType") != null)
			vm = (SelectMediaViewModel) request.getSession().getAttribute("SelectMediaType");
		
		ModelAndView mav = new ModelAndView("SelectMediaType","SelectMediaType", vm);
		mav = addSelectedFiles(mav, request);
		
		return mav;
	}
	
	//---SearchTvSeries View ------------------------------------------------------------------------//
	
	@RequestMapping(value="/searchTvSeries")
	public ModelAndView searchTvSeries(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("SearchSeries") == null)
			return new ModelAndView("redirect:/configuration");
			
		SearchSeriesViewModel vm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
		
		ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", vm); 
		mav = addSelectedFiles(mav, request);
		return mav; 			
	}
	
	@RequestMapping(value="/searchMedia", method = RequestMethod.POST)
	public ModelAndView searchMedia(@ModelAttribute("SelectMediaType") @Validated SelectMediaViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request)
	{
		if(viewModel.getSelectedMediaType().equals(SelectMediaViewModel.SelectedMediaType.Tv.toString()))
		{
			ModelAndView mav =new ModelAndView("SearchTvSeries","SearchSeries", new SearchSeriesViewModel()); 
			mav = addSelectedFiles(mav, request);
			return mav; 			
		}
		
		//TODO: redirect to movie selection
		return null;
	}
		
	@RequestMapping(value="/searchTvSeries_Search", method = RequestMethod.POST)
	public ModelAndView searchTvSeriesSearch(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request)
	{
		try 
		{
			List<Series> series = Factory.getTvdbClient().getSeries(viewModel.getSearchText());
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
	
	//---SelectSeason View ------------------------------------------------------------------------//
	
	@RequestMapping(value="/selectSeason")
	public ModelAndView selectSeason(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("SelectSeason") == null)
			return new ModelAndView("redirect:/configuration");
			
		SelectSeasonViewModel vm = (SelectSeasonViewModel) request.getSession().getAttribute("SelectSeason");
		
		ModelAndView mav =new ModelAndView("SelectSeason","SelectSeason", vm); 
		mav = addSelectedFiles(mav, request);
		return mav; 	
	}
	
	@RequestMapping(value="/selectSeason", method = RequestMethod.POST)
	public ModelAndView selectSeason(@ModelAttribute("SearchSeries") @Validated SearchSeriesViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request)
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
	
	//---MatchEpisode  ------------------------------------------------------------------------//
	
	@RequestMapping(value="/matchFiles", method = RequestMethod.POST)
	public ModelAndView matchFiles(@ModelAttribute("SelectSeason") @Validated SelectSeasonViewModel viewModel, BindingResult result, HttpServletRequest request) 
			throws TvdbException, TvdbConnectionException
	{
		MatchFilesViewModel epVm = new  MatchFilesViewModel();
		
		@SuppressWarnings("unchecked")
		List<String> paths = (List<String>) request.getSession().getAttribute(FilesToMoveSessionKey);
		for(String p : paths)
		{
			epVm.addMatch(p, null);
		}

		request.getSession().setAttribute("MatchFiles", epVm); //easier to manage this ourselves
		
		ModelAndView mav =new ModelAndView("MatchFiles","MatchFiles", epVm); 
		addSelectedFiles(mav, request);
		
		return mav;
	}
	
	
	//---SelectEpisode  ------------------------------------------------------------------------//

	
	@RequestMapping(value="/selectEpisode", method = RequestMethod.POST)
	public ModelAndView selectEpisode(String selectedFile, HttpServletRequest request) throws TvdbException, TvdbConnectionException
	{
		try 
		{
			long seriesId = getSeriesId(request);
			int seasonNumber = getSeasonNumber(request);
			
			List<Episode> episodes = Factory.getTvdbClient().getEpisodes(seriesId, seasonNumber);
			
			Type targetListType = new TypeToken<List<EpisodeViewModel>>() {}.getType();
			List<EpisodeViewModel> eps = Mapper.getMapper().map(episodes, targetListType);
			
			SelectEpisodeViewModel sepVm = new  SelectEpisodeViewModel();
			sepVm.setEpisodes(eps);
			sepVm.setSelectedFile(selectedFile);
			
			ModelAndView mav =new ModelAndView("SelectEpisode","SelectEpisode", sepVm); 
			addSelectedFiles(mav, request);
			return mav;	
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			//TODO: connection problem?
			throw e;
		}
		
	}
	
	@RequestMapping(value="/doSelectEpisode", method = RequestMethod.POST)
	public ModelAndView selectEpisode(@ModelAttribute("SelectEpisode") @Validated SelectEpisodeViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		MatchFilesViewModel mfvm = (MatchFilesViewModel) request.getSession().getAttribute("MatchFiles");
		
		Episode ep = Factory.getTvdbClient().getEpisode(getSeriesId(request),  getSeasonNumber(request), viewModel.getSelectedEpisodeId());
		EpisodeViewModel epvm = Mapper.getMapper().map(ep, EpisodeViewModel.class);
	
		mfvm.setFileEpisode(viewModel.getSelectedFile(), epvm);
		
		
	}
	
	
	//MatchFilesViewModel seriesVm = (MatchFilesViewModel) request.getSession().getAttribute("MatchFiles");	
	
	/*@RequestMapping(value="/selectEpisode", method = RequestMethod.POST)
	public ModelAndView selectEpisode(@ModelAttribute("SelectSeason") @Validated SelectSeasonViewModel viewModel, BindingResult result, HttpServletRequest request)
	{
		SelectEpisodeViewModel epVm = new  SelectEpisodeViewModel();
		try 
		{
			SearchSeriesViewModel seriesVm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
			long seriesId = seriesVm.getSelectedSeriesId();
			List<Episode> episodes = Factory.getTvdbClient().getEpisodes(seriesId, viewModel.getSelectedSeasonNumber());
			
			
			Type targetListType = new TypeToken<List<EpisodeViewModel>>() {}.getType();
			List<EpisodeViewModel> eps = Mapper.getMapper().map(episodes, targetListType);
			
			epVm.setEpisodes(eps);
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			TODO: connection problem?
		}
		
		ModelAndView mav =new ModelAndView("SelectEpisode","SelectEpisode", epVm); 
		addSelectedFiles(mav, request);
		return mav;
	}*/
	
	
	
	
	//----------------------------------------------------------------------------------------//
	
	
	private Long getSeriesId(HttpServletRequest request)
	{
		SearchSeriesViewModel seriesVm = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
		long seriesId = seriesVm.getSelectedSeriesId();
		return seriesId;
	}
	
	private int getSeasonNumber(HttpServletRequest request)
	{
		SelectSeasonViewModel seasonVm = (SelectSeasonViewModel) request.getSession().getAttribute("SelectSeason");
		int seasonNumber = seasonVm.getSelectedSeasonNumber();
		return seasonNumber;
	}
	
	
	private SelectSeasonViewModel getSelectSeasonViewModel(HttpServletRequest request, Long seriesId) throws TvdbException, TvdbConnectionException
	{
		SelectSeasonViewModel vm =  new SelectSeasonViewModel();
		
		int seasonCount = Factory.getTvdbClient().getSeasonCount(seriesId);
		vm.setSeasonsCount(seasonCount);
		
		for(int i=1; i <= seasonCount; i++)
		{
			Banner banner = Factory.getTvdbClient().getTopSeasonBanner(seriesId, i);
			
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
























