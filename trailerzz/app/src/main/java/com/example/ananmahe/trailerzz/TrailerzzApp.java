package com.example.ananmahe.trailerzz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TrailerzzApp extends YouTubeBaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailerzz_app);
        mRecyclerView = (RecyclerView) findViewById(R.id.youtubeDataRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<YouTubeData> youtubeChannelList = new ArrayList<YouTubeData>();

        youtubeChannelList.add(new YouTubeData("Tesla", "01/01/2019", "FzMRvrLBgZw"));
        youtubeChannelList.add(new YouTubeData("John Oliver", "31/01/2015", "ZwY2E0hjGuU"));
        youtubeChannelList.add(new YouTubeData("Music", "21/01/2017", "M029MbIiZiM"));

        mAdapter = new YouTubeDataAdapter(youtubeChannelList);
        mRecyclerView.setAdapter(mAdapter);
    }
}




