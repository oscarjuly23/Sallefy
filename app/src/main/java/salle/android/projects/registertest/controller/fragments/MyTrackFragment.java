package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.model.Track;

public class MyTrackFragment extends Fragment implements FragmentCallback {

    public static final String TAG = MyTrackFragment.class.getName();

    private Button btnAddSong;
    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;

    public MyTrackFragment() {

    }

    public static MyTrackFragment getInstance() {
        return new MyTrackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_library_canciones, container, false);
        initViews(v);
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
                fragment = AddSongFragment.getInstance();
                onChangeFragment(fragment);
            }

        /*mRecyclerView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView_playlists);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);*/
        });
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
}
