package com.android.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.mymusic.R;
import com.android.mymusic.adapter.ViewPagerPlayMusic;
import com.android.mymusic.fragment.Fragment_Music_Dish;
import com.android.mymusic.model.Songs;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
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
    MediaPlayer mediaPlayer;
    Fragment_Music_Dish fragment_music_dish;
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
        FirstAction();  // fixed first action of play music when the PLayMusicActivity initialized.

    }

    private void FirstAction(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.getCurrentPosition();
        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
        new PlayMusic().execute(songsArrayList.get(position).getSongLink());
        Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
        UpdateTime();

//        Picasso.with(this).load(songsArrayList.get(position).getSongImage()).into();
    }
    private void EventClick() {
        //----------------Button Play-----------------
        imageButtonPlay.setOnClickListener(view -> {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imageButtonPlay.setImageResource(R.drawable.ic_play_100);
                }else {
                    assert mediaPlayer != null;
                    mediaPlayer.start();
                imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                }
        });
        //-----------------Button Repeat-----------------
        imageButtonRepeat.setOnClickListener(view -> {
            if(!repeat){
                if(checkRandom){
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
        });
        //-----------------Button Shuffle-----------------
        imageButtonShuffle.setOnClickListener(view -> {
            if(!checkRandom){
                if(repeat){
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
        imageButtonNext.setOnClickListener(view -> {
            if (songsArrayList.size() > 0){
                if(mediaPlayer != null && mediaPlayer.isPlaying() || mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if(position < (songsArrayList.size())){
                    imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                    position++;
                    if(repeat){
                        if(position == 0){
                            position = songsArrayList.size();
                        }
                        position -= 1;
                    }
                    if(checkRandom){
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
                    Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
                    UpdateTime();
                }
            }
            imageButtonPrevious.setClickable(false);
            imageButtonNext.setClickable(false);
            Handler handler1 = new Handler();
            handler1.postDelayed(() -> {
                imageButtonPrevious.setClickable(true);
                imageButtonNext.setClickable(true);
            },500);
        });
        //-----------------Button Previous-----------------
        imageButtonPrevious.setOnClickListener(view -> {
            if (songsArrayList.size() > 0){
                if(mediaPlayer != null && mediaPlayer.isPlaying() || mediaPlayer != null){
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
                    if(repeat){
                        position += 1;
                    }
                    if(checkRandom){
                        Random random = new Random();
                        int index = random.nextInt(songsArrayList.size());
                        if(index == position){
                            position = index - 1;
                        }
                        position = index;
                    }
                    new PlayMusic().execute(songsArrayList.get(position).getSongLink());
                    Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
                    UpdateTime();
                }
            }
            imageButtonPrevious.setClickable(false);
            imageButtonNext.setClickable(false);
            Handler handler1 = new Handler();
            handler1.postDelayed(() -> {
                imageButtonPrevious.setClickable(true);
                imageButtonNext.setClickable(true);
            },500);
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
                songsArrayList = intent.getParcelableArrayListExtra("AllSong");
            }
        }
    }

    private void Init() {
        setSupportActionBar(toolbarPlayMusic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarPlayMusic.setTitleTextColor(getResources().getColor(R.color.yellow));
        toolbarPlayMusic.setNavigationOnClickListener(view -> {
            mediaPlayer.stop();
            songsArrayList.clear();
            finish();
        });

        viewPagerPlayMusic = new ViewPagerPlayMusic(this);
        viewPager2PlayMusic.setAdapter(viewPagerPlayMusic);

        if(songsArrayList.size() > 0){
            Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(0).getSongName());
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
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.stop();
                mediaPlayer.reset();
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
        @SuppressLint("SimpleDateFormat")
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
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        },300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next){
                    if(position < (songsArrayList.size())){
                        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
                        position++;
                        if(repeat){
                            if(position == 0){
                                position = songsArrayList.size();
                            }
                            position -= 1;
                        }
                        if(checkRandom){
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
                        Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
                    }
                imageButtonPrevious.setClickable(false);
                imageButtonNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(() -> {
                    imageButtonPrevious.setClickable(true);
                    imageButtonNext.setClickable(true);
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