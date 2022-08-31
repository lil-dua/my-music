package com.android.mymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayMusicAdapter extends RecyclerView.Adapter<PlayMusicAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> songsArrayList;

    public PlayMusicAdapter(Context context, ArrayList<Songs> songsArrayList) {
        this.context = context;
        this.songsArrayList = songsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.play_music_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs songs = songsArrayList.get(position);
        holder.txtPlayMusicSinger.setText(songs.getSinger());
        holder.txtPlayMusicIndex.setText(position + 1+ "");
        holder.txtPlayMusicNameSong.setText(songs.getSongName());
    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPlayMusicIndex,txtPlayMusicNameSong,txtPlayMusicSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlayMusicIndex = itemView.findViewById(R.id.textViewPlayMusicIndex);
            txtPlayMusicNameSong =itemView.findViewById(R.id.textViewPlayMusicNameSong);
            txtPlayMusicSinger = itemView.findViewById(R.id.textViewPlayMusicSinger);

        }
    }
}
