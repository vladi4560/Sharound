package com.vladi.karasove.sharound.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.objects.PlayList;

import java.util.ArrayList;

public class ListOfPlaylistsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<PlayList> playLists;

    public ListOfPlaylistsAdapter(Context context, ArrayList<PlayList> playLists) {
        this.context = context;
        this.playLists = playLists;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        MyViewHolder mvh = (MyViewHolder) holder;
        mvh.setData(playList);
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView title;
        private MaterialCardView cardView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title= view.findViewById(R.id.playList_LBL_title);
            cardView= view.findViewById(R.id.playList_LNL_song);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void setData(PlayList playList) {
            title.setText(playList.getName());
        }
    }
}
