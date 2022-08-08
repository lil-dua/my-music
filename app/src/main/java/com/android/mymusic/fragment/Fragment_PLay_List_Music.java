package com.android.mymusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mymusic.R;
import com.android.mymusic.activity.PlayMusicActivity;
import com.android.mymusic.adapter.PlayMusicAdapter;

public class Fragment_PLay_List_Music extends Fragment {

    View view;
    RecyclerView recyclerViewListPlay;
    PlayMusicAdapter playMusicAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_list_music,container,false);
        recyclerViewListPlay = view.findViewById(R.id.recycleViewListPlay);
        if(PlayMusicActivity.songsArrayList.size() > 0){
            playMusicAdapter = new PlayMusicAdapter(getActivity(), PlayMusicActivity.songsArrayList);
            recyclerViewListPlay.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewListPlay.setAdapter(playMusicAdapter);
        }

        return view;
    }
}
