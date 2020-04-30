package util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deezer_challenge.R;

import java.util.ArrayList;

import model.Playlist;

public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder> {

    private ArrayList<Playlist> playlists;
    private View.OnClickListener mClickListener;

    public PlaylistRecyclerAdapter(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = (View.OnClickListener) callback;
    }
    public void setPlaylists(ArrayList<Playlist> playlists)
    {
        this.playlists = playlists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_piece_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return holder;
    }
    public Playlist getItem(int position)
    {
        return playlists.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.custom(playlists.get(position));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        ImageView image ;
        TextView tvname ;
        TextView tvuser ;
        TextView tvitems ;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
             image = view.findViewById(R.id.imageViewPlaylist);
             tvname = view.findViewById(R.id.textName);
             tvuser = view.findViewById(R.id.textUser);
             tvitems = view.findViewById(R.id.textItems);
            view.setTag(this);

        }


        // Personalizamos un ViewHolder a partir de un lugar
        public void custom(Playlist playlist) {
            tvname.setText(playlist.getTitle());
            tvuser.setText(playlist.getUser().getName());
            tvitems.setText(playlist.getNb_tracks()+"");
            Glide.with(view).load(playlist.getPicture()).centerCrop().into(image);



        }
    }
}
