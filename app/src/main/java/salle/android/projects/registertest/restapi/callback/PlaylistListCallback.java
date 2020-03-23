package salle.android.projects.registertest.restapi.callback;

import salle.android.projects.registertest.model.Playlist;

public interface PlaylistListCallback {
    void onPlaylistSelected(Playlist playlist);
    void onTrackSelected(int index);
}
