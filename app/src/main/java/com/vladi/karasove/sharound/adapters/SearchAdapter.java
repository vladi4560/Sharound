package com.vladi.karasove.sharound.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vladi.karasove.sharound.CallBacks.CallBack_AdapterToSearch;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.models.VideoYT;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<VideoYT> videoYTS;
    private CallBack_AdapterToSearch songlistener;


    public void setCallBack_AdapterToSearch(CallBack_AdapterToSearch songlistener) {
        this.songlistener = songlistener;
    }
    public SearchAdapter(Context context, ArrayList<VideoYT> videoYTS) {
        this.context = context;
        this.videoYTS = videoYTS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoYT videoYT = videoYTS.get(position);
        MyViewHolder mvh = (MyViewHolder) holder;
        mvh.setData(videoYT);
    }



    @Override
    public int getItemCount() {
        return videoYTS.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView title, date;

        public MyViewHolder(@NonNull View view) {
            super(view);
            thumbnail = view.findViewById(R.id.songView_Pic);
            title = view.findViewById(R.id.songView_title);
            date = view.findViewById(R.id.songView_date);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (songlistener != null) {
                        songlistener.clickedVideo(videoYTS.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }


        public void setData(VideoYT videoYT) {
            String temptitle = videoYT.getSnippet().getTitle();
            String tempdate = videoYT.getSnippet().getPublishedAt();
            String tempurl = videoYT.getSnippet().getThumbnails().getMedium().getUrl();

            this.title.setText(temptitle);
            this.date.setText(tempdate);
            Picasso.get()
                    .load(tempurl)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(this.thumbnail, new
                            Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("pttt", "successfully Thumbnail ");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.d("pttt", "error Thumbnail ", e);
                                }
                            });

        }
    }
}
