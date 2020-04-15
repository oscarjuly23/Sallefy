package salle.android.projects.registertest.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SongActivity extends AppCompatActivity implements TrackCallback, PlaylistCallback, FailureCallback {

    private EditText etNamePl;
    private EditText etNameTrack;
    private Button btn_afegir;
    private Track track;
    private Playlist playlist;
    private ArrayList<Track> tracksDePlaylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        initViews();
    }

    private void initViews() {
        etNamePl = (EditText) findViewById(R.id.Playlist_name);
        etNameTrack = (EditText) findViewById(R.id.song_name);
        btn_afegir = (Button) findViewById(R.id.afegir);

        btn_afegir.setOnClickListener(new View.OnClickListener() {
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
    private void addTrackToPlaylist() {
        PlaylistManager.getInstance(this).addSong(playlist, this);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        // Cogemos el nombre de la Track introducida por teclado.
        String Name_Track = etNameTrack.getText().toString();

        for (Track llistaTracks: tracks) {
            // Si el nombre que estamos recorriendo de la Track coincide con la del teclado nos la guardamos.
            if (llistaTracks.getName().equals(Name_Track)) {
                track = llistaTracks;
                break;
            }
        }
        // Añadimos la Track guardada a la lista de Tracks de nuestra playlist.
        tracksDePlaylist.add(track);
        // Añadimos esta lista a nuestra Playlist.
        playlist.setTracks(tracksDePlaylist);

        addTrackToPlaylist();
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
    public void onLikeSuccess(Track track) {

    }
    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG); }
    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        String Name_Playlist = etNamePl.getText().toString();

        for (Playlist llistaPlaylists: playlists){
            if (llistaPlaylists.getName().equals(Name_Playlist)) {
                playlist = llistaPlaylists;
                for (Track track: llistaPlaylists.getTracks()){
                    tracksDePlaylist.add(track);
                }
            }
        }
        geTrackData();
    }
    @Override
    public void onShowPlaylistFailure(Throwable throwable) {

    }
    @Override
    public void onCreateSuccess(Playlist playlist) {

    }
    @Override
    public void onCreateFailed(Throwable throwable) {

    }
    @Override
    public void onUpdateSucces(Playlist playlist) {
        Toast.makeText(this, "Song " + etNameTrack.getText().toString()+ " Added to  Playlist" + etNamePl.getText().toString(), Toast.LENGTH_LONG).show(); }
    @Override
    public void onFollowSucces(Playlist playlist) {

    }
    @Override
    public void getIsFollowed(Playlist playlist) {

    }
}