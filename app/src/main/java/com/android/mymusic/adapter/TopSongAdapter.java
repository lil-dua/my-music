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
import com.android.mymusic.model.TopFavoriteSong;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> topFavoriteSongArrayList;

    public TopSongAdapter(Context context, ArrayList<Songs> topFavoriteSongArrayList) {
        this.context = context;
        this.topFavoriteSongArrayList = topFavoriteSongArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.topsong_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs topFavoriteSong = topFavoriteSongArrayList.get(position);
        holder.txtSongName.setText(topFavoriteSong.getSongName());
        holder.txtSinger.setText(topFavoriteSong.getSinger());
        Picasso.with(context).load(topFavoriteSong.getSongImage()).into(holder.imageViewSong);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSongName,txtSinger;
        ImageView imageViewSong, imageViewLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSongName = itemView.findViewById(R.id.textViewTopSongName);
            txtSinger = itemView.findViewById(R.id.textViewTopSongSingerName);
            imageViewSong = itemView.findViewById(R.id.imageViewTopSong);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);
            //---------setItemOnClick-------
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("Song",topFavoriteSongArrayList.get(getPosition()));
                context.startActivity(intent);
            });
            //---------likes--------------
            imageViewLike.setOnClickListener(view -> {
                imageViewLike.setImageResource(R.drawable.ic_love_50);
                Toast.makeText(context, "Đã thích!", Toast.LENGTH_SHORT).show();
                Dataservice dataservice = APIService.getService();
                Call<String> callback = dataservice.UpdateLikes("1",topFavoriteSongArrayList.get(getPosition()).getIdSong());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if(result.equals("Success")){
                            Toast.makeText(context, "Da thich!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xin thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                imageViewLike.setEnabled(false);
            });
        }
    }
}
