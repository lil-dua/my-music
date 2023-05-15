package com.android.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

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

    RecyclerView recyclerViewMoreAlbum;
    MoreAlbumAdapter moreAlbumAdapter;
    ImageView imageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_album);
        Mapping();
        GetData();
        Action();
    }

    private void Action() {
        imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetMoreAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                moreAlbumAdapter = new MoreAlbumAdapter(MoreAlbumActivity.this,albumArrayList);
                recyclerViewMoreAlbum.setLayoutManager(new GridLayoutManager(MoreAlbumActivity.this,2));
                recyclerViewMoreAlbum.setAdapter(moreAlbumAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Album>> call, @NonNull Throwable t) {

            }
        });
    }


    private void Mapping() {
        imageBack = findViewById(R.id.imgBack);
        recyclerViewMoreAlbum = findViewById(R.id.recycleViewMoreAlbum);
    }
}