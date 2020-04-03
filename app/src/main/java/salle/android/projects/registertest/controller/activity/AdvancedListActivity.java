package salle.android.projects.registertest.controller.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class AdvancedListActivity extends Activity implements TrackCallback, TrackListCallback {

    public static final String TAG = AdvancedListActivity.class.getName();
    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";

    private TextView tvTitle;
    private TextView tvAuthor;

    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private SeekBar mSeekBar;

    private Handler mHandler;
    private Runnable mRunnable;

    //private BarVisualizer mVisualizer;
    private int mDuration;

    private RecyclerView mRecyclerView;

    private MediaPlayer mediaPlayer;
    private ArrayList<Track> mTracks;
    private int actualTrack = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_list);
        mDuration = 0;
        initViews();
        getData();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

      //  mVisualizer = findViewById(R.id.dynamic_barVisualizer);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mediaPlayer.getDuration());
                mDuration =  mediaPlayer.getDuration();
                playAudio();

                int audioSessionId = mediaPlayer.getAudioSessionId();
               // if (audioSessionId != -1)
        //            mVisualizer.setAudioSessionId(audioSessionId);
            }
        });

        mHandler = new Handler();

        tvAuthor = findViewById(R.id.dynamic_artist);
        tvTitle = findViewById(R.id.dynamic_title);

        btnBackward = (ImageButton)findViewById(R.id.dynamic_backward_btn);
        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualTrack = ((actualTrack-1)%(mTracks.size()));
                actualTrack = actualTrack < 0 ? (mTracks.size()-1):actualTrack;
                updateTrack(mTracks.get(actualTrack));
            }
        });
        btnForward = (ImageButton)findViewById(R.id.dynamic_forward_btn);
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualTrack = ((actualTrack+1)%(mTracks.size()));
                actualTrack = actualTrack >= mTracks.size() ? 0:actualTrack;

                updateTrack(mTracks.get(actualTrack));
            }
        });

        btnPlayStop = (ImageButton)findViewById(R.id.dynamic_play_btn);
        btnPlayStop.setTag(PLAY_VIEW);
        btnPlayStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btnPlayStop.getTag().equals(PLAY_VIEW)) {
                    playAudio();
                } else {
                    pauseAudio();
                }
            }
        });

        mSeekBar = (SeekBar) findViewById(R.id.dynamic_seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                if (mDuration > 0) {
                    int newProgress = ((progress*100)/mDuration);
                    System.out.println("New progress: " + newProgress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void playAudio() {
        mediaPlayer.start();
        updateSeekBar();
        btnPlayStop.setImageResource(R.drawable.ic_pause);
        btnPlayStop.setTag(STOP_VIEW);
        //Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_SHORT).show();
    }

    private void pauseAudio() {
        mediaPlayer.pause();
        btnPlayStop.setImageResource(R.drawable.ic_play);
        btnPlayStop.setTag(PLAY_VIEW);
        //Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();
    }


    private void prepareMediaPlayer(final String url) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error, couldn't play the music\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        connection.start();
    }

    public void updateSeekBar() {
        mSeekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    public void updateTrack(Track track) {
        //updateSessionMusicData(offset);
        tvAuthor.setText(track.getUserLogin());
        tvTitle.setText(track.getName());
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(track.getUrl());
            //mediaPlayer.pause();
            mediaPlayer.prepare();
        } catch(Exception e) {
        }
    }


    private void getData() {
        TrackManager.getInstance(this).getAllTracks(this);
        mTracks = new ArrayList<>();
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter((TrackListCallback) this,this, mTracks);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onPersonalTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onUserTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onCreateTrack() {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onTrackSelected(Track track) {

    }

    @Override
    public void onTrackSelected(int index) {
        actualTrack = index;
        updateTrack(mTracks.get(actualTrack));
    }
}