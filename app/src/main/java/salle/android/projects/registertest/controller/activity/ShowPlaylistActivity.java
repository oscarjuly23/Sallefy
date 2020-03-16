package salle.android.projects.registertest.controller.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.restapi.callback.FailureCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class ShowPlaylistActivity extends AppCompatActivity implements FailureCallback, PlaylistCallback {
    private TextView tvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        initViews();
        getData();
    }

    private void initViews() {
        tvList = (TextView) findViewById(R.id.playlist_string_list);
    }
    private void getData() {
        PlaylistManager.getInstance(this).getAllPlaylist(this);
    }
    private void updateList(String list) {
        tvList.setText(list);
    }

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        StringBuilder res = new StringBuilder();
        for (Playlist p: playlists) {
            res.append(p.getName() + " - " + p.getUser().getLogin() + "\n");
        }
        updateList(res.toString());
    }

    @Override
    public void onShowPlaylistFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed!", Toast.LENGTH_LONG); }

    @Override
    public void onCreateSuccess(Playlist playlist) {

    }

    @Override
    public void OnCreateFailed(Throwable throwable) {

    }

    @Override
    public void onUpdateSucces(Playlist playlist) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG); }
}