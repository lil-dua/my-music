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
        recyclerViewTopSong = view.findViewById(R.id.recycleViewTopSong);
        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<TopFavoriteSong>> callback = dataservice.GetFavoriteSong();
        callback.enqueue(new Callback<List<TopFavoriteSong>>() {
            @Override
            public void onResponse(Call<List<TopFavoriteSong>> call, Response<List<TopFavoriteSong>> response) {
                ArrayList<TopFavoriteSong> favoriteSongArrayList = (ArrayList<TopFavoriteSong>) response.body();
                topSongAdapter = new TopSongAdapter(getActivity(),favoriteSongArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewTopSong.setLayoutManager(linearLayoutManager);
                recyclerViewTopSong.setAdapter(topSongAdapter);

            }

            @Override
            public void onFailure(Call<List<TopFavoriteSong>> call, Throwable t) {

            }
        });
    }
}
