package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class CreatePlaylistFragment extends Fragment implements PlaylistCallback {

    public static final String TAG = CreatePlaylistFragment.class.getName();

    private EditText edNamePlaylist;
    private EditText edDescPlaylist;
    private Button btnCreate;

    public CreatePlaylistFragment() {
    }

    public static CreatePlaylistFragment getInstance(){
        return new CreatePlaylistFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_new_playlist, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        edNamePlaylist = (EditText) v.findViewById(R.id.name_playlist);
        edDescPlaylist = (EditText) v.findViewById(R.id.desc_playlist) ;
        btnCreate = (Button) v.findViewById(R.id.create_playlist_action);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edNamePlaylist.getText().toString();
                String desc = edDescPlaylist.getText().toString();
                PlaylistManager manager = new PlaylistManager(getActivity());
                if  (!name.isEmpty()) {
                    Playlist playlist = new Playlist(name);
                    playlist.setDescription(desc);
                    manager.createPlaylist(playlist, CreatePlaylistFragment.this);
                } else {
                    Toast.makeText(getContext(), "Failed to create. Name can't be NULL", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {

    }
    @Override
    public void onShowPlaylistFailure(Throwable throwable) {

    }
    @Override
    public void onCreateSuccess(Playlist playlist) {
        Toast.makeText(getContext(), "Created " + playlist.getName() + " playlist", Toast.LENGTH_LONG).show();
        edNamePlaylist.setText("");
        edDescPlaylist.setText("");
    }
    @Override
    public void onCreateFailed(Throwable throwable) {
        Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_LONG).show();
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
    public void getPlaylist(Playlist playlist) {

    }

    @Override
    public void onFailure (Throwable throwable) {
        Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
    }
}