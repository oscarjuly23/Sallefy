package salle.android.projects.registertest.controller.callbacks;

import salle.android.projects.registertest.model.Playlist;

public interface PlaylistListCallback {
    void onPlaylistSelected(Playlist playlist);
    void onTrackSelected(int index);
}
