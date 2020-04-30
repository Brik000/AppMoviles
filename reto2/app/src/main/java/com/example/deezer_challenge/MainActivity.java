package com.example.deezer_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import model.Playlist;
import model.Search;
import util.HTTPSWebUtilDomi;
import util.PlaylistRecyclerAdapter;


public class MainActivity extends AppCompatActivity {

    RecyclerView reciclerPlaylist;
    ImageButton imgButtonSearch;
    private ArrayList<Playlist> playlists;
    EditText editTextSearch;
    PlaylistRecyclerAdapter recycler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setContentView(R.layout.activity_main);

        reciclerPlaylist=findViewById(R.id.playListRecycler);
        reciclerPlaylist.setLayoutManager(linearLayoutManager);
        imgButtonSearch=findViewById(R.id.imageButtonSearch);
        editTextSearch=findViewById(R.id.editTextSearch);

        playlists = new ArrayList<Playlist>();
        recycler=new PlaylistRecyclerAdapter(playlists);

        reciclerPlaylist.setAdapter(recycler);

        recycler.setClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                int pos= reciclerPlaylist.getChildAdapterPosition(v);

                long itemId  = ((Playlist)(recycler.getItem(pos))).getId();
                Intent i=new Intent(MainActivity.this,PlayListActivity.class);
                i.putExtra("id",itemId);
                startActivity(i);
            }
        });



        imgButtonSearch.setOnClickListener((view)->{

            String playlistSearch=editTextSearch.getText().toString();
            String urlSearch="https://api.deezer.com/search/playlist/?q="+playlistSearch+"&index=0&output=json";

            new Thread(
                    () -> {
                        try {
                            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
                            String json = util.GETrequest(urlSearch);

                            Gson g = new Gson();

                            Search ps = g.fromJson(json, Search.class);
                            ArrayList<Playlist> playlists=ps.getData();
                            runOnUiThread( ()-> {
                                recycler.setPlaylists(playlists);
                            } );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();

        });



    }
}
