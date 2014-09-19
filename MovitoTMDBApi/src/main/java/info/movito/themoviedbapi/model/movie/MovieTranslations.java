package info.movito.themoviedbapi.model.movie;

import info.movito.themoviedbapi.model.Translation;
import info.movito.themoviedbapi.model.core.IdElement;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MovieTranslations extends IdElement {

    @JsonProperty("translations")
    private List<Translation> translations;


    public List<Translation> getTranslations() {
        return translations;
    }
}
