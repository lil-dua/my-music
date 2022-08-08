package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    Toolbar toolbar;
    RecyclerView recyclerViewMorePlaylist;
    MorePLaylistAdapter morePLaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_playlist);
        Mapping();
        Init();
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

    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play Lists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.yellow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping() {
        toolbar = findViewById(R.id.toolbarMorePlaylist);
        recyclerViewMorePlaylist = findViewById(R.id.recycleViewMorePlaylist);
    }
}