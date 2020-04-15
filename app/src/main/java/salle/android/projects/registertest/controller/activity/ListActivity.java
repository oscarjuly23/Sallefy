package salle.android.projects.registertest.controller.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class ListActivity extends AppCompatActivity implements TrackCallback {

    private TextView tvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initViews();
        getData();
    }

    private void initViews() {
        tvList = (TextView) findViewById(R.id.tracks_string_list);
    }
    private void getData() {
        TrackManager.getInstance(this).getAllTracks(this);
    }
    private void updateList(String list) {
        tvList.setText(list);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        StringBuilder res = new StringBuilder();
        for (Track t: tracks) {
            res.append(t.getName() + " - " + t.getUser().getLogin() + "\n");
        }
        updateList(res.toString());
    }
    @Override
    public void onNoTracks(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed! " + throwable.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onLikeSuccess(Track track) {

    }
    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure" + throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}