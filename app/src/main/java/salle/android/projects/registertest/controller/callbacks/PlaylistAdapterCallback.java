package salle.android.projects.registertest.controller.callbacks;

import salle.android.projects.registertest.model.Playlist;

public interface PlaylistAdapterCallback {

    void onPlaylistClick(Playlist playlist);
    void onPlaylistClick(int index);

}
