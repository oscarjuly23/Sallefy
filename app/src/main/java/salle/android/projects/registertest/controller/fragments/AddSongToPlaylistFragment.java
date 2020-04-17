package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class AddSongToPlaylistFragment extends Fragment implements PlaylistAdapterCallback, MeCallback {

    public static final String TAG = AddSongToPlaylistFragment.class.getName();

    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylists;
    private int currentPlaylist = 0;
    private FragmentCallback callback;
    private Track track;

    public AddSongToPlaylistFragment(Track track){
        this.track = track;
    }

    public static AddSongToPlaylistFragment getInstance(Track track) {
        return new AddSongToPlaylistFragment(track);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_addsongtoplaylist, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        PlaylistListAdapter adapter = new PlaylistListAdapter(null, getContext(), null, R.layout.playlist_item);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        MeManager.getInstance(getActivity()).getMyPlaylists(this);
        mPlaylists = new ArrayList<>();
    }

    public int getIndex(){
        return currentPlaylist;
    }

    public ArrayList<Playlist> getmPlaylists(){
        return mPlaylists;
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistAdapterCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onPlaylistClick(Playlist playlist) {
        Toast.makeText(getContext(), "OK " , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistClick(int index) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   MeCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void myPlaylistsReceived(List<Playlist> playlists) {
        mPlaylists = (ArrayList) playlists;
        PlaylistListAdapter adapter = new PlaylistListAdapter(mPlaylists, getContext(), this, R.layout.playlist_item);
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
}
