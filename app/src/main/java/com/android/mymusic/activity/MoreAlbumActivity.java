package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.mymusic.R;
import com.android.mymusic.adapter.MoreAlbumAdapter;
import com.android.mymusic.model.Album;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreAlbumActivity extends AppCompatActivity {

    Toolbar toolbarMoreAlbum;
    RecyclerView recyclerViewMoreAlbum;
    MoreAlbumAdapter moreAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_album);
        Mapping();
        Init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetMoreAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                moreAlbumAdapter = new MoreAlbumAdapter(MoreAlbumActivity.this,albumArrayList);
                recyclerViewMoreAlbum.setLayoutManager(new GridLayoutManager(MoreAlbumActivity.this,2));
                recyclerViewMoreAlbum.setAdapter(moreAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void Init() {
        setSupportActionBar(toolbarMoreAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Albums");
        toolbarMoreAlbum.setTitleTextColor(getResources().getColor(R.color.yellow));
        toolbarMoreAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping() {
        toolbarMoreAlbum = findViewById(R.id.toolbarMoreAlbum);
        recyclerViewMoreAlbum = findViewById(R.id.recycleViewMoreAlbum);
    }
}