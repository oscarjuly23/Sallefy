package salle.android.projects.registertest.controller.callbacks;

import androidx.fragment.app.Fragment;

public interface TrackListCallback {
    void onTrackSelected(Fragment fragment);
    void onTrackSelected(int index);
    void onTrackLike(int index);
}