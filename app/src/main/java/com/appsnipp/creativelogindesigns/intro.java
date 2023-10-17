package com.appsnipp.creativelogindesigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class intro extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.uliza_intro);
        videoView.setVideoURI(videoUri);

        // Set an OnCompletionListener to handle the video ending
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Video has ended, start the next activity
                Intent intent = new Intent(intro.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Start playing the video
        videoView.start();


    }
}