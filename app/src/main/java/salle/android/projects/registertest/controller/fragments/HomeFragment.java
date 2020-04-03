package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.callbacks.PlaylistAdapterCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class HomeFragment extends Fragment implements PlaylistCallback, PlaylistAdapterCallback {


    public static final String TAG = HomeFragment.class.getName();
    private RecyclerView mRecyclerView;
    private PlaylistListAdapter mAdapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void initViews(View v) {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new PlaylistListAdapter(null, getContext(), this, R.layout.playlist_item);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_recyclerview);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        PlaylistManager.getInstance(getContext()).getAllPlaylist(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onPlaylistClick(Playlist playlist) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        mAdapter = new PlaylistListAdapter(playlists, getContext(), this, R.layout.playlist_item);
        mRecyclerView.setAdapter(mAdapter);
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

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
    }
}
