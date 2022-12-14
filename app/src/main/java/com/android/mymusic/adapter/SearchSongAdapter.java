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
        ImageView imageViewSong,imageViewLikes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSongName = itemView.findViewById(R.id.textViewSearchSong);
            txtSingerName = itemView.findViewById(R.id.textViewSearchSinger);
            imageViewSong = itemView.findViewById(R.id.imageViewSearch);
            imageViewLikes = itemView.findViewById(R.id.imageViewSearchLikes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("Song",songsArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imageViewLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageViewLikes.setImageResource(R.drawable.ic_love_50);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpdateLikes("1",songsArrayList.get(getPosition()).getIdSong());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(context, "???? th??ch", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imageViewLikes.setEnabled(false);
                }
            });
        }
    }
}
