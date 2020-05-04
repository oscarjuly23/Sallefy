package salle.android.projects.registertest.controller.callbacks;

import android.view.View;


public interface TrackListCallback {
    void onTrackSelected(View v);
    void onTrackSelected(int index);
    void onTrackLike(int index);
}