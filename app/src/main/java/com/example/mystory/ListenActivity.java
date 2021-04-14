package com.example.mystory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class ListenActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ListenActivity";
    MediaPlayer player;
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    ImageView playButton;
    ImageView rewindButton;
    ImageView fastforwardButton;
    Bitmap imageBitmap;
    ImageView storyImage;
    TextView timeElapsed;
    TextView timeRemaining;
    TextView storyTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback_activity);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        storyImage = findViewById(R.id.story_image);
        storyTitle = findViewById(R.id.story_title);
        storyTitle.setText(title);

        handler = new Handler();
        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), R.raw.sample4);
            /*
            String filePath = Environment.getExternalStorageDirectory()+"/folder/filename.mp3";
            player = new MediaPlayer();
            try {
                player.setDataSource(filePath);
            } catch (IOException e) {
                new Toast(this.getApplicationContext()).makeText(this.getApplicationContext(),"Unable to find audiofile", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Intent intent = new Intent(ListenActivity.this, MainActivity.class);
                startActivity(intent);

            }
            try {
                player.prepare();
            } catch (IOException e) {
                new Toast(this.getApplicationContext()).makeText(this.getApplicationContext(),"Unable to prepare audiofile", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Intent intent = new Intent(ListenActivity.this, MainActivity.class);
                startActivity(intent);
            }
            */
        }
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playCycle();
                mp.start();
            }
        });

        seekBar = findViewById(R.id.simpleSeekBar);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        seekBar.setMax(player.getDuration());

        playButton = findViewById(R.id.play_button);
        rewindButton = findViewById(R.id.rewind);
        fastforwardButton = findViewById(R.id.fast_forward);

        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((player.getCurrentPosition() - 5000) > 0) {
                    player.seekTo(player.getCurrentPosition() - 5000);
                    //seekBar.setProgress(player.getCurrentPosition() - 5000);
                }
                else {
                    player.seekTo(0);
                    //seekBar.setProgress(0);
                }
            }
        });

        fastforwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((player.getCurrentPosition() + 5000) < player.getDuration()) {
                    player.seekTo(player.getCurrentPosition() + 5000);
                    //seekBar.setProgress(player.getCurrentPosition() + 5000);
                }
                else {
                    player.seekTo(player.getDuration());
                    //seekBar.setProgress(player.getDuration());
                }
            }
        });

        timeElapsed = findViewById(R.id.time_progress);
        timeRemaining = findViewById(R.id.time_remaining);
        timeRemaining.setText(convertMilliseconds(player.getDuration()));


        playCycle();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    player.seekTo(progress);
                }
                timeElapsed.setText(convertMilliseconds(progress));
                timeRemaining.setText(convertMilliseconds(player.getDuration() - progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public String convertMilliseconds(int milli) {
        int min = (milli/1000)/60;
        int sec = (milli/1000)%60;
        if (sec < 10) {
            return String.valueOf(min) + ":0" + String.valueOf(sec);
        }
        return String.valueOf(min) + ":" + String.valueOf(sec);
    }

    public void selectPicture(View view) {
        UploadPictureDialog dialog = new UploadPictureDialog();
        dialog.show(getSupportFragmentManager(), "UploadPictureDialog");
    }


    public void playCycle() {
        seekBar.setProgress(player.getCurrentPosition());
        if (player.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
        playCycle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        handler.removeCallbacks(runnable);
    }

    public void pause(View view) {
        if(player.isPlaying()) {
            playButton.setImageResource(R.drawable.play);
            onPause();
        }
        else {
            playButton.setImageResource(R.drawable.pause);
            onResume();
        }
    }

    public void updateImage(Bitmap bitmap){
        imageBitmap = bitmap;
        storyImage.setImageBitmap(imageBitmap);
        //TODO: save the image to the story object
    }


}
