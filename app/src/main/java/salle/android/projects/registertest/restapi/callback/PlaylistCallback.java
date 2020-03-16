package salle.android.projects.registertest.restapi.callback;

import java.util.List;

import salle.android.projects.registertest.model.Playlist;

public interface PlaylistCallback extends FailureCallback {
    void onShowPlaylist(List<Playlist> playlists);
    void onShowPlaylistFailure(Throwable throwable);

    void onCreateSuccess(Playlist playlist);
    void OnCreateFailed(Throwable throwable);

    void onUpdateSucces(Playlist playlist);
}