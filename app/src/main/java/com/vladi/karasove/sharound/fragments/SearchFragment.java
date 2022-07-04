package com.vladi.karasove.sharound.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.vladi.karasove.sharound.CallBacks.CallBack_AdapterToSearch;
import com.vladi.karasove.sharound.CallBacks.CallBack_SearchToPlayNow;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.adapters.SearchAdapter;
import com.vladi.karasove.sharound.models.ModelHome;
import com.vladi.karasove.sharound.models.VideoYT;
import com.vladi.karasove.sharound.network.YoutubeAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private AppCompatActivity activity;
    private TextInputLayout searchView;
    private MaterialButton searchBtn;
    private ArrayList<VideoYT> videoYTS;
    private SearchAdapter adapter;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;

    private CallBack_SearchToPlayNow callBack_searchToPlayNow;


    public void setCallBack_searchToPlayNow(CallBack_SearchToPlayNow callBack_searchToPlayNow) {
        this.callBack_searchToPlayNow = callBack_searchToPlayNow;
    }

    public Fragment setActivity(AppCompatActivity activity, CallBack_SearchToPlayNow setCallBack_searchToPlayNow) {
        this.activity = activity;
        videoYTS = new ArrayList<>();
        setCallBack_searchToPlayNow(setCallBack_searchToPlayNow);
        return this;
    }

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        findViews(view);
        initButtons();
        return view;
    }

    private void initButtons() {
        searchBtn.setOnClickListener(view -> SearchVideos());
    }

    private void SearchVideos() {
        if (!TextUtils.isEmpty(searchView.getEditText().getText().toString())) {
            getJson(searchView.getEditText().getText().toString());
        }
    }

    private void getJson(String query) {
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.SEARCH + YoutubeAPI.PART + YoutubeAPI.KEY + YoutubeAPI.MAXRESULTS
                + YoutubeAPI.FREETEXT + query + YoutubeAPI.TYPE;
        Log.v("pttt", url);
        Call<ModelHome> data = YoutubeAPI.getHomeVideo().getYT(url);
        data.enqueue(new Callback<ModelHome>() {
            @Override
            public void onResponse(Call<ModelHome> call, Response<ModelHome> response) {
                if (response.errorBody() != null) {
                    Log.v("pttt", "on Response search:" + response.errorBody().toString());
                } else {
                    ModelHome mh = response.body();
                    if (mh.getItems().size() != 0) {
                        videoYTS.addAll(mh.getItems());
                        adapter.notifyDataSetChanged();
                        Log.v("pttt", videoYTS.get(0).getId().getVideoId());
                    } else {
                        Toast.makeText(activity, "No Video", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelHome> call, Throwable t) {
                Log.e("pttt", "onFailure search", t);
            }
        });
    }

    private void findViews(View view) {
        searchView = view.findViewById(R.id.SearchFrag_EDT_search);
        searchBtn = view.findViewById(R.id.SearchFrag_BTN_search);
        recyclerView = view.findViewById(R.id.SearchFrag_RCL_ITEMS);
        adapter = new SearchAdapter(this.activity, videoYTS);
        manager = new LinearLayoutManager(this.activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.setCallBack_AdapterToSearch(callBack_adapterToSearch);
    }


    private CallBack_AdapterToSearch callBack_adapterToSearch = new CallBack_AdapterToSearch() {
        @Override
        public void clickedVideo(VideoYT video, int position) {
            callBack_searchToPlayNow.playSong(video);
        }
    };

}
