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
import java.util.zip.Inflater;

public class MoreAlbumAdapter extends RecyclerView.Adapter<MoreAlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> albumArrayList;

    public MoreAlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.more_album_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        Picasso.with(context).load(album.getAlbumImage()).into(holder.imageViewMoreAlbum);
        holder.txtMoreAlbum.setText(album.getAlbumName());
        holder.txtSingerMoreAlbum.setText(album.getSinger());

    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMoreAlbum;
        TextView txtMoreAlbum,txtSingerMoreAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMoreAlbum = itemView.findViewById(R.id.imageViewMoreAlbum);
            txtMoreAlbum = itemView.findViewById(R.id.textViewMoreAlbumName);
            txtSingerMoreAlbum = itemView.findViewById(R.id.textViewSingerMoreAlbum);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ListSongsActivity.class);
                intent.putExtra("idAlbum",albumArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }
}
