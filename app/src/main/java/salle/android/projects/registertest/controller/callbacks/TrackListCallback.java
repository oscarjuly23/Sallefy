package salle.android.projects.registertest.controller.callbacks;

import salle.android.projects.registertest.model.Track;

public interface TrackListCallback {
    void onTrackSelected(Track track);
    void onTrackSelected(int index);
}
