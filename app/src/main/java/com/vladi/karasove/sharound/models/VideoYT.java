package com.vladi.karasove.sharound.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoYT {
    @SerializedName("id")
    @Expose
    private VideoID id;
    @SerializedName("snippet")
    @Expose
    private SnippetYT  snippet;

    public VideoID getId() {
        return id;
    }

    public void setId(VideoID id) {
        this.id = id;
    }

    public SnippetYT getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetYT snippet) {
        this.snippet = snippet;
    }

    public VideoYT(VideoID id, SnippetYT snippet) {
        this.id = id;
        this.snippet = snippet;
    }

    public VideoYT() {
    }
}
