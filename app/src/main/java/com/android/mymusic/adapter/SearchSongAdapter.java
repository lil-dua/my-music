package com.android.mymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.ListSongsActivity;
import com.android.mymusic.activity.PlayMusicActivity;
import com.android.mymusic.model.Songs;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> songsArrayList;

    public SearchSongAdapter(Context context, ArrayList<Songs> songsArrayList) {
        this.context = context;
        this.songsArrayList = songsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_song_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs songs = songsArrayList.get(position);
        holder.txtSongName.setText(songs.getSongName());
        holder.txtSingerName.setText(songs.getSinger());
        Picasso.with(context).load(songs.getSongImage()).into(holder.imageViewSong);
    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSongName,txtSingerName;
        ImageView imageViewSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSongName = itemView.findViewById(R.id.textViewSearchSong);
            txtSingerName = itemView.findViewById(R.id.textViewSearchSinger);
            imageViewSong = itemView.findViewById(R.id.imageViewSearch);
            // set item onClick
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("Song",songsArrayList.get(getPosition()));
                context.startActivity(intent);
            });

        }
    }
}
