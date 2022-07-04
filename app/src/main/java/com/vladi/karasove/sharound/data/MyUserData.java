package com.vladi.karasove.sharound.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.vladi.karasove.sharound.CallBacks.CallBack_loadSongs;
import com.vladi.karasove.sharound.objects.Song;
import com.vladi.karasove.sharound.objects.User;

import java.util.ArrayList;

public class MyUserData {


    private static MyUserData single_instance = null;
    private ArrayList<Song> songs;
    private User myUser;
    private FirebaseAuth myAuth;
    private MyFirebase myFirebase;

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    private MyUserData() {
        myFirebase = MyFirebase.getInstance();
        myAuth = FirebaseAuth.getInstance();
        myUser = new User();
        songs = new ArrayList<>();
        myFirebase.setCallBack_loadSongs(callBack_loadSongs);
    }

    public static MyUserData initHelper() {
        if (single_instance == null) {
            single_instance = new MyUserData();
        }
        return single_instance;
    }

    public static MyUserData getInstance() {
        return single_instance;
    }

    public void setMyUser(String firstName, String lastName, String year, String urlIMG) {
        songs = new ArrayList<>();
        myUser.setUserFirstName(firstName);
        myUser.setUserLastName(lastName);
        myUser.setBirthYear(year);
        myUser.setUserPhoneNumber(myAuth.getCurrentUser().getPhoneNumber());
        //myUser.setUserPic(urlIMG);
        String uid = myAuth.getCurrentUser().getUid();
        myFirebase.createUser(uid, myUser);
    }

    public User getMyUser() {
        return myUser;
    }

    public void saveSong(Song song) {
        if(song == null)
                return;
        if(checkSongExists(song))
                return;
        songs.add(song);
        myFirebase.saveSong(song,myAuth.getUid());
    }

    private boolean checkSongExists(Song songtemp) {
        for (Song song : songs) {
            if(song.getVideoID().equals(song.getVideoID()))
                return true;
        }
        return false;
    }
    public void loadUserSongs(){
        myFirebase.loadUserSongs(myAuth.getCurrentUser().getUid());
    }

    private CallBack_loadSongs callBack_loadSongs= new CallBack_loadSongs() {
        @Override
        public void loadSongsToUser(ArrayList<Song> songs) {
            Log.d("pttt",songs.toString());
            setSongs(songs);
        }
    };
}
