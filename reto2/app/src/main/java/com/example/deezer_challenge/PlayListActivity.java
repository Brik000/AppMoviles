package com.example.deezer_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;

import model.Playlist;
import model.Track;
import model.TracksReciever;
import util.HTTPSWebUtilDomi;
import util.TrackRecyclerAdapter;

public class PlayListActivity extends AppCompatActivity {
    RecyclerView reciclerTracks;
    private ArrayList<Track> tracks;
    ImageView imageViewPlaylist;
    TextView playlistName;
    TextView playlistDescription;
    TextView songNumber;
    TextView fanNumber;
    TrackRecyclerAdapter recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        reciclerTracks=findViewById(R.id.trackRecycler);
        reciclerTracks.setLayoutManager(linearLayoutManager);
        imageViewPlaylist=findViewById(R.id.imageViewList);
        playlistName=findViewById(R.id.textViewNameList);
        playlistDescription=findViewById(R.id.textViewDescription);
        songNumber=findViewById(R.id.textViewSongs);
        fanNumber=findViewById(R.id.textViewFans);

        tracks = new ArrayList<Track>();
        recycler=new TrackRecyclerAdapter(tracks);

        reciclerTracks.setAdapter(recycler);

        recycler.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = reciclerTracks.getChildAdapterPosition(v);

                Track item = (recycler.getItem(pos));

                Intent i = new Intent(PlayListActivity.this, TrackActivity.class);
                i.putExtra("title",item.getTitle());
                i.putExtra("album",item.getAlbum().getTitle());
                i.putExtra("artist",item.getArtist().getName());
                i.putExtra("image",item.getAlbum().getCover());
                i.putExtra("duration",item.getDuration());
                startActivity(i);
            }
        });



        long id=(long)getIntent().getExtras().get("id");
        String urlSearchPlaylist="https://api.deezer.com/playlist/"+id;

        new Thread(
                () -> {
                    try {
                        HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
                        String json = util.GETrequest(urlSearchPlaylist);
                        System.out.println(json);

                        Gson g = new Gson();

                        Playlist pd = g.fromJson(json, Playlist.class);

                        runOnUiThread(()->{
                            Glide.with(this).load(pd.getPicture()).centerCrop().into(imageViewPlaylist);
                            playlistName.setText(pd.getTitle());
                            playlistDescription.setText(pd.getDescription());
                            songNumber.setText("Songs: "+pd.getNb_tracks()+"");
                            fanNumber.setText("Fans: "+pd.getFans()+"");
                            TracksReciever trackDetail=pd.getTracks();

                            ArrayList<Track> tracks=trackDetail.getData();
                            Log.e(">>>>>>>", tracks.get(0).getRelease_date()+"");
                            recycler.setTracks(tracks);

                        });




                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }

    private void onPlaylistSearchCompletion(Playlist pd){



    }

}
