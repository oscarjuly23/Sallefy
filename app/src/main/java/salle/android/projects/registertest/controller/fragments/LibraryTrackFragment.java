package salle.android.projects.registertest.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public class LibraryTrackFragment extends Fragment implements FragmentCallback, MeCallback, TrackListCallback, TrackCallback {

    public static final String TAG = LibraryTrackFragment.class.getName();

    private Button btnAddSong;
    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;
    private int index = 0;
    private FragmentCallback callback;

    public LibraryTrackFragment() {

    }

    public static LibraryTrackFragment getInstance() {
        return new LibraryTrackFragment();
    }

    public void showPopup(View v, int style, Fragment fragment) {
        Context wraper = new ContextThemeWrapper(getContext(),style);
        PopupMenu popupMenu = new PopupMenu(wraper, v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_toPlaylist:
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.share:
                        String url ="http://sallefy.eu-west-3.elasticbeanstalk.com/track/"+index;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("tet/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, url);
                        startActivity(Intent.createChooser(intent, "Share with: "));
                        break;
                }
                return true;
            }
        });
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
        View v = inflater.inflate(R.layout.fragment_library_canciones, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View v) {
        btnAddSong = (Button) v.findViewById(R.id.add_song_action);
        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = CreateSongFragment.getInstance();
                onChangeFragment(fragment);
            }
        });
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView_tracks);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), null, this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        MeManager.getInstance(getActivity()).getMyTracks(this);
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
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), mTracks, LibraryFragment.getInstance());
        mRecyclerView.setAdapter(adapter);
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
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(View v, Fragment fragment, int idTrack) {
        index = idTrack;
        showPopup(v, R.style.MenuPopup, fragment);
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

    @Override
    public void getTrack(Track track) {

    }
}