package salle.android.projects.registertest.restapi.callback;

import java.util.List;

import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;

public interface MeCallback extends FailureCallback {
    void myPlaylistsReceived(List<Playlist> playlists);
    void playlistsFollowingReceived(List<Playlist> playlists);
    void myTracksReceived(List<Track> tracks);
    void tracksLikedReceived(List<Track> tracks);
    void noPlaylistsReceived(Throwable throwable);
    void noTracksReceived(Throwable throwable);
}
