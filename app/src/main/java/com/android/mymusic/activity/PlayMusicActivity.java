package com.android.mymusic.activity;
import static com.android.mymusic.notification.MediaNotification.CHANNEL_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.mymusic.R;
import com.android.mymusic.adapter.ViewPagerPlayMusic;
import com.android.mymusic.fragment.Fragment_Music_Dish;
import com.android.mymusic.fragment.Fragment_PLay_List_Music;
import com.android.mymusic.model.Songs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    ViewPager viewPagerPlayMusic;
    public static ArrayList<Songs> songsArrayList = new ArrayList<>();
    public static ViewPagerPlayMusic songAdapter;
    MediaPlayer mediaPlayer;
    Fragment_Music_Dish fragment_music_dish;
    Fragment_PLay_List_Music fragment_pLay_list_music;
    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;
    private static final String ACTION_PREVIOUS = "com.android.mymusic.ACTION_PREVIOUS";
    private static final String ACTION_PAUSE = "com.android.mymusic.ACTION_PAUSE";
    private static final String ACTION_NEXT = "com.android.mymusic.ACTION_NEXT";



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
        SendMediaNotification();
    }

    private void SendMediaNotification() {
        // Load the image from the URL using Glide
        Glide.with(this)
                .asBitmap()
                .load(songsArrayList.get(position).getSongImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(PlayMusicActivity.this, "tag");


                        // Tạo PendingIntent cho action Previous
                        Intent previousIntent = new Intent(ACTION_PREVIOUS);
                        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                previousIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        // Tạo PendingIntent cho action Pause
                        Intent pauseIntent = new Intent(ACTION_PAUSE);
                        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                pauseIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        // Tạo PendingIntent cho action Next
                        Intent nextIntent = new Intent(ACTION_NEXT);
                        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                nextIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        // Build notification
                        Notification notification = new NotificationCompat.Builder(PlayMusicActivity.this, CHANNEL_ID)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setSmallIcon(R.drawable.ic_music_note_24)
                                .setLargeIcon(resource)
                                .setSubText("Music Player")
                                .setContentTitle(songsArrayList.get(position).getSongName())
                                .setContentText(songsArrayList.get(position).getSinger())
                                // Add media control buttons that invoke intent in your media service
                                .addAction(R.drawable.ic_previous_24,"Previous",previousPendingIntent)
                                .addAction(R.drawable.ic_pause_24,"Pause",pausePendingIntent)
                                .addAction(R.drawable.ic_next_24,"Next",nextPendingIntent)
                                // Apply the media style template
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setShowActionsInCompactView(1 /* #1: pause button*/)
                                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                                .build();

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(PlayMusicActivity.this);
                        managerCompat.notify(1,notification);

                        // Đăng ký BroadcastReceiver để bắt các broadcast intent
                        registerReceiver(musicControlReceiver, new IntentFilter(ACTION_PREVIOUS));
                        registerReceiver(musicControlReceiver, new IntentFilter(ACTION_PAUSE));
                        registerReceiver(musicControlReceiver, new IntentFilter(ACTION_NEXT));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Called when the resource is cleared
                    }
                });

    }
    // BroadcastReceiver để bắt các broadcast intent
    private BroadcastReceiver musicControlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(ACTION_PREVIOUS)) {
                    // Xử lý sự kiện Previous
                    onPreviousClicked();
                } else if (action.equals(ACTION_PAUSE)) {
                    // Xử lý sự kiện Pause
                    onPlayClick();
                    updateMediaNotification();
                } else if (action.equals(ACTION_NEXT)) {
                    // Xử lý sự kiện Next
                    onNextClicked();
                }
            }
        }
    };

    private void updateMediaNotification() {

        // Load the image from the URL using Glide
        Glide.with(this)
                .asBitmap()
                .load(songsArrayList.get(position).getSongImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int playIcon = mediaPlayer.isPlaying() ? R.drawable.ic_pause_24 : R.drawable.ic_play_24;

                        // Tạo PendingIntent cho action Previous
                        Intent previousIntent = new Intent(ACTION_PREVIOUS);
                        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                previousIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        // Tạo PendingIntent cho action Pause
                        Intent pauseIntent = new Intent(ACTION_PAUSE);
                        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                pauseIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        // Tạo PendingIntent cho action Next
                        Intent nextIntent = new Intent(ACTION_NEXT);
                        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(
                                PlayMusicActivity.this,
                                0,
                                nextIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(PlayMusicActivity.this, "tag");
                        // Create the updated notification
                        Notification updatedNotification = new NotificationCompat.Builder(PlayMusicActivity.this, CHANNEL_ID)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setSmallIcon(R.drawable.ic_music_note_24)
                                .setLargeIcon(resource)
                                .setSubText("Music Player")
                                .setContentTitle(songsArrayList.get(position).getSongName())
                                .setContentText(songsArrayList.get(position).getSinger())
                                .addAction(R.drawable.ic_previous_24, "Previous", previousPendingIntent)
                                .addAction(playIcon, "Play", pausePendingIntent)  // Update the play icon
                                .addAction(R.drawable.ic_next_24, "Next", nextPendingIntent)
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setShowActionsInCompactView(1)
                                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                                .build();

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(PlayMusicActivity.this);
                        managerCompat.notify(1, updatedNotification);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }


    private void FirstAction(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.getCurrentPosition();
        imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
        new PlayMusic().execute(songsArrayList.get(position).getSongLink());
        Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
        UpdateTime();
    }
    private void EventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(songAdapter.getItem(1) != null){
                    if(songsArrayList.size() > 0){
                        fragment_music_dish.PlayMusic(songsArrayList.get(position).getSongImage());
                        handler.removeCallbacks( this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        //----------------Button Play-----------------
        imageButtonPlay.setOnClickListener(view -> {
                onPlayClick();
        });
        //-----------------Button Repeat-----------------
        imageButtonRepeat.setOnClickListener(view -> {
            onRepeatSelected();
        });
        //-----------------Button Shuffle-----------------
        imageButtonShuffle.setOnClickListener(view -> {
            onShuffleSelected();
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
            onNextClicked();
        });
        //-----------------Button Previous-----------------
        imageButtonPrevious.setOnClickListener(view -> {
            onPreviousClicked();
        });
    }

    private void onPreviousClicked() {
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
                fragment_music_dish.PlayMusic(songsArrayList.get(position).getSongImage());
                Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
                UpdateTime();
            }
        }

        SendMediaNotification();

        imageButtonPrevious.setClickable(false);
        imageButtonNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            imageButtonPrevious.setClickable(true);
            imageButtonNext.setClickable(true);
        },500);
    }

    private void onNextClicked() {
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
                fragment_music_dish.PlayMusic(songsArrayList.get(position).getSongImage());
                Objects.requireNonNull(getSupportActionBar()).setTitle(songsArrayList.get(position).getSongName());
                UpdateTime();
            }
        }
        imageButtonPrevious.setClickable(false);
        imageButtonNext.setClickable(false);

        SendMediaNotification();

        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            imageButtonPrevious.setClickable(true);
            imageButtonNext.setClickable(true);
        },500);

    }

    private void onShuffleSelected() {
        if(!checkRandom){
            if(repeat){
                repeat = false;
                imageButtonShuffle.setImageResource(R.drawable.icons8_shuffled_50);
                imageButtonRepeat.setImageResource(R.drawable.icons8_repeat_50);

            }
            imageButtonShuffle.setImageResource(R.drawable.icons8_shuffled_50);
            checkRandom = true;
        }else {
            imageButtonShuffle.setImageResource(R.drawable.icons8_shuffle_50);
            checkRandom = false;
        }
    }

    private void onRepeatSelected() {
        if(!repeat){
            if(checkRandom){
                checkRandom = false;
                imageButtonRepeat.setImageResource(R.drawable.ic_repeated_50);
                imageButtonShuffle.setImageResource(R.drawable.icons8_shuffle_50);
            }
            imageButtonRepeat.setImageResource(R.drawable.ic_repeated_50);
            repeat = true;
        }else {
            imageButtonRepeat.setImageResource(R.drawable.icons8_repeat_50);
            repeat = false;
        }
    }

    private void onPlayClick() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            fragment_music_dish.onPauseClick();
            imageButtonPlay.setImageResource(R.drawable.ic_play_100);
        }else {
            assert mediaPlayer != null;
            mediaPlayer.start();
            fragment_music_dish.onResumeClick();
            imageButtonPlay.setImageResource(R.drawable.ic_pause_100);
        }
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

        fragment_music_dish = new Fragment_Music_Dish();
        fragment_pLay_list_music = new Fragment_PLay_List_Music();
        songAdapter = new ViewPagerPlayMusic(getSupportFragmentManager());
        songAdapter.addFragment(fragment_music_dish);
        songAdapter.addFragment(fragment_pLay_list_music);
        viewPagerPlayMusic.setAdapter(songAdapter);
        fragment_music_dish = (Fragment_Music_Dish) songAdapter.getItem(0);

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
        viewPagerPlayMusic = findViewById(R.id.viewPagerPlayMusic);


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
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
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