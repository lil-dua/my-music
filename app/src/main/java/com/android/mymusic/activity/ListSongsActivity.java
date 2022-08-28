package com.android.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.adapter.ListSongAdapter;
import com.android.mymusic.model.Advertisement;
import com.android.mymusic.model.Album;
import com.android.mymusic.model.Category;
import com.android.mymusic.model.Playlist;
import com.android.mymusic.model.Songs;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongsActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewListSong;
    FloatingActionButton floatingActionButton;
    Advertisement advertisement;
    ImageView imageViewListSong;
    ArrayList<Songs> songsArrayList;
    ListSongAdapter listSongAdapter;
    Playlist playlist;
    Category category;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_songs);
        //--------Check Internet------
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //-----------------------------
        DataIntent();
        Mapping();
        Init();
        if(advertisement != null && !advertisement.getSongName().equals("")){
            setValueInView(advertisement.getSongName(),advertisement.getSongImage());
            GetDataAdvertisement(advertisement.getIdAdvertisement());
        }
        if(playlist != null && !playlist.getPlaylistName().equals("")){
            setValueInView(playlist.getPlaylistName(),playlist.getPlaylistIcon());
            GetDataPlaylist(playlist.getIdPlaylist());
        }
        if(category != null && !category.getCategoryName().equals("")){
            setValueInView(category.getCategoryName(),category.getCategoryImage());
            GetDataCategory(category.getIdCategory());
        }
        if(album != null && !album.getAlbumName().equals("")){
            setValueInView(album.getAlbumName(),album.getAlbumImage());
            GetDataAlbum(album.getIdAlbum());
        }


    }


    private void setValueInView(String name, String image) {
        collapsingToolbarLayout.setTitle(name);
        try {
            URL url = new URL(image);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(image).into(imageViewListSong);
    }


    private void GetDataAdvertisement(String idAdvertisement) {
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetListSongWithAdvertisement(idAdvertisement);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(@NonNull Call<List<Songs>> call, Response<List<Songs>> response) {
                songsArrayList = (ArrayList<Songs>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongsActivity.this,songsArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(ListSongsActivity.this));
                recyclerViewListSong.setAdapter(listSongAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }

    private void GetDataPlaylist(String idPlaylist) {
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetListSongWithPlaylist(idPlaylist);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                songsArrayList = (ArrayList<Songs>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongsActivity.this,songsArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(ListSongsActivity.this));
                recyclerViewListSong.setAdapter(listSongAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }


    private void GetDataCategory(String idCategory){
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetListSongWithCategory(idCategory);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                songsArrayList = (ArrayList<Songs>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongsActivity.this,songsArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(ListSongsActivity.this));
                recyclerViewListSong.setAdapter(listSongAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }
    private void GetDataAlbum(String idAlbum) {
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetListSongWithAlbum(idAlbum);
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                songsArrayList = (ArrayList<Songs>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongsActivity.this,songsArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(ListSongsActivity.this));
                recyclerViewListSong.setAdapter(listSongAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }
    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void Mapping() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.toolbarListSong);
        recyclerViewListSong = findViewById(R.id.recycleViewListSong);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imageViewListSong = findViewById(R.id.imageViewListSong);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("banner")){
                advertisement = (Advertisement) intent.getSerializableExtra("banner");
            }
            if(intent.hasExtra("itemPlaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemPlaylist");
            }
            if(intent.hasExtra("idCategory")){
                category = (Category) intent.getSerializableExtra("idCategory");
            }
            if(intent.hasExtra("idAlbum")){
                album = (Album) intent.getSerializableExtra("idAlbum");
            }
        }
    }

    private void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(ListSongsActivity.this,PlayMusicActivity.class);
            intent.putExtra("AllSong",songsArrayList);
            startActivity(intent);
        });
    }
}