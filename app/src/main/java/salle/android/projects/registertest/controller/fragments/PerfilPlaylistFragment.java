package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.MeCallback;

public class PerfilPlaylistFragment extends Fragment implements FragmentCallback, MeCallback {

    public static final String TAG = LibraryPlaylistFragment.class.getName();

    private TextView tvTitle;
    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylist;

    public PerfilPlaylistFragment() {

    }

    public static PerfilPlaylistFragment getInstance() { return new PerfilPlaylistFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_playlist, container, false);
        initViews(v);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {
        /*mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView_playlists);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);*/
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

    @Override
    public void updateTrack(ArrayList<Track> mTracks, int index) {

    }
    @Override
    public void myPlaylistsReceived(List<Playlist> playlists) {

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