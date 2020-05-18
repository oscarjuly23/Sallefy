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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.model.Track;

public class TrackFragment extends Fragment {
    public static final String TAG = TrackFragment.class.getName();

    private ImageView imageView;
    private TextView tvName;
    private TextView tvArtist;

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
}
