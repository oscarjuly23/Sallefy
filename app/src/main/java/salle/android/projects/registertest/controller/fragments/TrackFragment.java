package salle.android.projects.registertest.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class TrackFragment extends Fragment implements TrackCallback{
    public static final String TAG = TrackFragment.class.getName();

    private ImageView imageView;
    private TextView tvName;
    private TextView tvArtist;
    private LikeButton likeButton;

    private Track track;
    private FragmentCallback callback;

    public TrackFragment(Track t){
        this.track = t;
    }

    public static TrackFragment getInstance(Track t) {
        return new TrackFragment(t);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_view_track, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        likeButton = (LikeButton) v.findViewById(R.id.heart_button);
        if (track.isLiked()){
            likeButton.setLiked(true);
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like();
                likeButton.onClickAnimation(v);
            }
        });

        imageView = v.findViewById(R.id.track_photo);
        if (track.getThumbnail() != null) {
            Glide.with(this).load(track.getThumbnail()).into(imageView);
        }

        tvName = v.findViewById(R.id.track_name);
        tvName.setText(track.getName());

        tvArtist = v.findViewById(R.id.track_artist);
        tvArtist.setText(track.getUser().getLogin());

        ArrayList<Track> mTracks = new ArrayList<>();
        mTracks.add(track);
        callback.updateTrack(mTracks, 0);
    }

    private void like() {
        TrackManager.getInstance(getContext()).likeTrack(track.getId(),this);
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

    @Override
    public void onFailure(Throwable throwable) {

    }
}
