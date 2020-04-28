package model;

import java.util.ArrayList;

public class TracksReciever {

    ArrayList<Track> data;

    public TracksReciever(ArrayList<Track> data) {
        this.data = data;
    }
    public TracksReciever(){

    }

    public ArrayList<Track> getData() {
        return data;
    }

    public void setData(ArrayList<Track> data) {
        this.data = data;
    }
}
