package info.movito.themoviedbapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import info.movito.themoviedbapi.tools.MovieDbException;
import info.movito.themoviedbapi.tools.MovieDbExceptionType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Utils {

    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb The moviedb object to compare too
     * @param title   The title of the movie to compare
     * @param year    The year of the movie to compare exact match
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year) {
        return compareMovies(moviedb, title, year, 0);
    }


    /**
     * Compare the MovieDB object with a title & year
     *
     * @param moviedb     The moviedb object to compare too
     * @param title       The title of the movie to compare
     * @param year        The year of the movie to compare
     * @param maxDistance The Levenshtein Distance between the two titles. 0 = exact match
     * @return True if there is a match, False otherwise.
     */
    public static boolean compareMovies(MovieDb moviedb, String title, String year, int maxDistance) {
        if ((moviedb == null) || (StringUtils.isBlank(title))) {
            return Boolean.FALSE;
        }

        if (isValidYear(year) && isValidYear(moviedb.getReleaseDate())) {
            // Compare with year
            String movieYear = moviedb.getReleaseDate().substring(0, 4);
            if (movieYear.equals(year)) {
                if (compareDistance(moviedb.getOriginalTitle(), title, maxDistance)) {
                    return Boolean.TRUE;
                }

                if (compareDistance(moviedb.getTitle(), title, maxDistance)) {
                    return Boolean.TRUE;
                }
            }
        }

        // Compare without year
        if (compareDistance(moviedb.getOriginalTitle(), title, maxDistance)) {
            return Boolean.TRUE;
        }

        if (compareDistance(moviedb.getTitle(), title, maxDistance)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


    /**
     * Compare the Levenshtein Distance between the two strings
     *
     * @param title1
     * @param title2
     * @param distance
     */
    private static boolean compareDistance(String title1, String title2, int distance) {
        return (StringUtils.getLevenshteinDistance(title1, title2) <= distance);
    }


    /**
     * Check the year is not blank or UNKNOWN
     *
     * @param year
     */
    private static boolean isValidYear(String year) {
        return (StringUtils.isNotBlank(year) && !year.equals("UNKNOWN"));
    }


    /**
     * Generate the full image URL from the size and image path
     *
     * @param tmdb
     * @param imagePath
     * @param requiredSize
     */
    public static URL createImageUrl(TmdbApi tmdb, String imagePath, String requiredSize) {
        if (StringUtils.isBlank(imagePath)) {
            return null;
        }

        TmdbConfiguration configuration = tmdb.getConfiguration();
        if (!configuration.isValidSize(requiredSize)) {
            throw new MovieDbException(MovieDbExceptionType.INVALID_IMAGE, requiredSize);
        }

        StringBuilder sb = new StringBuilder(configuration.getBaseUrl());
        sb.append(requiredSize);
        sb.append(imagePath);
        try {
            return (new URL(sb.toString()));
        } catch (MalformedURLException ex) {
            LoggerFactory.getLogger(Utils.class).warn("Failed to create image URL: {}", ex.getMessage());
            throw new MovieDbException(MovieDbExceptionType.INVALID_URL, sb.toString(), ex);
        }
    }


    public static <T> List<T> nullAsEmpty(List<T> items) {
        return items == null ? new ArrayList<T>() : items;
    }


    /**
     * Use Jackson to convert Map to json string.
     */
    public static String convertToJson(ObjectMapper jsonMapper, Map<String, ?> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException("json conversion failed", jpe);
        }

    }


    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }


    public static String[] asStringArray(Object[] appendToResponse) {
        if (appendToResponse == null || appendToResponse.length == 0) return null;

        // guava would be more convenient here (just use transformer)
        String[] asArray = new String[appendToResponse.length];
        for (int i = 0; i < appendToResponse.length; i++) {
            asArray[i] = appendToResponse[i].toString();
        }

        return asArray;
    }


    public static Integer parseInteger(String valueOrNull) {
        return valueOrNull == null ? null : Integer.parseInt(valueOrNull);
    }

}
