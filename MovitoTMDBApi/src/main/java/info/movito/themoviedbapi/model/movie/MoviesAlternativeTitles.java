
package info.movito.themoviedbapi.model.movie;

import info.movito.themoviedbapi.model.AlternativeTitle;
import info.movito.themoviedbapi.model.core.AbstractJsonMapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MoviesAlternativeTitles extends AbstractJsonMapping {

    @JsonProperty("titles")
    private List<AlternativeTitle> titles;


    public List<AlternativeTitle> getTitles() {
        return titles;
    }


    public void setTitles(List<AlternativeTitle> titles) {
        this.titles = titles;
    }
}
