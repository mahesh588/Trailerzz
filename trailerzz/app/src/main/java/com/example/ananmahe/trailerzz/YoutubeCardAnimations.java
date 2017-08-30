package com.example.ananmahe.trailerzz;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by ananmahe on 8/29/17.
 */

public class YoutubeCardAnimations {
    private View view;
    private static Handler handler = new Handler();

    public class PlayVideoRunnable implements Runnable {
        private View v;
        public PlayVideoRunnable(View v) {
            this.v = v;
        }
        @Override
        public void run() {
            //v.callOnClick();
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void animate(View view) {
        this.setView(view);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new PlayVideoRunnable(view),3000);
    }
}
