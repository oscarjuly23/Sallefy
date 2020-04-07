package salle.android.projects.registertest.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class PlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText edNamePlaylist;
    private EditText edDescPlaylist;
    private Button btnCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);
        initViews();
    }

    private void initViews() {
        edNamePlaylist = (EditText) findViewById(R.id.name_playlist);
        edDescPlaylist = (EditText) findViewById(R.id.desc_playlist) ;
        btnCreate = (Button) findViewById(R.id.create_playlist_action);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edNamePlaylist.getText().toString();
                String desc = edDescPlaylist.getText().toString();
                Playlist playlist = new Playlist(name);
                playlist.setDescription(desc);
                PlaylistManager manager = new PlaylistManager(PlaylistActivity.this);
                manager.createPlaylist(playlist,PlaylistActivity.this);
            }
        });
    }

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {

    }

    @Override
    public void onShowPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onCreateSuccess(Playlist playlist) {
        Toast.makeText(this, "Created " + playlist.getName() + " playlist", Toast.LENGTH_LONG).show();
        edNamePlaylist.setText("");
        edDescPlaylist.setText("");
    }

    @Override
    public void onCreateFailed(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failed to create", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpdateSucces(Playlist playlist) {

    }

    @Override
    public void onFollowSucces(Playlist playlist) {

    }

    @Override
    public void getIsFollowed(Playlist playlist) {

    }

    @Override
    public void onFailure (Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG);
    }

}