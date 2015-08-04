package io.awts.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class Videos {

    @Expose
    private List<VideoResult> results = new ArrayList<VideoResult>();

    /**
     *
     * @return
     * The results
     */
    public List<VideoResult> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<VideoResult> results) {
        this.results = results;
    }

}