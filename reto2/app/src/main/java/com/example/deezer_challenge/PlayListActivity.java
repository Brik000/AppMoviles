package com.example.deezer_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;

import model.Playlist;
import model.Track;
import util.HTTPSWebUtilDomi;
import util.TrackAdapter;

public class PlayListActivity extends AppCompatActivity {
    TrackAdapter adapter;
    ImageView imageViewPlaylist;
    TextView playlistName;
    TextView playlistDescription;
    TextView songNumber;
    TextView fanNumber;
    ListView songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        adapter=new TrackAdapter();
        imageViewPlaylist=findViewById(R.id.imageViewList);
        playlistName=findViewById(R.id.textViewNameList);
        playlistDescription=findViewById(R.id.textViewDescription);
        songNumber=findViewById(R.id.textViewSongs);
        fanNumber=findViewById(R.id.textViewFans);
        songs=findViewById(R.id.listViewTracks);

        songs.setAdapter(adapter);

        songs.setOnItemClickListener((parent, view, position, id) -> {
            Track td=(Track) adapter.getItem(position);
            Intent i=new Intent(PlayListActivity.this,TrackActivity.class);
            i.putExtra("title",td.getTitle());
            i.putExtra("album",td.getAlbum().getTitle());
            i.putExtra("artist",td.getArtist().getName());
            i.putExtra("image",td.getAlbum().getCover());
            i.putExtra("duration",td.getDuration());
            startActivity(i);
        });

        long id=(long)getIntent().getExtras().get("id");
        String urlSearchPlaylist="https://api.deezer.com/playlist/"+id;
        String baseUrlSearchTrack="https://api.deezer.com/track/";

        new Thread(
                () -> {
                    try {
                        HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
                        String json = util.GETrequest(urlSearchPlaylist);
                        System.out.println(json);
                        Log.e(">>>",json);

                        Gson g = new Gson();

                        Playlist pd = g.fromJson(json, Playlist.class);

                        runOnUiThread(()->{
                            Glide.with(this).load(pd.getPicture()).centerCrop().into(imageViewPlaylist);
                            playlistName.setText(pd.getTitle());
                            playlistDescription.setText(pd.getDescription());
                            songNumber.setText("Songs: "+pd.getNb_tracks()+"");
                            fanNumber.setText("Fans: "+pd.getFans()+"");
                        });


                        ArrayList<Track> tracks=new ArrayList<Track>();

                        for (int cont=0;cont<pd.getTracks().getData().size();cont++){
                            String search=  util.GETrequest(baseUrlSearchTrack+pd.getTracks().getData().get(cont).getId());
                            Track trackDetail=g.fromJson(search,Track.class);
                            runOnUiThread(()->{
                                adapter.addTrack(trackDetail);
                            });
                        }






                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }

    private void onPlaylistSearchCompletion(Playlist pd){



    }

}
