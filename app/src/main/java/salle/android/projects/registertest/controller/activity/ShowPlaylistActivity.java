package salle.android.projects.registertest.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.FailureCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class ShowPlaylistActivity extends AppCompatActivity implements FailureCallback, PlaylistCallback {

    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        initViews();
        getData();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        PlaylistListAdapter adapter = new PlaylistListAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MostrarTracksDePlaylistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        PlaylistManager.getInstance(this).getAllPlaylist(this);
        mPlaylists = new ArrayList<>();
    }

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        mPlaylists = (ArrayList) playlists;
        PlaylistListAdapter adapter = new PlaylistListAdapter(this, mPlaylists);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onShowPlaylistFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed!", Toast.LENGTH_LONG); }

    @Override
    public void onCreateSuccess(Playlist playlist) {

    }

    @Override
    public void onCreateFailed(Throwable throwable) {

    }

    @Override
    public void onUpdateSucces(Playlist playlist) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG); }
}