package salle.android.projects.registertest.restapi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;

public interface MeService {
    @GET("me/playlists")
    Call<List<Playlist>> callMyPlaylists();
    @GET("me/playlists/following")
    Call<List<Playlist>> callPlaylistsFollowing();
    @GET("me/tracks")
    Call<List<Track>> callMyTrakcs();
    @GET("me/tracks/liked")
    Call<List<Track>> callTracksLiked();
}
