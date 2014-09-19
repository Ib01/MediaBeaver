package info.movito.themoviedbapi.model;

import info.movito.themoviedbapi.model.core.AbstractJsonMapping;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ReleaseInfo extends AbstractJsonMapping {


    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("certification")
    private String certification;
    @JsonProperty("release_date")
    private String releaseDate;


    public String getCertification() {
        return certification;
    }


    public String getCountry() {
        return country;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

}
