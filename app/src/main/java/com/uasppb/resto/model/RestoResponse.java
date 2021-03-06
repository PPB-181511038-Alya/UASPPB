package com.uasppb.resto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestoResponse {
    @SerializedName("results_found")
    @Expose
    private Integer resultsFound;
    @SerializedName("results_start")
    @Expose
    private Integer resultsStart;
    @SerializedName("results_shown")
    @Expose
    private Integer resultsShown;
    @SerializedName("restaurants")
    @Expose
    private List<RestoItem_> restaurants = null;

    public Integer getResultsFound() {
        return resultsFound;
    }

    public void setResultsFound(Integer resultsFound) {
        this.resultsFound = resultsFound;
    }

    public Integer getResultsStart() {
        return resultsStart;
    }

    public void setResultsStart(Integer resultsStart) {
        this.resultsStart = resultsStart;
    }

    public Integer getResultsShown() {
        return resultsShown;
    }

    public void setResultsShown(Integer resultsShown) {
        this.resultsShown = resultsShown;
    }

    public List<RestoItem_> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestoItem_> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public String toString() {
        return "RestoResponse{" +
                "resultsFound=" + resultsFound +
                ", resultsStart=" + resultsStart +
                ", resultsShown=" + resultsShown +
                ", restaurants=" + restaurants +
                '}';
    }
}
