package com.vladi.karasove.sharound.objects;

public class Song {
    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
    }

    private String videoID;
    private String videoTitle;
    private String videoDate;

    public Song(String videoID, String videoTitle, String videoDate) {
        this.videoID = videoID;
        this.videoTitle = videoTitle;
        this.videoDate = videoDate;
    }

    public Song() {
    }
}
