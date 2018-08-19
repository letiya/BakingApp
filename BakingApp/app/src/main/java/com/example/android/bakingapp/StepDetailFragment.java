package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    private Context mContext;


    private SimpleExoPlayer mExoPlayer;

    @BindView(R.id.videoView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_recipe_step_instruction)
    TextView mStepInstruction;

    private final String TAG_CLICKED_STEP = "clickedStep";
    private final String PLAYBACK_POSITION = "playbackPosition";

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Recipe Step Detail fragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        mContext = rootView.getContext();

        Intent intentThatStartedThisActivity = getActivity().getIntent();
        Step clickedStep = intentThatStartedThisActivity.getParcelableExtra(TAG_CLICKED_STEP);

        ButterKnife.bind(this, rootView);

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String uriString = clickedStep.getVideoURL();
            if (uriString != "") {
                Uri uri = Uri.parse(uriString).buildUpon().build();
                String userAgent = Util.getUserAgent(mContext   , "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
                if (savedInstanceState != null) {
                    mExoPlayer.seekTo(savedInstanceState.getLong(PLAYBACK_POSITION));
                }
            }
        }

        mStepInstruction.setText(clickedStep.getDescription());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long playbackPosition = mExoPlayer.getCurrentPosition();
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) { // Note: On API 24+, onPause is still visible!
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
