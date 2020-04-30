package model;

import android.util.Log;

public class Track {

    long id;
    String title;
    String release_date;
    Artist artist;
    Album album;
    long duration;


    public Track(long id, String title, String release_date, Artist artist,Album album, long duration) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.artist = artist;
        this.album=album;
        this.duration=duration;

    }
    public Track(){

    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
