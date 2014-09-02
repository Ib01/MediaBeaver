package info.movito.themoviedbapi;

import info.movito.themoviedbapi.tools.MovieDbException;
import info.movito.themoviedbapi.tools.MovieDbExceptionType;


public class TmdbChanges extends AbstractApiElement {

    TmdbChanges(TmdbApi tmdbApi) {
        super(tmdbApi);
    }


    public void getMovieChangesList(int page, String startDate, String endDate) {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }


    public void getPersonChangesList(int page, String startDate, String endDate) {
        throw new MovieDbException(MovieDbExceptionType.UNKNOWN_CAUSE, "Not implemented yet");
    }
}
