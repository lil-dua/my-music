package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.mymusic.R;
import com.android.mymusic.adapter.MorePLaylistAdapter;
import com.android.mymusic.adapter.MoreThemeAdapter;
import com.android.mymusic.model.Playlist;
import com.android.mymusic.model.Songs;
import com.android.mymusic.model.Theme;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreThemeActivity extends AppCompatActivity {

    ImageView imageBack;
    RecyclerView recyclerViewMoreTheme;
    MoreThemeAdapter moreThemeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_theme);
        Mapping();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Theme>> callback = dataservice.GetMoreTheme();
        callback.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                ArrayList<Theme> themeArrayList = (ArrayList<Theme>) response.body();
                moreThemeAdapter = new MoreThemeAdapter(MoreThemeActivity.this,themeArrayList);
                recyclerViewMoreTheme.setLayoutManager(new GridLayoutManager(MoreThemeActivity.this,2));
                recyclerViewMoreTheme.setAdapter(moreThemeAdapter);
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

            }
        });
    }


    private void Mapping(){
        imageBack = findViewById(R.id.imgBack);
        recyclerViewMoreTheme = findViewById(R.id.recycleViewMoreTheme);

        imageBack.setOnClickListener(v -> onBackPressed());

    }

}