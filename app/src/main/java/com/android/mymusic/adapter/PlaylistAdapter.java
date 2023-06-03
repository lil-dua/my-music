package com.android.mymusic.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.ListSongsActivity;
import com.android.mymusic.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<Playlist> playlistArrayList;

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlistArrayList) {
        this.context = context;
        this.playlistArrayList = playlistArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.playlist_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlistArrayList.get(position);
        holder.txtPlaylistName.setText(playlist.getPlaylistName());
        Picasso.with(context).load(playlist.getPlaylistIcon()).into(holder.imagePlayList);
    }

    @Override
    public int getItemCount() {
        return playlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePlayList;
        TextView txtPlaylistName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePlayList = itemView.findViewById(R.id.imageViewPlaylist);
            txtPlaylistName = itemView.findViewById(R.id.textViewPlaylistName);
            // set on item clicked
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ListSongsActivity.class);
                intent.putExtra("idPlaylist",playlistArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }

}
