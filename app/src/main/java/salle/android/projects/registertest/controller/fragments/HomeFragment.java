package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.GenresAdapter;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.GenreAdapterCallback;
import salle.android.projects.registertest.controller.callbacks.PlaylistAdapterCallback;
import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.GenreCallback;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.manager.GenreManager;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;

public class HomeFragment extends Fragment implements PlaylistCallback, PlaylistAdapterCallback, GenreCallback, FragmentCallback, GenreAdapterCallback {

    public static final String TAG = HomeFragment.class.getName();

    private RecyclerView mPlaylistsView;
    private PlaylistListAdapter mPlaylistAdapter;

    private RecyclerView mGenresView;
    private GenresAdapter mGenresAdapter;


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
        LinearLayoutManager managerPlaylists = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mPlaylistAdapter = new PlaylistListAdapter(null, getContext(), null, R.layout.item_playlist_short);
        mPlaylistsView = (RecyclerView) v.findViewById(R.id.home_playlists_recyclerview);
        mPlaylistsView.setLayoutManager(managerPlaylists);
        mPlaylistsView.setAdapter(mPlaylistAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, RecyclerView.HORIZONTAL, false);
        mGenresAdapter = new GenresAdapter(null, getContext(), null, R.layout.item_genre);
        mGenresView = (RecyclerView) v.findViewById(R.id.home_genres_recyclerview);
        mGenresView.setLayoutManager(gridLayoutManager);
        mGenresView.setAdapter(mGenresAdapter);
    }

    private void getData() {
        PlaylistManager.getInstance(getContext()).getAllPlaylist(this);
        GenreManager.getInstance(getContext()).getAllGenres(this);
    }

    @Override
    public void onPause() {
        super.onPause();
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

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {
        mPlaylistAdapter = new PlaylistListAdapter(playlists, getContext(), this, R.layout.item_playlist_short);
        mPlaylistsView.setAdapter(mPlaylistAdapter);
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
    public void onFollowSucces(Playlist playlist) {

    }

    @Override
    public void getIsFollowed(Playlist playlist) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {
        mGenresAdapter = new GenresAdapter(genres, getContext(), this, R.id.item_genre_btn);
        mGenresView.setAdapter(mGenresAdapter);
    }

    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {

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
     *   *   *   *   *   *   *   *   GenreAdapterCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onClickGenre(Genre genre) {
        Fragment fragment = null;
        fragment = GenreFragment.getInstance(genre);
        onChangeFragment(fragment);
    }

}
