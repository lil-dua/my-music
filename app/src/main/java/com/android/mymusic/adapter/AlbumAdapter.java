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
import com.android.mymusic.model.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.album_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.txtAlbumSinger.setText(album.getSinger());
        holder.txtAlbumName.setText(album.getAlbumName());
        Picasso.with(context).load(album.getAlbumImage()).into(holder.imgViewAlbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgViewAlbum;
        TextView txtAlbumName,txtAlbumSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewAlbum = itemView.findViewById(R.id.imageViewAlbum);
            txtAlbumName = itemView.findViewById(R.id.textViewAlbumName);
            txtAlbumSinger = itemView.findViewById(R.id.textViewSingerNameAlbum);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ListSongsActivity.class);
                intent.putExtra("idAlbum",albumArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }
}
