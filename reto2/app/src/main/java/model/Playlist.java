package model;

import java.util.ArrayList;

public class Playlist {

    long id;
    String title;
    String description;
    String picture;
    TracksReciever tracks;
    String creation_date;
    User user;
    int nb_tracks;
    int fans;

    public Playlist(long id, String title, String description, String picture,  TracksReciever tracks, String creation_date, User user, int nb_tracks,int fans) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.tracks = tracks;
        this.creation_date = creation_date;
        this.user = user;
        this.nb_tracks = nb_tracks;
        this.fans=fans;
    }

    public Playlist(){

    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public TracksReciever getTracks() {
        return tracks;
    }

    public void setTracks(TracksReciever tracks) {
        this.tracks = tracks;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNb_tracks() {
        return nb_tracks;
    }

    public void setNb_tracks(int nb_tracks) {
        this.nb_tracks = nb_tracks;
    }
}
