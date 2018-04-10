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


/**
 * A simple {@link Fragment} subclass.
 */
public class StepVideoFragment extends Fragment {

    private static final String positionBundle = "position_bundle";
    private static final String stateBundle = "state_bundle";

    private long mPlayerPosition = 0;
    private boolean mPlayerState = false;

    private Step mStep;

    @BindView(R.id.step_video_thumbnail_fragment)
    ImageView mThumbnailImageView;

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

        if( savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(positionBundle);
            mPlayerState = savedInstanceState.getBoolean(stateBundle);
        }

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
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), mTrackSelector);
            mDataSourceFactory = new DefaultDataSourceFactory(getContext(), "BakingApp");
            mediaSource = new ExtractorMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(Uri.parse(url), handler, null);

            mPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.setPlayWhenReady(mPlayerState);
            mSimpleExoPlayer.prepare(mediaSource);
            if(mPlayerPosition != 0) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
            }
        } else if(mStep.getThumbnailURL() != "") {
            // Check if thumbnail is an image
            // https://stackoverflow.com/questions/3453641/detect-if-specified-url-is-an-image-in-android
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
            mThumbnailImageView.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if( mSimpleExoPlayer != null) {
            outState.putLong(positionBundle, mSimpleExoPlayer.getCurrentPosition());
            outState.putBoolean(stateBundle, mSimpleExoPlayer.getPlayWhenReady());
        }
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
