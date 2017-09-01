package com.example.ananmahe.trailerzz;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class YoutubeDataSync extends IntentService {

    public static final String ACTION_YOUTUBE_DATA_SYNC = "com.example.ananmahe.trailerzz.action.YOUTUBE_DATA_SYNC";
    private YoutubeDataAPI mYouTubeDataAPI;


    public YoutubeDataSync() {
        super("YoutubeDataSync");
        mYouTubeDataAPI = YoutubeDataAPI.getInstance();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_YOUTUBE_DATA_SYNC.equals(action)) {
                handleActionYoutubeDataSync();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionYoutubeDataSync() {
        mYouTubeDataAPI.fetchDataFromAPI();
    }

}
