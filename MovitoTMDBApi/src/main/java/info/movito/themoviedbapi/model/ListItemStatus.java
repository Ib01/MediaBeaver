package info.movito.themoviedbapi.model;

import info.movito.themoviedbapi.model.core.AbstractJsonMapping;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author Holger Brandl
 */
public class ListItemStatus extends AbstractJsonMapping {


    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("item_present")
    private boolean itemPresent;


    public int getStatusCode() {
        return statusCode;
    }


    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    public boolean isItemPresent() {
        return itemPresent;
    }


    public void setItemPresent(boolean itemPresent) {
        this.itemPresent = itemPresent;
    }
}