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
import salle.android.projects.registertest.restapi.callback.FailureCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class PlaylistActivity extends AppCompatActivity implements FailureCallback, PlaylistCallback {
    private EditText etCrear;
    private Button btnNewPlaylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);
        initViews();
    }

    private void initViews() {
        etCrear = (EditText) findViewById(R.id.etNewPl);
        btnNewPlaylist = (Button) findViewById(R.id.createPl_btn_action);
        btnNewPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCreatePlaylist = etCrear.getText().toString();
                Playlist playlist = new Playlist();
                playlist.setPublicAccessible(true);
                playlist.setName(nameCreatePlaylist);

                PlaylistManager manager = new PlaylistManager(getApplicationContext());
                manager.createPlaylist(playlist, PlaylistActivity.this);
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
        Toast.makeText(this, "PlaylistCallback here!\n Playlist name:" + playlist.getName(), Toast.LENGTH_LONG).show(); }

    @Override
    public void OnCreateFailed(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed ", Toast.LENGTH_LONG).show(); }

    @Override
    public void onUpdateSucces(Playlist playlist) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show(); }
}