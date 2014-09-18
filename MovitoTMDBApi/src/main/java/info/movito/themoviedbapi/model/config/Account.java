package info.movito.themoviedbapi.model.config;

import info.movito.themoviedbapi.model.core.NamedIdElement;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Account extends NamedIdElement {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("include_adult")
    private boolean includeAdult;


    public boolean isIncludeAdult() {
        return includeAdult;
    }


    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
}
