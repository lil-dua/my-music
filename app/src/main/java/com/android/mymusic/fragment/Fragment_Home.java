package com.android.mymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.android.mymusic.R;
import com.android.mymusic.activity.UserProfileActivity;

public class Fragment_Home extends Fragment {
    AppCompatImageView imageUserProfile;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home,container,false);
        Init();
        return view;
    }

    private void Init() {
        imageUserProfile = view.findViewById(R.id.imageUserProfile);
        imageUserProfile.setOnClickListener(view1 -> {startActivity(new Intent(getActivity(), UserProfileActivity.class));});
    }


}
