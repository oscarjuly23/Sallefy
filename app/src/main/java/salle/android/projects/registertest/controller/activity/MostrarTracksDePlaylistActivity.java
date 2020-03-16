package salle.android.projects.registertest.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.FailureCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class MostrarTracksDePlaylistActivity extends AppCompatActivity implements FailureCallback, PlaylistCallback, TrackCallback {
    private EditText Playlist_text;
    private TextView tracks;
    private Button btn_mostrar;
    private Playlist playlist;
    private ArrayList<Track> tracksDePlaylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tracks_playlist);
        initViews();
    }

    private void initViews() {
        tracks = (TextView) findViewById(R.id.list_songs);
        Playlist_text = (EditText) findViewById(R.id.nom_playlist);
        btn_mostrar = (Button) findViewById(R.id.createPl_btn_action);
        btn_mostrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cogemos los datos de las playlists
                    getPlaylistData();
                }
            });
        }

    private void getPlaylistData() {
        PlaylistManager.getInstance(this).getAllPlaylist(this);
    }
    private void geTrackData() {
        TrackManager.getInstance(this).getAllTracks(this);
    }
    private void updateList(String list) {
        tracks.setText(list);
    }

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        String Name_Playlist = Playlist_text.getText().toString();

        // Recorremos todas las playlists
        for (Playlist llistaPlaylists: playlists) {
            // Si coincide con la que hemos introducido:
            if (llistaPlaylists.getName().equals(Name_Playlist)) {
                playlist = llistaPlaylists;
                StringBuilder res = new StringBuilder();
                // Recorremos las traks de esta Playlist y las mostramos
                for (Track track: llistaPlaylists.getTracks()) {
                    tracksDePlaylist.add(track);
                        res.append(track.getName() + " - " + track.getUser().getLogin() + "\n");
                    }
                    updateList(res.toString());
                }
            }
        geTrackData();
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

    @Override
    public void onTracksReceived(List<Track> tracks) {

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
}
