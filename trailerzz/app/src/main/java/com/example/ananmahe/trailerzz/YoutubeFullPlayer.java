package com.example.ananmahe.trailerzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeFullPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String INTENT_VIDEO_ID = "videoId";
    public static String videoId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_full_player);
        Intent intent = getIntent();
        videoId = intent.getStringExtra(INTENT_VIDEO_ID);
        System.out.println("Got Message Youtube video: "+videoId);
        YouTubePlayerView youTubeView = findViewById(R.id.youtube_full_view_player);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored && videoId != null) {
            player.loadVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        System.out.println("Failed to launch youtube video: "+errorReason.toString());
    }

}
