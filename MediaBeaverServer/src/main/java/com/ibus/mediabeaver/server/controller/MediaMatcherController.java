package com.ibus.mediabeaver.server.controller;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import info.movito.themoviedbapi.model.core.MovieResults;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
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
import com.ibus.mediabeaver.core.entity.ResultType;
import com.ibus.mediabeaver.core.exception.DuplicateFileException;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.filesystem.EpisodePathParser;
import com.ibus.mediabeaver.core.filesystem.FileSystem;
import com.ibus.mediabeaver.core.filesystem.MoviePathParser;
import com.ibus.mediabeaver.core.util.EventLogger;
import com.ibus.mediabeaver.core.util.Factory;
import com.ibus.mediabeaver.core.util.Platform;
import com.ibus.mediabeaver.server.util.Data;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.EpisodeViewModel;
import com.ibus.mediabeaver.server.viewmodel.MatchFilesViewModel;
import com.ibus.mediabeaver.server.viewmodel.MatchFilesViewModel.FileMatchViewModel;
import com.ibus.mediabeaver.server.viewmodel.MoviePartViewModel;
import com.ibus.mediabeaver.server.viewmodel.SearchMoviesViewModel;
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
@RequestMapping(value = "/mediaMatcher")
@SessionAttributes({"SelectMediaType", "SearchSeries", "SelectSeason", "SelectEpisode", "SearchMovies"})
public class MediaMatcherController 
{
	public static final String ReffererSessionKey = "mediaMatcherReffererKey";
	public static final String FilesToMoveSessionKey = "filesToResolve";
	EventLogger eventLogger = new EventLogger(Platform.Web);
	private static TmdbConfiguration tmdbConfiguration;
	
	private static TmdbConfiguration getTmdbConfiguration()
	{
		if(tmdbConfiguration == null)
		{
			tmdbConfiguration = Factory.getTmdbClient().getConfiguration();
		}
		
		return tmdbConfiguration;
	}
	

	//---SelectMdeiaType View ------------------------------------------------------------------------//
	
	@RequestMapping
	public ModelAndView SelectMediaType(HttpServletRequest request)
	{
		SelectMediaViewModel vm = new SelectMediaViewModel();
		if(request.getSession().getAttribute("SelectMediaType") != null)
			vm = (SelectMediaViewModel) request.getSession().getAttribute("SelectMediaType");
		
		vm.setRefferer((String) request.getSession().getAttribute(ReffererSessionKey));
		
		ModelAndView mav = new ModelAndView("SelectMediaType","SelectMediaType", vm);
		mav = addSelectedFiles(mav, request);
		
		return mav;
	}
	
	//---SearchMedia View ------------------------------------------------------------------------//
	
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
		else
		{
			//TODO: Research how best to process multipart movie files!!
			
			/*List<String> srcPaths = (List<String>) request.getSession().getAttribute(FilesToMoveSessionKey);
			if(srcPaths.size() > 1)
			{
				List<MoviePartViewModel> vm = new ArrayList<MoviePartViewModel>();
				for(String p : srcPaths)
				{
					vm.add(new MoviePartViewModel(p));
				}
				
				ModelAndView mav = new ModelAndView("EnterMoviePartNumbers", "EnterMoviePartNumbers", vm);
				addSelectedFiles(mav,request);
				return mav;
			}
			*/
			ModelAndView mav =new ModelAndView("SearchMovies","SearchMovies", new SearchMoviesViewModel()); 
			mav = addSelectedFiles(mav, request);
			return mav; 			
		}
	}

	//---enterMoviePartNumbers View ------------------------------------------------------------------------//
	
	
	
	//---SearchMovies View ------------------------------------------------------------------------//
	
	
	@RequestMapping(value="/SearchMovies_Search", method = RequestMethod.POST)
	public ModelAndView searchMoviesSearch(@ModelAttribute("SearchMovies") @Validated SearchMoviesViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request)
	{
		int movieYear = 0;
		if(viewModel.getMovieYear() != null && viewModel.getMovieYear().trim().length() > 0) //TODO: still need to validate is empty or number on client
			movieYear =Integer.parseInt(viewModel.getMovieYear());
		
		int currentPage = viewModel.getCurrentPage();
		if(viewModel.getCurrentPage() == 0)
			currentPage = 1;
		
		MovieResults results = Factory.getTmdbClient().getSearch().searchMovie(viewModel.getMovieName(), movieYear, "en", true, currentPage);
		
		viewModel.getSearchResults().clear();
		for(MovieDb m : results.getResults())
		{
			//Factory.getTmdbClient().getSearch().searchMovie does not return movie overview, so we need to do a more detailed call.
			MovieDb fullMovie = Factory.getTmdbClient().getMovies().getMovie(m.getId(), null);
			viewModel.getSearchResults().add(fullMovie);
		}
		
		viewModel.setTotalPages(results.getTotalPages());
		viewModel.setCurrentPage(results.getPage());
		
		viewModel.setBaseImageUrl(getTmdbConfiguration().getBaseUrl() + "/w185/");
		return new ModelAndView("SearchMovies","SearchMovies",viewModel);
		
		//http://api.themoviedb.org/3/configuration?api_key=e482b9df13cbf32a25570c09174a1d84
		//https://image.tmdb.org/t/p/w185/5li3ZIIPrjb6bmSFy7wp6J0Z8no.jpg		
		//http://api.themoviedb.org/3/search/movie?query=fury&year=2014&api_key=e482b9df13cbf32a25570c09174a1d84
		//http://api.themoviedb.org/3/movie/228150?api_key=e482b9df13cbf32a25570c09174a1d84
	}
	
/*	@RequestMapping(value="/SearchMovies_ChangePage", method = RequestMethod.POST)
	public ModelAndView SearchMoviesChangePage(@ModelAttribute("SearchMovies") @Validated SearchMoviesViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request)
	{
		int movieYear = 0;
		if(viewModel.getMovieYear() != null && viewModel.getMovieYear().trim().length() > 0) //TODO: still need to validate is empty or number on client
			movieYear =Integer.parseInt(viewModel.getMovieYear());
		
		MovieResults results = Factory.getTmdbClient().getSearch().searchMovie(viewModel.getMovieName(), movieYear, "en", true, viewModel.getCurrentPage());
		
		
		
		
		return new ModelAndView("SearchMovies","SearchMovies",viewModel);
	}*/
	
	@RequestMapping(value="/SearchMovies_Select", method = RequestMethod.POST)
	public ModelAndView SearchMoviesSelect(@ModelAttribute("SearchMovies") @Validated SearchMoviesViewModel viewModel, 
			BindingResult result, 
			HttpServletRequest request) throws Exception
	{
		try 
		{
			Configuration config = new Data(request).getConfiguration(); 
			MoviePathParser pathParser = new MoviePathParser(config.getMovieFormatPath());
			
			String srcPath = ((List<String>) request.getSession().getAttribute(FilesToMoveSessionKey)).get(0);
			MovieDb movie = viewModel.getSelectedMovie();
			String destPathEnd = pathParser.parseMoviePath(movie.getTitle(), movie.getReleaseDate());
			destPathEnd += "." + FilenameUtils.getExtension(srcPath);
			
			if(config.isCopyAsDefault())
				FileSystem.copyFile(srcPath, config.getMovieRootDirectory(), destPathEnd);
			else
				FileSystem.moveFile(srcPath, config.getMovieRootDirectory(), destPathEnd);
			
			eventLogger.logEvent(srcPath, Paths.get(config.getMovieRootDirectory(), destPathEnd).toString(), 
					ResultType.Succeeded, "Successfully moved file");
			
			return new ModelAndView("redirect:/mediaDirectory?type=source");
		
		} catch (IOException | DuplicateFileException | PathParseException e) 
		{
			// TODO Auto-generated catch block
			throw e;
		}
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
	
	@RequestMapping(value="/matchFiles")
	public ModelAndView matchFiles(HttpServletRequest request) 
	{
		MatchFilesViewModel mfvm = (MatchFilesViewModel) request.getSession().getAttribute("MatchFiles");
		return new ModelAndView("MatchFiles","MatchFiles", mfvm); 
	}
	
	
	@RequestMapping(value="/matchFiles_Match")
	public ModelAndView matchFilesMatch(HttpServletRequest request) throws PathParseException, IOException, DuplicateFileException
	{
		MatchFilesViewModel mfvm = (MatchFilesViewModel) request.getSession().getAttribute("MatchFiles");
		for(FileMatchViewModel m : mfvm.getMatches())
		{			
			Configuration config = new Data(request).getConfiguration(); 
			EpisodePathParser parser = new EpisodePathParser(config.getEpisodeFormatPath());
			
			try 
			{
				String pathEnd= parser.parseEpisodePath(
						getSeries(request).getSelectedSeries().getSeriesName(), 
						Integer.toString(getSeason(request).getSelectedSeasonNumber()), 
						m.getEpisode().getEpisodeNumber(),
						m.getEpisode().getEpisodeName());
				
				pathEnd += "." + FilenameUtils.getExtension(m.getFile());
				
				//log.debug(String.format("Destination path generated %s", fullDestinationPath));
				
				if(config.isCopyAsDefault())
					FileSystem.copyFile(m.getFile(), config.getTvRootDirectory(), pathEnd);
				else
					FileSystem.moveFile(m.getFile(), config.getTvRootDirectory(), pathEnd);
			
				String fullDestinationPath = Paths.get(config.getTvRootDirectory(), pathEnd).toString();
				eventLogger.logEvent(m.getFile(), fullDestinationPath, ResultType.Succeeded, "Successfully moved file");
				
				//log.debug(String.format("File %s was successfully moved to %s", file.getAbsolutePath(), fullDestinationPath));
				
				
			} catch (PathParseException | IOException | DuplicateFileException e) 
			{
				//TODO: ?
				throw e;
			}
		
		}
		
		return new ModelAndView("redirect:/mediaDirectory?type=source");
	}
	
	
	
	
	
	//---SelectEpisode  ------------------------------------------------------------------------//

	@RequestMapping(value="/SelectEpisode", method = RequestMethod.POST)
	public ModelAndView SelectEpisode(String selectedFile, HttpServletRequest request) throws TvdbException, TvdbConnectionException
	{
		try 
		{
			long seriesId = getSeries(request).getSelectedSeriesId();
			int seasonNumber = getSeason(request).getSelectedSeasonNumber();
			
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
	
	@RequestMapping(value="/SelectEpisode_Select", method = RequestMethod.POST)
	public ModelAndView selectEpisodeSelect(@ModelAttribute("SelectEpisode") @Validated SelectEpisodeViewModel viewModel, BindingResult result, HttpServletRequest request) 
			throws TvdbException , TvdbConnectionException 
	{
		Episode ep;
		try {
			ep = Factory.getTvdbClient().getEpisode(getSeries(request).getSelectedSeriesId(),  getSeason(request).getSelectedSeasonNumber(), 
					viewModel.getSelectedEpisodeNumber());
		} 
		catch (TvdbException | TvdbConnectionException e) 
		{
			// TODO return msg???
			throw e;
		}
		EpisodeViewModel epvm = Mapper.getMapper().map(ep, EpisodeViewModel.class);
	
		MatchFilesViewModel mfvm = (MatchFilesViewModel) request.getSession().getAttribute("MatchFiles");
		mfvm.setMatchEpisode(viewModel.getSelectedFile(), epvm);
		
		return new ModelAndView("MatchFiles","MatchFiles", mfvm); 
	}
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------//
	
	
	private SearchSeriesViewModel getSeries(HttpServletRequest request)
	{
		SearchSeriesViewModel series = (SearchSeriesViewModel) request.getSession().getAttribute("SearchSeries");
		return series;
	}
	
	private SelectSeasonViewModel getSeason(HttpServletRequest request)
	{
		SelectSeasonViewModel season = (SelectSeasonViewModel) request.getSession().getAttribute("SelectSeason");
		return season;
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
























