package com.vladi.karasove.sharound.network;

import com.vladi.karasove.sharound.models.ModelHome;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {
    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String KEY = "API KEY";
    public static final String SEARCH = "search?";
    public static final String FREETEXT="&q=";
    public static final String TYPE="&type=video";
    public static final String PART = "part=snippet";
    public static final String MAXRESULTS = "&maxResults=15";
    public static final String ORDER = "&order=date";

    public interface HomeVideo {
        @GET
        Call<ModelHome> getYT(@Url String url);
    }

    private static HomeVideo homeVideo = null;

    public static HomeVideo getHomeVideo() {
        if (homeVideo == null) {
            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            homeVideo = retrofit.create(HomeVideo.class);
        }
        return homeVideo;
    }
}
