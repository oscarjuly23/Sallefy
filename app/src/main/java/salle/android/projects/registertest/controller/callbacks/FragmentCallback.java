package salle.android.projects.registertest.controller.callbacks;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import salle.android.projects.registertest.model.Track;

public interface FragmentCallback {
    void onChangeFragment(Fragment fragment);
    void updateTrack(ArrayList<Track> mTracks, int index);
}