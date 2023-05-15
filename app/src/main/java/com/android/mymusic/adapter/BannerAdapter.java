package com.android.mymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.mymusic.R;
import com.android.mymusic.activity.ListSongsActivity;
import com.android.mymusic.model.Advertisement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    Context context;
    ArrayList<Advertisement> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<Advertisement> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_line,null);

        ImageView imgBackgroundBanner = view.findViewById(R.id.imgViewBackgroundBanner);

        //Load the data and push it on the banner
        Picasso.with(context).load(arrayListBanner.get(position).getImaget()).into(imgBackgroundBanner);

        //set Onclick function user receipted when click on the advertisement of the song
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ListSongsActivity.class);
            intent.putExtra("banner",arrayListBanner.get(position));
            context.startActivity(intent);
        });
        // add View custom form Fragment_Banner Class: GetData()
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
