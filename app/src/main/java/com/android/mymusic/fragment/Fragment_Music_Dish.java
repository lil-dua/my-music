package com.android.mymusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.mymusic.R;
import com.android.mymusic.activity.PlayMusicActivity;
import com.android.mymusic.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_Music_Dish extends Fragment {
    View view;
    ImageView imageViewPlayMusicImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_music_dish,container,false);
       imageViewPlayMusicImage = view.findViewById(R.id.imageViewPlayMusicImage);
        return view;
    }

}
