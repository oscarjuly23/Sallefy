package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class PlaylistFragment extends Fragment implements TrackListCallback, PlaylistCallback {
    public static final String TAG = PlaylistFragment.class.getName();

    private static final String FOLLOW_VIEW = "Siguiendo";
    private static final String UNFOLLOW_VIEW = "Seguir";

    private ImageView imageView;
    private TextView tvName;
    private Button btnFollow;

    private RecyclerView mRecyclerView;

    private Playlist playlist;
    private FragmentCallback callback;

    public PlaylistFragment(Playlist p){
        this.playlist = p;
    }

    public static PlaylistFragment getInstance(Playlist p) {
        return new PlaylistFragment(p);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_playlist, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v){
        mRecyclerView = v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), (ArrayList<Track>) playlist.getTracks());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        imageView = v.findViewById(R.id.playlist_photo);
        if (playlist.getThumbnail() != null){
            Glide.with(this).load(playlist.getThumbnail()).into(imageView);
        }

        tvName = v.findViewById(R.id.name_playlist);
        tvName.setText(playlist.getName());

        PlaylistManager.getInstance(getContext()).isFollowingPlaylist(playlist.getId(),this);

        btnFollow = v.findViewById(R.id.follow_playlist);
        btnFollow.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (btnFollow.getTag().equals(UNFOLLOW_VIEW)) {
                followPlaylist();
                btnFollow.setTag(FOLLOW_VIEW);
                btnFollow.setText(FOLLOW_VIEW);
            } else {
                unFollowPlaylist();
                btnFollow.setTag(UNFOLLOW_VIEW);
                btnFollow.setText(UNFOLLOW_VIEW);
            }
        }
    });
    }

    private void followPlaylist() {
        PlaylistManager.getInstance(getContext()).followPlaylist(playlist.getId(),this);
    }

    private void unFollowPlaylist() {
        PlaylistManager.getInstance(getContext()).followPlaylist(playlist.getId(),this);
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(Track track) {

    }
    @Override
    public void onTrackSelected(int index) {
        callback.updateTrack((ArrayList<Track>) playlist.getTracks(), index);
    }

    @Override
    public void onTrackLike(int index) {

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
        if (playlist.isFollowed()) {
            btnFollow.setTag(FOLLOW_VIEW);
            btnFollow.setText(FOLLOW_VIEW);
        } else {
            btnFollow.setTag(UNFOLLOW_VIEW);
            btnFollow.setText(UNFOLLOW_VIEW);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}