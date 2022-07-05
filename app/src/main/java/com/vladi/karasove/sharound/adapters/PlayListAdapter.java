package com.vladi.karasove.sharound.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.vladi.karasove.sharound.CallBacks.CallBack_AdapterToSearch;
import com.vladi.karasove.sharound.CallBacks.CallBack_AdatperToPlaylist;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.models.VideoYT;
import com.vladi.karasove.sharound.objects.Song;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Song> songs;
    private CallBack_AdatperToPlaylist callBack_adatperToPlaylist;

    public void setCallBack_adapterToPlaylist(CallBack_AdatperToPlaylist callback){
        this.callBack_adatperToPlaylist=callback;
    }

    public PlayListAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Song tempSong = songs.get(position);
        MyViewHolder mvh = (MyViewHolder) holder;
        mvh.setData(tempSong);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView title,date;
        private MaterialCardView cardView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title= view.findViewById(R.id.songList_LBL_title);
            date= view.findViewById(R.id.songList_LBL_date);
            cardView= view.findViewById(R.id.songList_LNL_song);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack_adatperToPlaylist.playSong(songs.get(getAdapterPosition()));
                }
            });
        }

        public void setData(Song song) {
            title.setText(song.getVideoTitle());
            date.setText(song.getVideoDate());
        }
    }
}
