package com.vladi.karasove.sharound.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vladi.karasove.sharound.CallBacks.CallBack_loadSongs;
import com.vladi.karasove.sharound.objects.Song;
import com.vladi.karasove.sharound.objects.User;

import java.util.ArrayList;

public class MyFirebase {
    public static final String USERS = "Users",SONGS="Songs";
    private FirebaseDatabase database;
    private DatabaseReference users,songs;
    private static MyFirebase single_instance;
    private CallBack_loadSongs callBack_loadSongs;
    private User user;

    public MyFirebase setCallBack_loadSongs(CallBack_loadSongs callBack_loadSongs) {
        this.callBack_loadSongs = callBack_loadSongs;
        return this;
    }
    private MyFirebase() {
        database = FirebaseDatabase.getInstance();
        users = database.getReference(USERS);
        songs = database.getReference(SONGS);
    }


    public static MyFirebase getInstance() {
        if (single_instance == null) {
            single_instance = new MyFirebase();
        }
        return single_instance;
    }

    public void createUser(String uid, User user) {
        if (user != null && uid != null) {
            users.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("pttt","created user successfully");
                }
            });
        }
    }
    public void saveSong(Song song,String uid){
        if(song!=null){
            songs.child(uid).child(song.getVideoID()).setValue(song).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("pttt","save song successfully");
                }
            });
        }
    }

    public void loadUserSongs(String uid) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Song> songs = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            Song item = child.getValue(Song.class);
                            songs.add(item);
                            Log.i("pttt",item.toString());
                        } catch (Exception ex) {
                        }
                    }
                }
                if (callBack_loadSongs != null) {
                    callBack_loadSongs.loadSongsToUser(songs);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        songs.child(uid).addListenerForSingleValueEvent(eventListener);
    }
}
