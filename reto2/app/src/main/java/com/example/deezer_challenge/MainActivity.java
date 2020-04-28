package com.example.deezer_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import model.Playlist;
import model.Search;
import util.HTTPSWebUtilDomi;
import util.PlayListAdapter;


public class MainActivity extends AppCompatActivity {

    ListView listViewPlaylist;
    ImageButton imgButtonSearch;
    EditText editTextSearch;
    PlayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPlaylist=findViewById(R.id.listViewPlaylists);
        imgButtonSearch=findViewById(R.id.imageButtonSearch);
        editTextSearch=findViewById(R.id.editTextSearch);
        adapter= new PlayListAdapter();
        listViewPlaylist.setAdapter(adapter);

        listViewPlaylist.setOnItemClickListener((parent, view, position, id) -> {
            long itemId=((Playlist)(adapter.getItem(position))).getId();
            Intent i=new Intent(MainActivity.this,TrackListActivity.class);
            i.putExtra("id",itemId);
            startActivity(i);

        });

        imgButtonSearch.setOnClickListener((view)->{

            String playlistSearch=editTextSearch.getText().toString();
            String urlSearch="https://api.deezer.com/search/playlist/?q="+playlistSearch+"&index=0&output=json";

            new Thread(
                    () -> {
                        try {
                            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
                            String json = util.GETrequest(urlSearch);
                            Log.e(">>>",json);

                            Gson g = new Gson();

                            Search ps = g.fromJson(json, Search.class);
                            ArrayList<Playlist> playlists=ps.getData();
                            runOnUiThread( ()-> {
                                adapter.setPlaylists(playlists);
                            } );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();

        });



    }
}
