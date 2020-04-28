package util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deezer_challenge.R;

import java.util.ArrayList;

import model.Track;

public class TrackAdapter extends BaseAdapter {
    ArrayList<Track> tracks;

    public TrackAdapter(){
        tracks = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int i) {
        return tracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View playlistview = inflater.inflate(R.layout.track_piece_layout, null);
        ImageView image = playlistview.findViewById(R.id.imageViewTrack);
        TextView tvname = playlistview.findViewById(R.id.textNameTrack);
        TextView tvartist = playlistview.findViewById(R.id.textArtistTrack);
        TextView tvdate = playlistview.findViewById(R.id.textDateTrack);

        tvname.setText(tracks.get(i).getTitle());
        tvartist.setText(tracks.get(i).getArtist().getName());
        tvdate.setText(tracks.get(i).getRelease_date().split("-")[0]);
        String picture=tracks.get(i).getAlbum().getCover();

        Glide.with(playlistview).load(picture).centerCrop().into(image);

        return playlistview;
    }

    public void setTracks(ArrayList<Track> tracks){
        this.tracks=tracks;
        notifyDataSetChanged();
    }

    public void addTrack(Track track){
        tracks.add(track);
        notifyDataSetChanged();
    }
}
