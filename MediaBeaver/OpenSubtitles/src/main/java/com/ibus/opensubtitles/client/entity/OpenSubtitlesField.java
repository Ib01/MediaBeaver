package com.ibus.opensubtitles.client.entity;

public enum OpenSubtitlesField
{
	UserNickName("UserNickName"),
	SubFormat("SubFormat"),
	SeriesIMDBParent("SeriesIMDBParent"),
	IDSubtitle("IDSubtitle"),
	IDMovie("IDMovie"),
	SubBad("SubBad"),
	UserID("UserID"),
	ZipDownloadLink("ZipDownloadLink"),
	SubSize("SubSize"),
	SubFileName("SubFileName"),
	SubDownloadLink("SubDownloadLink"),
	MovieKind("MovieKind"),
	UserRank("UserRank"),
	SubActualCD("SubActualCD"),
	MovieImdbRating("MovieImdbRating"),
	SubAuthorComment("SubAuthorComment"),
	SubRating("SubRating"),
	SeriesSeason("SeriesSeason"),
	SubFeatured("SubFeatured"),
	SubtitlesLink("SubtitlesLink"),
	SubHearingImpaired("SubHearingImpaired"),
	SubHash("SubHash"),
	IDSubMovieFile("IDSubMovieFile"),
	ISO639("ISO639"),
	MovieFPS("MovieFPS"),
	SubDownloadsCnt("SubDownloadsCnt"),
	MovieHash("MovieHash"),
	SubSumCD("SubSumCD"),
	SubComments("SubComments"),
	MovieByteSize("MovieByteSize"),
	LanguageName("LanguageName"),
	MovieYear("MovieYear"),
	SubLanguageID("SubLanguageID"),
	MovieReleaseName("MovieReleaseName"),
	SeriesEpisode("SeriesEpisode"),
	MovieName("MovieName"),
	MovieTimeMS("MovieTimeMS"),
	MatchedBy("MatchedBy"),
	SubHD("SubHD"),
	MovieNameEng("MovieNameEng"),
	SubAddDate("SubAddDate"),
	IDMovieImdb("IDMovieImdb"),
	IDSubtitleFile("IDSubtitleFile");            

	
	private String name; 
	
    private OpenSubtitlesField(String name) { 
        this.name = name; 
    } 
    
    @Override 
    public String toString(){ 
        return name; 
    } 
}
