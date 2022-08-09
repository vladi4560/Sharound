package com.vladi.karasove.sharound.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.adapters.ListOfPlaylistsAdapter;
import com.vladi.karasove.sharound.objects.PlayList;

import java.util.ArrayList;

public class ListOfPlayListFragment extends Fragment  {
    private AppCompatActivity activity;
    private RecyclerView recyclerView;
    private ListOfPlaylistsAdapter adapter;
    private LinearLayoutManager manager;
    private ArrayList<PlayList> playLists;
    public Fragment setActivity(AppCompatActivity activity) {
        this.activity = activity;
        return this;
    }

    public ListOfPlayListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_of_playlist, container, false);
        findViews(view);
        initButtons();


        return view;
    }

    private void initButtons() {

    }



    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.List_OF_PlayLists_RecyclerView_playlists);
        adapter = new ListOfPlaylistsAdapter(this.activity,playLists);
        manager = new LinearLayoutManager(this.activity,LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}

