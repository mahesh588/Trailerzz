package com.example.ananmahe.trailerzz;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ananmahe on 8/31/17.
 */

public class YoutubeDataController implements YoutubeDataAPI.IYoutubeDataAPIListener {
    private static YoutubeDataController youtubeDataController;


    private YoutubeDataController() {
        YoutubeDataAPI.getInstance().setListener(this);
    }

    public static YoutubeDataController getInstance() {
        if(youtubeDataController == null) {
            youtubeDataController = new YoutubeDataController();
        }
        return youtubeDataController;
    }

    public void refreshData(List<YouTubeData> youtubeDataList) {
        EventBus.getDefault().post(new YoutubeDataChangeEvent());
    }

    public List<YouTubeData> fetchData() {
        return YoutubeDataAPI.getInstance().getData();
    }

    interface DataFunctions {
        void refreshData();
    }

}
