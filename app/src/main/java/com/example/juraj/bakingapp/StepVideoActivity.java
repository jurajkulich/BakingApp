package com.example.juraj.bakingapp;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.juraj.bakingapp.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepVideoActivity extends AppCompatActivity {

    private Step mStep;

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

        ButterKnife.bind(this);

        mStep = (Step) getIntent().getExtras().getSerializable("STEP");

        if( mStep.getDescription() != null) {
            mStepDescription.setText(mStep.getDescription());
        } else {
            mStepDescription.setText("");
        }

        if( mStep.getVideoURL() != "" || mStep.getThumbnailURL() != "") {
            String url = mStep.getThumbnailURL();
            if( mStep.getVideoURL() != "") {
                url = mStep.getVideoURL();
            }

            Handler handler = new Handler();
            mBandwidthMeter = new DefaultBandwidthMeter();
            mTrackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
            mTrackSelector = new DefaultTrackSelector(mTrackSelectionFactory);
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, mTrackSelector);
            mDataSourceFactory = new DefaultDataSourceFactory(this, "BakingApp");
            mediaSource = new ExtractorMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(Uri.parse(url), handler, null);

            mPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            mSimpleExoPlayer.prepare(mediaSource);

            mSimpleExoPlayer.setPlayWhenReady(true);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
