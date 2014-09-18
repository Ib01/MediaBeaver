package info.movito.themoviedbapi.model;

import info.movito.themoviedbapi.model.core.StatusCode;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MovieListCreationStatus extends StatusCode {

    @JsonProperty("list_id")
    private String listId;


    public String getListId() {
        return listId;
    }
}
