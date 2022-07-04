package com.vladi.karasove.sharound.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.material.textview.MaterialTextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.models.VideoYT;

public class NowPlayingFragment extends Fragment  {
    private AppCompatActivity activity;
    private YouTubePlayerView playerView;
    private MaterialTextView title, date;
    private ImageView next, previous, stop;
    private VideoYT videoYT;

    public Fragment setActivity(AppCompatActivity activity) {
        this.activity = activity;
        return this;
    }

    public NowPlayingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_now_playing, container, false);
        findViews(view);
        initButtons();


        return view;
    }

    private void initButtons() {

    }

    public void setVideoYT(VideoYT videoYT) {
        this.videoYT = videoYT;
        Log.d("pttt", videoYT.toString());
    }

    private void findViews(View view) {
        //playerView = view.findViewById(R.id.NowPlaying_PLY_youtubePlayer);
        title = view.findViewById(R.id.NowPlaying_TXT_title);
        date = view.findViewById(R.id.NowPlaying_TXT_date);

    }


}

