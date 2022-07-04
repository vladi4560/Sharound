package com.vladi.karasove.sharound.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vladi.karasove.sharound.CallBacks.CallBack_AdatperToPlaylist;
import com.vladi.karasove.sharound.CallBacks.CallBack_PlayListToPlayNow;
import com.vladi.karasove.sharound.CallBacks.CallBack_SearchToPlayNow;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.adapters.PlayListAdapter;
import com.vladi.karasove.sharound.data.MyUserData;
import com.vladi.karasove.sharound.objects.Song;

import java.util.ArrayList;

public class PlayListFragment extends Fragment {

    private AppCompatActivity activity;
    private ArrayList<Song> songs;
    private MyUserData myUserData;
    private PlayListAdapter adapter;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private CallBack_PlayListToPlayNow callBack_playListToPlayNow;


    public Fragment setActivity(AppCompatActivity activity,CallBack_PlayListToPlayNow callBack_playListToPlayNow) {
        this.activity = activity;
        setCallBack_PlayListToPlayNow(callBack_playListToPlayNow);
        myUserData= MyUserData.getInstance();
//        if(myUserData.getSongs()!= null)
//            songs=myUserData.getSongs();
//        else
//            songs=new ArrayList<>();
        return this;

    }
    public void setCallBack_PlayListToPlayNow(CallBack_PlayListToPlayNow callBack_playListToPlayNow) {
        this.callBack_playListToPlayNow = callBack_playListToPlayNow;
    }

    public PlayListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_playlist, container, false);
        findViews(view);
        initButtons();
        adapter.notifyDataSetChanged();
        return view;
    }

    private void initButtons() {
    }

    private void findViews(View view) {
        adapter = new PlayListAdapter(this.activity,songs);
        manager = new LinearLayoutManager(this.activity);
        recyclerView = view.findViewById(R.id.MySongsFrag_RecyclerView_songs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.setCallBack_adatperToPlaylist(CallBack_AdatperToPlaylist);
    }

    private CallBack_AdatperToPlaylist CallBack_AdatperToPlaylist = new CallBack_AdatperToPlaylist() {
        @Override
        public void playSong( int position) {
           callBack_playListToPlayNow.playSong(position);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        loadSongs();
    }

    private void loadSongs() {
       songs=myUserData.getSongs();
       Log.d("ptttt",songs.toString());
    }
}
