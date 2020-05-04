package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.MeCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.MeManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class PerfilTrackFragment extends Fragment implements FragmentCallback, MeCallback, TrackListCallback, TrackCallback {

    public static final String TAG = PerfilTrackFragment.class.getName();

    private TextView tvTitle;
    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;
    private FragmentCallback callback;

    public PerfilTrackFragment() {
    }

    public static PerfilTrackFragment getInstance() {
        return new PerfilTrackFragment();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_canciones, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView_tracks);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), null, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        MeManager.getInstance(getActivity()).getMyTracksLiked(this);
        mTracks = new ArrayList<>();
    }

    public int getIndex(){
        return currentTrack;
    }

    public ArrayList<Track> getmTracks(){
        return mTracks;
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

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   MeCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

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
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), mTracks, PerfilFragment.getInstance());
        mRecyclerView.setAdapter(adapter);
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
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(View v) {
        showPopup(v);
    }
    @Override
    public void onTrackSelected(int index) {
        callback.updateTrack(mTracks, index);
    }
    @Override
    public void onTrackLike(int index) {
        TrackManager.getInstance(getContext()).likeTrack(mTracks.get(index).getId(),this);
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

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
    @Override
    public void onCreateTrack() {

    }
    @Override
    public void onLikeSuccess(Track track) {

    }
}