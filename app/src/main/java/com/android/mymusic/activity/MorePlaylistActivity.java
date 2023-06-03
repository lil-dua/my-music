package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.mymusic.R;
import com.android.mymusic.adapter.MorePLaylistAdapter;
import com.android.mymusic.model.Playlist;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MorePlaylistActivity extends AppCompatActivity {

    ImageView imageBack;
    RecyclerView recyclerViewMorePlaylist;
    MorePLaylistAdapter morePLaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_playlist);
        Mapping();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Playlist>> callback = dataservice.GetMorePlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlistArrayList = (ArrayList<Playlist>) response.body();
                morePLaylistAdapter = new MorePLaylistAdapter(MorePlaylistActivity.this,playlistArrayList);
                recyclerViewMorePlaylist.setLayoutManager(new GridLayoutManager(MorePlaylistActivity.this,2));
                recyclerViewMorePlaylist.setAdapter(morePLaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }


    private void Mapping() {
        imageBack = findViewById(R.id.imgBack);
        recyclerViewMorePlaylist = findViewById(R.id.recycleViewMorePlaylist);

        imageBack.setOnClickListener(v -> onBackPressed());
    }
}