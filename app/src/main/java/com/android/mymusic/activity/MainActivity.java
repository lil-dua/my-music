package com.android.mymusic.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.mymusic.R;
import com.android.mymusic.adapter.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabLayout tabLayout = findViewById(R.id.myTabLayout);
        ViewPager2 viewPager = findViewById(R.id.myViewPager);


        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(myViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
           switch (position){
               case 0:  //home
                   tab.setIcon(R.drawable.ic_home_50);
                   break;
               case 1:  //search
                   tab.setIcon(R.drawable.ic_search_50);
                   break;
               case 2:
                   tab.setIcon(R.drawable.ic_love_50);
                   break;
           }
        }).attach(); //must remember this .attach()
    }
}