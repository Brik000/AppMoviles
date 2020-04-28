package com.example.deezer_challenge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class TrackActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title;
    TextView artist;
    TextView album;
    TextView duration;
    Button play;

    public static String formatTime(long biggy)
    {

        long hours = (long) biggy / 3600;
        long remainder = (long) biggy - hours * 3600;
        long mins = remainder / 60;
        remainder = remainder - mins * 60;
        long secs = remainder;

        String ints = mins+":"+secs;
        return ints;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_track);

    imageView=findViewById(R.id.imageTrack);
    title=findViewById(R.id.textViewTrackTitle);
    artist=findViewById(R.id.textViewTrackArtist);
    album=findViewById(R.id.textViewTrackAlbum);
    duration=findViewById(R.id.textViewTrackDuration);
    play=findViewById(R.id.buttonPlay);

    String stitle=(String)getIntent().getExtras().get("title");
    String sartist=(String)getIntent().getExtras().get("artist");
    long sduration=(long)getIntent().getExtras().get("duration");
    String salbum=(String)getIntent().getExtras().get("album");
    String spreview=(String)getIntent().getExtras().get("preview");
    String simage=(String)getIntent().getExtras().get("image");
    String slink=(String)getIntent().getExtras().get("link");

        Glide.with(this).load(simage).centerCrop().into(imageView);

        title.setText(stitle);

        artist.setText(sartist);
        album.setText(salbum);
        duration.setText("Duration: "+ formatTime(sduration));

        play.setOnClickListener((view)->{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Still working on this feature!",
                    Toast.LENGTH_SHORT);

            toast.show();

    });



}
}
