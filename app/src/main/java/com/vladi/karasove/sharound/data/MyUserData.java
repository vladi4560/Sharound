package com.vladi.karasove.sharound.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.vladi.karasove.sharound.CallBacks.CallBack_LoadUser;
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
        myFirebase.setCallBack_LoadUser(callBack_loadUser);
    }

    public static MyUserData initHelper() {
        if (single_instance == null) {
            single_instance = new MyUserData();
        }
        return single_instance;
    }
    public void loadUser(){
        myFirebase.loadUser(myAuth.getUid());
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

   public void loadSong(Song song){
        songs.add(song);
   }
    private CallBack_loadSongs callBack_loadSongs= new CallBack_loadSongs() {
        @Override
        public void loadSongsToUser(Song song) {
            Log.d("pttt",songs.toString());
            loadSong(song);
        }
    };
    private CallBack_LoadUser callBack_loadUser= new CallBack_LoadUser() {
        @Override
        public void loadUser(User user) {
            myUser.setUserFirstName(user.getUserFirstName());
            myUser.setUserLastName(user.getUserLastName());
            myUser.setBirthYear(user.getUserBirthYear());
            myUser.setUserPhoneNumber(myAuth.getCurrentUser().getPhoneNumber());
        }
    };
}
