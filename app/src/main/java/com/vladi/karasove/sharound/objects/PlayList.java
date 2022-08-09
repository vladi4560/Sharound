package com.vladi.karasove.sharound.objects;

import java.util.ArrayList;

public class PlayList {
    private int ID;
    private ArrayList<Song> listOfSongs;
    private String name;

    public PlayList(String name,ArrayList<Song> listOfSongs) {
        //this.ID = ID;
        this.listOfSongs = listOfSongs;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public int getID() {
        return ID;
    }

    public ArrayList<Song> getListOfSongs() {
        return listOfSongs;
    }


}
