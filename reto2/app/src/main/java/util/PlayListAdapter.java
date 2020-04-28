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

import model.Playlist;



public class PlayListAdapter  extends BaseAdapter {

    ArrayList<Playlist> playlists;

    public PlayListAdapter(){
        playlists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int i) {
        return playlists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View playlistview = inflater.inflate(R.layout.playlist_piece_layout, null);
        ImageView image = playlistview.findViewById(R.id.imageViewPlaylist);
        TextView tvname = playlistview.findViewById(R.id.textName);
        TextView tvuser = playlistview.findViewById(R.id.textUser);
        TextView tvitems = playlistview.findViewById(R.id.textItems);

        tvname.setText(playlists.get(i).getTitle());
        tvuser.setText(playlists.get(i).getUser().getName());
        tvitems.setText("Songs: "+playlists.get(i).getNb_tracks()+"");
        System.out.println("acaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+playlists.get(i).getNb_tracks()+"");
        String picture=playlists.get(i).getPicture();

        Glide.with(playlistview).load(picture).centerCrop().into(image);



        return playlistview;
    }

    public void setPlaylists(ArrayList<Playlist> playlists){
        this.playlists=playlists;
        notifyDataSetChanged();
    }
}
