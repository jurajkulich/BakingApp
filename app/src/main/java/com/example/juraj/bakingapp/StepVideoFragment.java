package com.example.juraj.bakingapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juraj.bakingapp.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepVideoFragment extends Fragment {

    Step mStep;

    private SimpleExoPlayer mSimpleExoPlayer;
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

        Handler handler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        // mediaSource = new ExtractorMediaSource();

        return rootView;
    }

}
