package com.example.juraj.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juraj.bakingapp.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepVideoActivity extends AppCompatActivity {

    private static final String positionBundle = "position_bundle";
    private static final String stateBundle = "state_bundle";

    private long mPlayerPosition = 0;
    private boolean mPlayerState = true;

    private Step mStep;

    @BindView(R.id.step_video_thumbnail)
    ImageView mThumbnailImageView;

    @BindView(R.id.exo_player_activity)
    PlayerView mPlayerView;

    @BindView(R.id.step_description_activity)
    TextView mStepDescription;

    private SimpleExoPlayer mSimpleExoPlayer;
    private TrackSelection.Factory mTrackSelectionFactory;
    private BandwidthMeter mBandwidthMeter;
    private TrackSelector mTrackSelector;
    private DataSource.Factory mDataSourceFactory;
    private MediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_video);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(positionBundle);
            mPlayerState = savedInstanceState.getBoolean(stateBundle);
            Log.e("OnCreate", mPlayerPosition+"");
        }

        ButterKnife.bind(this);

        mStep = (Step) getIntent().getExtras().getSerializable(RecipeDetailActivity.stepBundle);

        if( mStep.getDescription() != null) {
            mStepDescription.setText(mStep.getDescription());
        } else {
            mStepDescription.setText("");
        }

        if( mStep.getVideoURL() != "") {
            mThumbnailImageView.setVisibility(View.GONE);

            String url = mStep.getVideoURL();

            Handler handler = new Handler();
            mBandwidthMeter = new DefaultBandwidthMeter();
            mTrackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
            mTrackSelector = new DefaultTrackSelector(mTrackSelectionFactory);
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, mTrackSelector);
            mDataSourceFactory = new DefaultDataSourceFactory(this, "BakingApp");
            mediaSource = new ExtractorMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(Uri.parse(url), handler, null);

            mPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.setPlayWhenReady(mPlayerState);
            mSimpleExoPlayer.prepare(mediaSource);
            if(mPlayerPosition != 0) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
                Log.e("StepVideoActivity", mPlayerPosition+"");
            }

        } else if (mStep.getThumbnailURL() != "") {
            final String url = mStep.getThumbnailURL();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                String contentType = urlConnection.getHeaderField("Content-Type");
                                if( contentType.startsWith("image/")) {
                                    Picasso.get().load(url).into(mThumbnailImageView);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } else {
            mPlayerView.setVisibility(View.GONE);
            mThumbnailImageView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(positionBundle, mSimpleExoPlayer.getCurrentPosition());
        outState.putBoolean(stateBundle, mSimpleExoPlayer.getPlayWhenReady());
        Log.e("OnSave", mSimpleExoPlayer.getCurrentPosition()+"");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( mSimpleExoPlayer != null) {
            mSimpleExoPlayer.release();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
