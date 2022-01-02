package com.example.assiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoErbil extends AppCompatActivity {

    VideoView video;


    int posistion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_erbil);

        this.setTitle(getResources().getString(R.string.aboutvideo));

        video = findViewById(R.id.video);

        String path = "android.resource://com.example.assiment/raw/"+R.raw.erbilvideo2;

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);

        video.setVideoPath(path);
        video.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        posistion = video.getCurrentPosition();
    }

    @Override
    public void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("posit" , posistion);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        posistion =  savedInstanceState.getInt("posit");
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.seekTo(posistion+10);
    }
}
