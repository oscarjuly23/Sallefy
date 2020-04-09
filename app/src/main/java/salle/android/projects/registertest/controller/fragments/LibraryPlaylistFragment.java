package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.PlaylistAdapterCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.MeCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.MeManager;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class LibraryPlaylistFragment extends Fragment implements FragmentCallback, MeCallback, PlaylistAdapterCallback {

    public static final String TAG = LibraryPlaylistFragment.class.getName();

    private Button btnCreatePlaylist;
    private PlaylistListAdapter mPlaylistAdapter;
    private RecyclerView mRecyclerView;
    private MeManager meManager;
    private ArrayList<Playlist> mPlaylist;

    public LibraryPlaylistFragment() {

    }

    public static LibraryPlaylistFragment getInstance() {
        return new LibraryPlaylistFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_library_playlists, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {

        btnCreatePlaylist = (Button) v.findViewById(R.id.create_playlist_action);
        btnCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = CreatePlaylistFragment.getInstance();
                onChangeFragment(fragment);
            }
        });
        LinearLayoutManager managerPlaylists = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mPlaylistAdapter = new PlaylistListAdapter(null, getContext(), null, R.layout.playlist_item);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView_playlists);
        mRecyclerView.setLayoutManager(managerPlaylists);
        mRecyclerView.setAdapter(mPlaylistAdapter);
    }

    private void getData() {
        MeManager.getInstance(getActivity()).getMyPlaylists(this);
        mPlaylist = new ArrayList<>();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   FragmentCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onChangeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   FragmentCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void updateTrack(ArrayList<Track> mTracks, int index) {

    }
    @Override
    public void myPlaylistsReceived(List<Playlist> playlists) {
        mPlaylist = (ArrayList) playlists;
        PlaylistListAdapter adapter = new PlaylistListAdapter(mPlaylist, getContext(), this, R.layout.playlist_item);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void playlistsFollowingReceived(List<Playlist> playlists) {

    }
    @Override
    public void myTracksReceived(List<Track> tracks) {

    }
    @Override
    public void tracksLikedReceived(List<Track> tracks) {

    }
    @Override
    public void noPlaylistsReceived(Throwable throwable) {

    }
    @Override
    public void noTracksReceived(Throwable throwable) {

    }
    @Override
    public void onFailure(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistAdapterCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onPlaylistClick(Playlist playlist) {
        Fragment fragment = null;
        fragment = PlaylistFragment.getInstance(playlist);
        onChangeFragment(fragment);
    }

    @Override
    public void onPlaylistClick(int index) {

    }
}