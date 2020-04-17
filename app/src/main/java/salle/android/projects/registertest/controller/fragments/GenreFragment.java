package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.GenreCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.GenreManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class GenreFragment extends Fragment implements GenreCallback, TrackListCallback, TrackCallback {
    public static final String TAG = GenreFragment.class.getName();

    private TextView tvGenre;
    private RecyclerView mTracksView;
    private Genre genre;

    private ArrayList<Track> mTracks;
    private FragmentCallback callback;

    public GenreFragment(Genre genre){
        this.genre=genre;
    }

    public static GenreFragment getInstance(Genre genre) {
        return new GenreFragment(genre);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_advanced_list, container, false);
        initViews(v);
        getData();
        return v;
    }

    private void initViews(View v){
        mTracksView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter trackListAdapter = new TrackListAdapter( this, getActivity(), null);
        mTracksView.setLayoutManager(manager);
        mTracksView.setAdapter(trackListAdapter);

        tvGenre = v.findViewById(R.id.textView);
        tvGenre.setText(genre.getName());
    }

    private void getData() {
        GenreManager.getInstance(getActivity()).getTracksByGenre(genre.getId(),this);
        mTracks = new ArrayList<>();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {

    }
    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), mTracks);
        mTracksView.setAdapter(adapter);
    }
    @Override
    public void onFailure(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onTrackSelected(int index) {
        callback.updateTrack(mTracks, index);
    }
    @Override
    public void onTrackLike(int index) {
        TrackManager.getInstance(getContext()).likeTrack(mTracks.get(index).getId(), this);
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