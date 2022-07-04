package com.vladi.karasove.sharound.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelHome {
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("items")
    @Expose
    private ArrayList<VideoYT> items;

    public ModelHome() {
    }

    public ModelHome(String nextPageToken, ArrayList<VideoYT> items) {
        this.nextPageToken = nextPageToken;
        this.items = items;
    }
    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<VideoYT> getItems() {
        return items;
    }

    public void setItems(ArrayList<VideoYT> items) {
        this.items = items;
    }


}
