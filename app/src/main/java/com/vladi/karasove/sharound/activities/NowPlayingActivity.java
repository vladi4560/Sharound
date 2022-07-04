package com.vladi.karasove.sharound.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.data.MyUserData;
import com.vladi.karasove.sharound.models.VideoYT;
import com.vladi.karasove.sharound.objects.Song;

import java.util.Iterator;

public class NowPlayingActivity extends YouTubeBaseActivity {
    private YouTubePlayerView playerView;
    private MaterialTextView title, date;
    private MaterialButton saveSongBtn;
    private ImageView next, previous, stop;
    private VideoYT videoYT;
    private String songID, titleID, dateID;
    private MyUserData myUserData;
    private int fromWhere=0;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_now_playing);
        findViews();
        myUserData = MyUserData.getInstance();
        Bundle b = getIntent().getExtras();
        fromWhere= b.getInt("POS");
        switch (fromWhere) {
            case 1:
                songFromPlayList(b.getInt("position"));
                break;
            case 2:
                songFromSearchView();
                break;
            default:
                defaultVideo();
                break;
        }




    }

    private void defaultVideo() {
        playerView.initialize("API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.

                        youTubePlayer.loadVideo("RjrA-slMoZ4");
                        Log.i("pttt", "playing");
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.i("pttt", "Not playing");
                    }
                });

    }

    private void songFromSearchView() {
        Bundle b = getIntent().getExtras();
        songID = b.getString("videoID");
        titleID = b.getString("title");
        dateID = b.getString("date");
        playerView.initialize("AIzaSyBwIBqWbB1a78MkIX2kxkOtv7ZjPlIBZmU",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                            title.setText(titleID);
                            date.setText(dateID);
                            Log.i("pttt", titleID + " " + dateID);
                            youTubePlayer.loadVideo(songID);
                            youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.i("pttt", "Not playing");
                    }
                });

    }

    private void songFromPlayList(int position) {
        playerView.initialize("API KEY", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                while (position != myUserData.getSongs().size()){
                    youTubePlayer.loadVideo(myUserData.getSongs().get(position).getVideoID());
                    youTubePlayer.play();
                    ;
                }


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("pttt", "Not playing");
            }
        });
    }


    private void findViews() {
        playerView = findViewById(R.id.NowPlaying_PLY_youtubePlayer);
        title = findViewById(R.id.NowPlaying_TXT_title);
        date = findViewById(R.id.NowPlaying_TXT_date);
        saveSongBtn = findViewById(R.id.NowPlaying_BTN_save);
        saveSongBtn.setOnClickListener(view -> saveWasClicked());
    }

    private void saveWasClicked() {
        myUserData.saveSong(new Song(songID, titleID, dateID));
    }

}
