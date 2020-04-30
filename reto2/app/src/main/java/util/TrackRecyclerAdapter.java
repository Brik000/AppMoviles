package util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deezer_challenge.R;

import java.util.ArrayList;

import model.Track;

public class TrackRecyclerAdapter extends RecyclerView.Adapter<TrackRecyclerAdapter.ViewHolder> {
    ArrayList<Track> tracks;
    private View.OnClickListener mClickListener;

    public TrackRecyclerAdapter(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setTracks(ArrayList<Track> playlists)
    {
        this.tracks = playlists;
        notifyDataSetChanged();
    }
    public void setClickListener(View.OnClickListener callback) {
        mClickListener = (View.OnClickListener) callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_piece_layout, parent, false);
        TrackRecyclerAdapter.ViewHolder holder = new TrackRecyclerAdapter.ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return holder;    }

    public Track getItem(int position)
    {
        return tracks.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.custom(tracks.get(position));

    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        ImageView image;
        TextView tvname ;
        TextView tvartist ;
        TextView tvdate ;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
             image = view.findViewById(R.id.imageViewTrack);
             tvname = view.findViewById(R.id.textNameTrack);
             tvartist = view.findViewById(R.id.textArtistTrack);
             tvdate = view.findViewById(R.id.textDateTrack);

            view.setTag(this);

        }

        // Personalizamos un ViewHolder a partir de un lugar
        public void custom(Track track) {


            tvname.setText(track.getTitle());

            tvartist.setText(track.getArtist().getName());
            tvdate.setText(track.getRelease_date()+"");
            Glide.with(view).load(track.getAlbum().getCover()).centerCrop().into(image);



        }
    }
}
