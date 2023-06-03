package com.android.mymusic.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.mymusic.R;
import com.android.mymusic.activity.PlayMusicActivity;
import com.android.mymusic.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Music_Dish extends Fragment {
    View view;
    CircleImageView circleImageViewSong;
    ObjectAnimator objectAnimator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_music_dish,container,false);
       circleImageViewSong = view.findViewById(R.id.circleImageSong);
       objectAnimator = ObjectAnimator.ofFloat(circleImageViewSong,"rotation",0f,360f);
       objectAnimator.setDuration(10000);
       objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
       objectAnimator.setRepeatMode(ValueAnimator.RESTART);
       objectAnimator.setInterpolator(new LinearInterpolator());
       objectAnimator.start();
       return view;
    }

    public void PlayMusic(String image) {
        Picasso.with(getContext()).load(image).into(circleImageViewSong);
    }

    public void onPauseClick(){
        objectAnimator.pause();
    }

    public void onResumeClick(){
        objectAnimator.resume();
    }

}
