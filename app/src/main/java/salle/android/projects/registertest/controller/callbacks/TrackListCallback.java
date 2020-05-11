package salle.android.projects.registertest.controller.callbacks;

import android.view.View;

import androidx.fragment.app.Fragment;


public interface TrackListCallback {
    void onTrackSelected(View v, Fragment fragment, int idTrack);
    void onTrackSelected(int index);
    void onTrackLike(int index);
}