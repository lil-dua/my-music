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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> songsArrayList;

    public ListSongAdapter(Context context, ArrayList<Songs> songsArrayList) {
        this.context = context;
        this.songsArrayList = songsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listsong_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs songs = songsArrayList.get(position);
        holder.txtSongName.setText(songs.getSongName());
        holder.txtSinger.setText(songs.getSinger());
        holder.txtIndex.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtIndex,txtSongName,txtSinger;
        ImageView imageViewLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.textViewListSongIndex);
            txtSongName = itemView.findViewById(R.id.textViewNameOfSong);
            txtSinger = itemView.findViewById(R.id.textViewSinger);
            imageViewLike = itemView.findViewById(R.id.imageViewLikesOfSong);
            imageViewLike.setOnClickListener(view -> {
                imageViewLike.setImageResource(R.drawable.ic_love_50);
                Toast.makeText(context, "Đã thích!", Toast.LENGTH_SHORT).show();
                Dataservice dataservice = APIService.getService();
                Call<String> callback = dataservice.UpdateLikes("1",songsArrayList.get(getPosition()).getIdSong());
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
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("Song",songsArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }
}
