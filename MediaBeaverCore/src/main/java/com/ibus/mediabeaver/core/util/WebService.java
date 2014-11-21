package com.ibus.mediabeaver.core.util;

import info.movito.themoviedbapi.TmdbApi;

import java.util.ArrayList;
import java.util.List;

import com.ibus.mediabeaver.core.entity.Activity;
import com.ibus.mediabeaver.core.entity.Configuration;
import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.filesystem.FileSystem;
import com.ibus.mediabeaver.core.filesystem.MediaMover.Platform;
import com.ibus.opensubtitles.client.OpenSubtitlesClient;
import com.ibus.tvdb.client.TvdbClient;

public abstract class WebService 
{
	public static TvdbClient TVDBClient;
	public static TmdbApi TMDBClient;
	OpenSubtitlesClient OpenSubtitlesClient;
	
	Configuration config;	
	
	Platform platform;
	
	List<PathToken> episodePathTokens;
	List<PathToken> moviePathTokens;
	private static String ostUserName = "";
	private static String ostPassword = "";
	private static String ostUseragent = "FileBot v4.5";
	//private static String ostUseragent = "OS Test User Agent";
	//private static String ostUseragent = "OSTestUserAgent";
	private static String ostHost = "http://api.opensubtitles.org/xml-rpc";
	private static String ostSublanguageid = "eng";
	private static String tmdbApiKey = "e482b9df13cbf32a25570c09174a1d84";
	private List<Activity> unmovedMedia = new ArrayList<Activity>();
	private List<Activity> movedMedia = new ArrayList<Activity>();
}
