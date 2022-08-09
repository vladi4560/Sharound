package com.vladi.karasove.sharound.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.data.MyUserData;
import com.vladi.karasove.sharound.objects.Song;

public class NowPlayingActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    private YouTubePlayerView playerView;
    private YouTubePlayer youTubePlayer;
    private MaterialTextView title, date;
    private MaterialButton saveSongBtn;
    private ImageView previousVideo,startVideo,nextVideo;
    private String songID, titleID, dateID;
    private MyUserData myUserData;
    private int fromWhere = 0;
    private boolean isPlay =false;

    @Override
    protected void onPause() {
        super.onPause();
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
                myUserData.getNowPlay().setVideoID( b.getString("videoID"));
                myUserData.getNowPlay().setVideoTitle( b.getString("title"));
                myUserData.getNowPlay().setVideoDate( b.getString("date"));
                saveSongBtn.setVisibility(View.INVISIBLE);
                previousVideo.setVisibility(View.VISIBLE);
                nextVideo.setVisibility(View.VISIBLE);
                startVideo.setImageResource(R.drawable.ic_play);
                songFromPlayList(true);
                break;
            case 2:
                saveSongBtn.setVisibility(View.VISIBLE);
                previousVideo.setVisibility(View.INVISIBLE);
                nextVideo.setVisibility(View.INVISIBLE);
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
                        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                        youTubePlayer.setPlaybackEventListener(playbackEventListener);
                        title.setText(titleID);
                        date.setText(dateID);
                        Log.d("pttt", titleID + " " + dateID);
                        youTubePlayer.loadVideo(songID);
                        youTubePlayer.play();
                        isPlay =true;
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
                   myUserData.setSongIndex( myUserData.getSongs().indexOf(songLoop));
               }
           }
       }

        playerView.initialize("",this);
    }



    private void findViews() {
        playerView = findViewById(R.id.NowPlaying_PLY_youtubePlayer);
        title = findViewById(R.id.NowPlaying_TXT_title);
        date = findViewById(R.id.NowPlaying_TXT_date);
        previousVideo = findViewById(R.id.previousVideo);
        startVideo = findViewById(R.id.startVideo);
        nextVideo = findViewById(R.id.nextVideo);
        previousVideo.setOnClickListener(view->previousVideoClicked());
        startVideo.setOnClickListener(view->startPauseClicked());
        nextVideo.setOnClickListener(view->nextVideoClicked());
        saveSongBtn = findViewById(R.id.NowPlaying_BTN_save);
        saveSongBtn.setOnClickListener(view -> saveWasClicked());
    }

    private void nextVideoClicked() {
        myUserData.setSongIndex(myUserData.getSongIndex()+1);
        myUserData.setNowPlay(myUserData.getSongs().get((myUserData.getSongIndex())));
        startNewSong();
    }

    private void startPauseClicked() {
        if(isPlay){
            startVideo.setImageResource(R.drawable.ic_play);
            youTubePlayer.pause();
        }else {
            startVideo.setImageResource(R.drawable.ic_pause);
            youTubePlayer.play();
        }
    }

    private void previousVideoClicked() {
        myUserData.setSongIndex(myUserData.getSongIndex()-1);
        myUserData.setNowPlay(myUserData.getSongs().get((myUserData.getSongIndex())));
        startNewSong();

    }

    private void saveWasClicked() {
        Song song = new Song(songID, titleID, dateID);
        myUserData.saveSong(song);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.d("pttt","line 199="+myUserData.getNowPlay().toString());
        this.youTubePlayer=youTubePlayer;
        this.youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        this.youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if (!b) {
            Log.d("pttt","line 204="+myUserData.getNowPlay().toString());
            title.setText(myUserData.getNowPlay().getVideoTitle());
            date.setText(myUserData.getNowPlay().getVideoDate());
            this.youTubePlayer.cueVideo(myUserData.getSongs().get(myUserData.getSongIndex()-1).getVideoID());
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("pttt", "Not playing");
    }


    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            startVideo.setImageResource(R.drawable.ic_play);
            isPlay=false;
        }

        @Override
        public void onPlaying() {
            startVideo.setImageResource(R.drawable.ic_pause);
            isPlay=true;
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            if(fromWhere==1){
                Log.d("pttt","line 194");

                if(myUserData.getSongIndex()<myUserData.getSongs().size()){
                    Log.d("pttt","line 196 "+myUserData.getSongIndex());
                    myUserData.setSongIndex(myUserData.getSongIndex()+1);
                }else{
                    myUserData.setSongIndex(1);
                    Log.d("pttt","line 200");

                }
                myUserData.setNowPlay(myUserData.getSongs().get((myUserData.getSongIndex()-1)));

                startNewSong();
            }
        }


        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }

        @Override
        public void onVideoStarted() {
        }
    };

    private void startNewSong() {
        title.setText(myUserData.getNowPlay().getVideoTitle());
        date.setText(myUserData.getNowPlay().getVideoDate());
        Log.d("pttt","line 292 "+myUserData.getSongIndex());
        youTubePlayer.loadVideo(myUserData.getNowPlay().getVideoID());
    }


}
