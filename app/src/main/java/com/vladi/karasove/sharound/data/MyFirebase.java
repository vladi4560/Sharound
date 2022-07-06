package com.vladi.karasove.sharound.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vladi.karasove.sharound.CallBacks.CallBack_LoadUser;
import com.vladi.karasove.sharound.CallBacks.CallBack_loadSongs;
import com.vladi.karasove.sharound.CallBacks.CallBack_UploadImg;
import com.vladi.karasove.sharound.objects.Song;
import com.vladi.karasove.sharound.objects.User;

import java.util.ArrayList;

public class MyFirebase {
    public static final String USERS = "Users",SONGS="Songs",PROFILE="ProfilePic";
    private FirebaseDatabase database;
    private DatabaseReference users,songs;
    private static MyFirebase single_instance;
    private CallBack_loadSongs callBack_loadSongs;
    private CallBack_LoadUser callBack_loadUser;
    private FirebaseStorage myStorage;
    public StorageReference storageReferenceProfile;
    private CallBack_UploadImg callBack_uploadImg;
    public void setCallBack_uploadImg(CallBack_UploadImg callBack_uploadImg) {
        this.callBack_uploadImg = callBack_uploadImg;
    }
    public MyFirebase setCallBack_LoadUser(CallBack_LoadUser callBack_loadUser) {
        this.callBack_loadUser = callBack_loadUser;
        return this;
    }
    public MyFirebase setCallBack_loadSongs(CallBack_loadSongs callBack_loadSongs) {
        this.callBack_loadSongs = callBack_loadSongs;
        return this;
    }
    private MyFirebase() {
        Log.d("pttt","line 39 myfirebase");
        database = FirebaseDatabase.getInstance();
        users = database.getReference(USERS);
        songs = database.getReference(SONGS);
        myStorage= FirebaseStorage.getInstance();
        storageReferenceProfile = myStorage.getReference().child(PROFILE);
    }


    public static MyFirebase getInstance() {
        if (single_instance == null) {
            single_instance = new MyFirebase();
        }
        return single_instance;
    }

    public void createUser(String uid, User user) {
        Log.d("pttt","created user successfully");
        if (user != null && uid != null) {
            users.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("pttt","created user successfully");
                }
            });
        }
    }
    public void saveSong(Song song,String uid){
        if(song!=null){
            Log.d("pttt",songs.getKey()+" "+song.toString());
            songs.child(uid).child(song.getVideoID()).setValue(song).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("pttt","save song successfully");
                    Log.d("pttt","task:" + task.isSuccessful());
                }
            });
        }
    }

    public  void loadUser(String userID) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                   return;
                } else {
                    try {
                        User user = snapshot.getValue(User.class);
                        Log.d("pttt","line 86 firebase"+user.toString()+"  "+ userID);
                        loadUserSongs(userID);
                        if (callBack_loadUser != null) {
                            callBack_loadUser.loadUser(user);
                        }
                    } catch (Exception ex) {
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("tfff", error.getMessage());
            }
        };
        users.child(userID).addListenerForSingleValueEvent(eventListener);
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
                            if (callBack_loadSongs != null) {
                                callBack_loadSongs.loadSongsToUser(item);
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        songs.child(uid).addListenerForSingleValueEvent(eventListener);
    }
    public void uploadImageProfile(Uri resultUri, Activity activity, String uid){
        if (resultUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // adding listeners on upload
            // or failure of image
            storageReferenceProfile.child(uid).putFile(resultUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                // Image uploaded successfully
                                // Dismiss dialog

                                progressDialog.dismiss();
                                Toast
                                        .makeText(activity,
                                                "Image Uploaded!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                storageReferenceProfile.child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        progressDialog.dismiss();
                                        callBack_uploadImg.urlReady(uri.toString(),activity);
                                    }
                                });
                            }
                        }
                    })


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(activity,
                                            "Failed 196 " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


}
