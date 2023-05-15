package com.android.mymusic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.adapter.TopSongAdapter;
import com.android.mymusic.model.Songs;
import com.android.mymusic.model.TopFavoriteSong;
import com.android.mymusic.service.APIService;
import com.android.mymusic.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_TopFavoriteSong extends Fragment {
    View view;
    RecyclerView recyclerViewTopSong;
    TopSongAdapter topSongAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topfavoritesong,container,false);
        // mapping recycleView
        recyclerViewTopSong = view.findViewById(R.id.recycleViewTopSong);
        // get Data
        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.GetFavoriteSong();
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                ArrayList<Songs> songsArrayList = (ArrayList<Songs>) response.body();

                topSongAdapter = new TopSongAdapter(getActivity(),songsArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerViewTopSong.setLayoutManager(linearLayoutManager);
                recyclerViewTopSong.setAdapter(topSongAdapter);
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }


        });
    }
}
