package com.example.juraj.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StepVideoFragment extends Fragment {

    private static final String positionBundle = "position_bundle";
    private static final String stateBundle = "state_bundle";

    private long mPlayerPosition = 0;
    private boolean mPlayerState = false;

    private Step mStep;

    @BindView(R.id.exo_player)
    PlayerView mPlayerView;

    @BindView(R.id.step_description)
    TextView mStepDescription;

    private SimpleExoPlayer mSimpleExoPlayer;
    private TrackSelection.Factory mTrackSelectionFactory;
    private BandwidthMeter mBandwidthMeter;
    private TrackSelector mTrackSelector;
    private DataSource.Factory mDataSourceFactory;
    private MediaSource mediaSource;

    public StepVideoFragment() {
        // Required empty public constructor
    }

    public static StepVideoFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putSerializable("STEP", step);
        StepVideoFragment fragment = new StepVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if( bundle != null) {
            mStep = (Step) bundle.getSerializable("STEP");
            mPlayerPosition = bundle.getLong(positionBundle);
            mPlayerState = savedInstanceState.getBoolean(stateBundle);
        } else {
            mStep = null;
            Log.e("StepVideo", "mStep is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_video, container, false);
        ButterKnife.bind(this, rootView);

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
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), mTrackSelector);
            mDataSourceFactory = new DefaultDataSourceFactory(getContext(), "BakingApp");
            mediaSource = new ExtractorMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(Uri.parse(url), handler, null);

            mPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.setPlayWhenReady(mPlayerState);
            mSimpleExoPlayer.prepare(mediaSource);
            if(mPlayerPosition != 0) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
                Log.e("StepVideoActivity", mPlayerPosition+"");
            }
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(positionBundle, mSimpleExoPlayer.getCurrentPosition());
        outState.putBoolean(stateBundle, mSimpleExoPlayer.getPlayWhenReady());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if( mSimpleExoPlayer != null) {
            mSimpleExoPlayer.release();
        }
    }
}
