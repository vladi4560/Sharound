package com.vladi.karasove.sharound.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoID {

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public VideoID(String videoId) {
        this.videoId = videoId;
    }

    public VideoID() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
