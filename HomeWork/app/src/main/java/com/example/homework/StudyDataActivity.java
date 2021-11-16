package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;
public class StudyDataActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button btn_start,btn_end,btn_back,btn_pause;
    private MediaController mediaController;
    Integer style,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_data);
        init();
    }
    private void init() {
        videoView= findViewById(R.id.videoView);
        btn_start= findViewById(R.id.btn_start);
        btn_end= findViewById(R.id.btn_end);
        btn_pause=findViewById(R.id.btn_pause);
        videoView = findViewById(R.id.videoView);
        btn_back=findViewById(R.id.btn_back1);
        style=getIntent().getIntExtra("position",0);
        String uri;
        if(style==0){
             uri = "android.resource://" + getPackageName() + "/" + R.raw.test2;
        }else if(style==1){
             uri = "android.resource://" + getPackageName() + "/" + R.raw.test3;
        }else {
             uri = "android.resource://" + getPackageName() + "/" + R.raw.test4;
        }
        mediaController = new MediaController(this);

        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.requestFocus();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击开始准备播放视频
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVide();
            }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                }else {
                    videoView.start();
                }
            }
        });



    }

    private void startVide() {
        videoView.start();
    }

}

