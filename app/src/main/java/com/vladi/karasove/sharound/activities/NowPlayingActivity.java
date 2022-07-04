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

public class NowPlayingActivity extends YouTubeBaseActivity implements YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.OnInitializedListener{
    private YouTubePlayerView playerView;
    private MaterialTextView title, date;
    private MaterialButton saveSongBtn;
    private ImageView next, previous, stop;
    private VideoYT videoYT;
    private String songID, titleID, dateID;
    private MyUserData myUserData;
    private int fromWhere = 0;
    private int songIndex = 0;
    private Song nowPlay=new Song();
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pttt", "line 36 on pause");
        finish();
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_now_playing);
        myUserData = MyUserData.getInstance();
        Bundle b = getIntent().getExtras();
        findViews();
        fromWhere = b.getInt("POS");
        switch (fromWhere) {
            case 1:
                nowPlay.setVideoID( b.getString("videoID"));
                nowPlay.setVideoTitle( b.getString("title"));
                nowPlay.setVideoDate( b.getString("date"));
                saveSongBtn.setVisibility(View.INVISIBLE);
                songFromPlayList(true);
                break;
            case 2:
                saveSongBtn.setVisibility(View.VISIBLE);
                songFromSearchView();
                break;
            default:
                defaultVideo();
                break;
        }


    }

    private void defaultVideo() {
        playerView.initialize("key",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.

                        youTubePlayer.loadVideo("RjrA-slMoZ4");
                        Log.d("pttt", "playing");
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("pttt", "Not playing");
                    }
                });

    }

    private void songFromSearchView() {
        Bundle b = getIntent().getExtras();
        songID = b.getString("videoID");
        titleID = b.getString("title");
        dateID = b.getString("date");
        playerView.initialize("key",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        title.setText(titleID);
                        date.setText(dateID);
                        Log.d("pttt", titleID + " " + dateID);
                        youTubePlayer.loadVideo(songID);
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("pttt", "Not playing");
                    }
                });

    }

    private void songFromPlayList(boolean loop) {
       if(loop){
           for (Song songLoop : myUserData.getSongs()) {
               if (songLoop.getVideoID().equals(songID)) {
                   songIndex = myUserData.getSongs().indexOf(songLoop);
               }
           }
       }


        Log.d("pttt","line 130="+songIndex);



        playerView.initialize("Key", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                Log.d("pttt","line 133="+nowPlay.toString());
                    youTubePlayer.setPlayerStateChangeListener(NowPlayingActivity.this);
                    title.setText(nowPlay.getVideoTitle());
                    date.setText(nowPlay.getVideoDate());
                    youTubePlayer.loadVideo(nowPlay.getVideoID());
                    youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("pttt", "Not playing");
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
        Song song = new Song(songID, titleID, dateID);
        myUserData.saveSong(song);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

            if(fromWhere==1){
                Log.d("pttt","line 194");

                if(songIndex<myUserData.getSongs().size()){
                    Log.d("pttt","line 196 "+songIndex);
                    songIndex++;
                }else{
                    songIndex=0;
                    Log.d("pttt","line 200");

                }
                nowPlay=myUserData.getSongs().get(songIndex);
                songFromPlayList(false);

            }
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.d("pttt","line 133="+nowPlay.toString());
        youTubePlayer.setPlayerStateChangeListener(NowPlayingActivity.this);
        title.setText(nowPlay.getVideoTitle());
        date.setText(nowPlay.getVideoDate());
        youTubePlayer.loadVideo(nowPlay.getVideoID());
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("pttt", "Not playing");
    }


 public void startListener(){
     onInitializedListener = new YouTubePlayer.OnInitializedListener() {
         @Override
         public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
             Log.d("pttt","line 133="+nowPlay.toString());
             youTubePlayer.setPlayerStateChangeListener(NowPlayingActivity.this);
             title.setText(nowPlay.getVideoTitle());
             date.setText(nowPlay.getVideoDate());
             youTubePlayer.loadVideo(nowPlay.getVideoID());
             youTubePlayer.play();
         }

         @Override
         public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
             Log.d("pttt", "Not playing");
         }
     };
     playerView.initialize("API KEY",onInitializedListener);
 }

}
