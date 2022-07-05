package com.vladi.karasove.sharound.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vladi.karasove.sharound.CallBacks.CallBack_PlayListToPlayNow;
import com.vladi.karasove.sharound.CallBacks.CallBack_SearchToPlayNow;
import com.vladi.karasove.sharound.R;
import com.vladi.karasove.sharound.data.MyUserData;
import com.vladi.karasove.sharound.fragments.PlayListFragment;
import com.vladi.karasove.sharound.fragments.ProfileFragment;
import com.vladi.karasove.sharound.fragments.SearchFragment;
import com.vladi.karasove.sharound.models.VideoYT;
import com.vladi.karasove.sharound.objects.Song;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    public static final int SIZE = 4, SEARCH = 0, PLAYLIST = 1, PLAYING = 2, PROFILE = 3;
    private Fragment[] allFragments;
    private SearchFragment searchFragment;
    private ProfileFragment profileFragment;
    private PlayListFragment playListFragment;
    private FragmentManager fragmentManager;
    private MyUserData myUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (MyUserData.getInstance() == null) {
            myUserData = MyUserData.initHelper();
            myUserData.loadUser();
        }
        findViews();
        setFragments();
        initColorMenu();
        initButtons();
    }

    private void initColorMenu() {
        int navDefaultTextColor = Color.parseColor("#FFFFFFFF");
        int navDefaultIconColor = Color.parseColor("#FFFFFFFF");

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        Color.parseColor("#309AFF"),
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );


        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        Color.parseColor("#309AFF"),
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor
                }
        );

        bottomNavigationView.setItemTextColor(navMenuTextList);
        bottomNavigationView.setItemIconTintList(navMenuIconList);
    }




    private void initButtons() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.menu_search:
                        replaceFragments(searchFragment);
                        break;
                    case R.id.menu_library:
                        replaceFragments(playListFragment);
                        break;
                    case R.id.menu_music:
                        openActivity(NowPlayingActivity.class);
                        break;
                    case R.id.menu_profile:
                        replaceFragments(profileFragment);
                        break;
                }
                return true;
            }
        });
    }


    private void replaceFragments(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment, null).commit();

    }


    private void findViews() {
        bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        bottomNavigationView.setBackground(null);

    }

    private void setFragments() {
        allFragments = new Fragment[SIZE];
        searchFragment = new SearchFragment();
        searchFragment.setActivity(this, callBackSearchToPlayNow);
        playListFragment = new PlayListFragment();
        playListFragment.setActivity(this, callBack_playListToPlayNow);
        profileFragment = new ProfileFragment();
        profileFragment.setActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadUserDetails() {
        myUserData.loadUserSongs();
    }


    private void openActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("videoID", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    CallBack_SearchToPlayNow callBackSearchToPlayNow = new CallBack_SearchToPlayNow() {
        @Override
        public void playSong(VideoYT video) {
            openActivity(NowPlayingActivity.class, video.getId().getVideoId(), video.getSnippet().getTitle(), video.getSnippet().getPublishedAt(), 2);
        }
    };

    private void openActivity(Class activity, String videoID, String title, String date, int position) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("videoID", videoID);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        intent.putExtra("POS", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    CallBack_PlayListToPlayNow callBack_playListToPlayNow = new CallBack_PlayListToPlayNow() {
        @Override
        public void playSong(Song song) {
            openActivity(NowPlayingActivity.class, song.getVideoID(), song.getVideoTitle(), song.getVideoDate(), 1);
        }
    };

//    private void openActivity(Class activity, Song song) {
//        Intent intent = new Intent(this, activity);
//        Bundle b = getIntent().getExtras();
//        b.putParcelable("position",song);
//       // intent.putExtra("position", song);
//        intent.putExtra("POS", 1);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
//    }
}