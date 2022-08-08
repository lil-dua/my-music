package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mymusic.R;
import com.android.mymusic.adapter.ViewPagerPlayMusic;
import com.android.mymusic.fragment.Fragment_Music_Dish;
import com.android.mymusic.fragment.Fragment_PLay_List_Music;
import com.android.mymusic.model.Songs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {

    Toolbar toolbarPlayMusic;
    TextView txtTimeSong,txtTotalTimeSong;
    SeekBar seekBarTime;
    ImageButton imageButtonPlay,imageButtonPrevious,imageButtonNext,
                imageButtonRepeat,imageButtonShuffle;
    ViewPager2 viewPager2PlayMusic;
    public static ArrayList<Songs> songsArrayList = new ArrayList<>();
    public static ViewPagerPlayMusic viewPagerPlayMusic;
    Fragment_Music_Dish fragmentMusicDish;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        //--------Check Internet------
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //-----------------------------
        Mapping();
        Init();
        GetDataFromIntent();
        EventClick();

    }

    private void EventClick() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if()
//            }
//        },500);
        //----------------Button Play-----------------
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imageButtonPlay.setImageResource(R.drawable.ic_play_100);
                }else {
                    mediaPlayer.start();
                    imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                }
            }
        });
        //-----------------Button Repeat-----------------
        imageButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat == false){
                    if(checkRandom == true){
                        checkRandom = false;
                        imageButtonRepeat.setImageResource(R.drawable.ic_repeated_50);
                        imageButtonShuffle.setImageResource(R.drawable.ic_shuffle_50);
                    }
                    imageButtonRepeat.setImageResource(R.drawable.ic_repeated_50);
                    repeat = true;
                }else {
                    imageButtonRepeat.setImageResource(R.drawable.ic_repeat_50);
                    repeat = false;
                }
            }
        });
        //-----------------Button Shuffle-----------------
        imageButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRandom == false){
                    if(repeat == true){
                        repeat = false;
                        imageButtonShuffle.setImageResource(R.drawable.ic_shuffled_50);
                        imageButtonRepeat.setImageResource(R.drawable.ic_repeat_50);

                    }
                    imageButtonShuffle.setImageResource(R.drawable.ic_shuffled_50);
                    checkRandom = true;
                }else {
                    imageButtonShuffle.setImageResource(R.drawable.ic_shuffle_50);
                    checkRandom = false;
                }
            }
        });
        //-----------------Seekbar Time-----------------
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        //-----------------Button Next---------------------
        imageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songsArrayList.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (songsArrayList.size())){
                        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = songsArrayList.size();
                            }
                            position -= 1;
                        }
                        if(checkRandom == true){
                            Random random = new Random();
                            int index = random.nextInt(songsArrayList.size());
                            if(index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if(position > (songsArrayList.size() - 1)){
                            position = 0;
                        }
                        new PlayMusic().execute(songsArrayList.get(position).getSongLink());
                        getSupportActionBar().setTitle(songsArrayList.get(position).getSongName());
                        UpdateTime();
                    }
                }
                imageButtonPrevious.setClickable(false);
                imageButtonNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonPrevious.setClickable(true);
                        imageButtonNext.setClickable(true);
                    }
                },5000);
            }
        });
        //-----------------Button Previous-----------------
        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songsArrayList.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < (songsArrayList.size())){
                        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                        position--;
                        if(position < 0){
                            position = songsArrayList.size() - 1;
                        }
                        if(repeat == true){
                            position += 1;
                        }
                        if(checkRandom == true){
                            Random random = new Random();
                            int index = random.nextInt(songsArrayList.size());
                            if(index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMusic().execute(songsArrayList.get(position).getSongLink());
                        getSupportActionBar().setTitle(songsArrayList.get(position).getSongName());
                        UpdateTime();
                    }
                }
                imageButtonPrevious.setClickable(false);
                imageButtonNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonPrevious.setClickable(true);
                        imageButtonNext.setClickable(true);
                    }
                },5000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        songsArrayList.clear();
        if(intent != null){
            if(intent.hasExtra("Song")){
                Songs songs = intent.getParcelableExtra("Song");
                songsArrayList.add(songs);
            }
            if(intent.hasExtra("AllSong")){
                ArrayList<Songs> arrayList = intent.getParcelableArrayListExtra("AllSong");
                songsArrayList = arrayList;
            }
        }
    }

    private void Init() {
        setSupportActionBar(toolbarPlayMusic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayMusic.setTitleTextColor(getResources().getColor(R.color.yellow));
        toolbarPlayMusic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                songsArrayList.clear();
                finish();
            }
        });

        viewPagerPlayMusic = new ViewPagerPlayMusic(this);
        viewPager2PlayMusic.setAdapter(viewPagerPlayMusic);

        if(songsArrayList.size() > 0){
            getSupportActionBar().setTitle(songsArrayList.get(0).getSongName());
            new PlayMusic().execute(songsArrayList.get(0).getSongLink());
            imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
        }
    }

    private void Mapping() {
        toolbarPlayMusic = findViewById(R.id.toolbarPlayMusic);
        txtTimeSong = findViewById(R.id.textViewTimeSong);
        txtTotalTimeSong = findViewById(R.id.textViewTotalTimeSong);
        seekBarTime = findViewById(R.id.seekBarPLayMusic);
        imageButtonPlay = findViewById(R.id.imageButtonPlay);
        imageButtonPrevious = findViewById(R.id.imageButtonPrevious);
        imageButtonNext = findViewById(R.id.imageButtonNext);
        imageButtonRepeat = findViewById(R.id.imageButtonRepeat);
        imageButtonShuffle = findViewById(R.id.imageButtonShuffle);
        viewPager2PlayMusic = findViewById(R.id.viewPagerPlayMusic);


    }

    class PlayMusic extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String songs) {
            super.onPostExecute(songs);
            try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
                mediaPlayer.setDataSource(songs);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarTime.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true){
                    if(position < (songsArrayList.size())){
                        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position = songsArrayList.size();
                            }
                            position -= 1;
                        }
                        if(checkRandom == true){
                            Random random = new Random();
                            int index = random.nextInt(songsArrayList.size());
                            if(index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if(position > (songsArrayList.size() - 1)){
                            position = 0;
                        }
                        new PlayMusic().execute(songsArrayList.get(position).getSongLink());
                        getSupportActionBar().setTitle(songsArrayList.get(position).getSongName());
                    }
                imageButtonPrevious.setClickable(false);
                imageButtonNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageButtonPrevious.setClickable(true);
                        imageButtonNext.setClickable(true);
                    }
                },5000);
                next = false;
                handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}