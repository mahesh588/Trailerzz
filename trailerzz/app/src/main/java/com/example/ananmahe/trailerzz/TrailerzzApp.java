package com.example.ananmahe.trailerzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TrailerzzApp extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Context context;

    public TrailerzzApp() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trailerzz_app);

        mRecyclerView = (RecyclerView) findViewById(R.id.youtubeDataRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        TrailerzzApp.context = getApplicationContext();

        //Syncing youtube data on create
        syncYoutubeData();

        //Add refresh Listener
        refreshYoutubeData();

        // use a linear layout manager
        mLayoutManager = new ZoomCenterCardLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new YouTubeDataAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Syncing youtube data onPause
        syncYoutubeData();
    }

    private void syncYoutubeData() {
        Intent intent = new Intent(this, YoutubeDataSync.class);
        intent.setAction(YoutubeDataSync.ACTION_YOUTUBE_DATA_SYNC);
        startService(intent);
    }

    private void refreshYoutubeData() {
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                syncYoutubeData();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onYoutubeDataChangeEvent(YoutubeDataChangeEvent event) {
        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setRefreshing(false);
    };

    public static Context getAppContext() {
        return TrailerzzApp.context;
    }
}




