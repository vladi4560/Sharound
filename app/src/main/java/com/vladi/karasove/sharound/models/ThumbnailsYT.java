package com.vladi.karasove.sharound.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailsYT {
    @SerializedName("medium")
    @Expose
    private MediumYT medium;

    public MediumYT getMedium() {
        return medium;
    }

    public void setMedium(MediumYT medium) {
        this.medium = medium;
    }

    public ThumbnailsYT() {
    }

    public ThumbnailsYT(MediumYT medium) {
        this.medium = medium;
    }

    public class MediumYT {
        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public MediumYT(String url) {
            this.url = url;
        }

        public MediumYT() {
        }
    }
}
