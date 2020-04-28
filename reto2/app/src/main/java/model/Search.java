package model;

import java.util.ArrayList;

public class Search {
    ArrayList<Playlist> data;


    public Search(ArrayList<Playlist> data, long total, String next) {
        this.data = data;

    }

    public Search() {
    }

    public ArrayList<Playlist> getData()
    {
        return data;
    }

    public void setData(ArrayList<Playlist> data)
    {
        this.data = data;
    }






}
