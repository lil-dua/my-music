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
import com.android.mymusic.activity.MorePlaylistActivity;
import com.android.mymusic.model.Playlist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MorePLaylistAdapter extends RecyclerView.Adapter<MorePLaylistAdapter.ViewHolder> {

    Context context;
    ArrayList<Playlist> playlistArrayList;

    public MorePLaylistAdapter(Context context, ArrayList<Playlist> playlistArrayList) {
        this.context = context;
        this.playlistArrayList = playlistArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.more_playlist_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlistArrayList.get(position);
        Picasso.with(context).load(playlist.getPlaylistImage()).into(holder.imageViewPlaylistImage);
        holder.txtPlaylistName.setText(playlist.getPlaylistName());
    }

    @Override
    public int getItemCount() {
        return playlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewPlaylistImage;
        TextView txtPlaylistName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlaylistImage = itemView.findViewById(R.id.imageViewMorePlaylist);
            txtPlaylistName = itemView.findViewById(R.id.textViewMorePLaylistName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListSongsActivity.class);
                    intent.putExtra("itemPlaylist",playlistArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
