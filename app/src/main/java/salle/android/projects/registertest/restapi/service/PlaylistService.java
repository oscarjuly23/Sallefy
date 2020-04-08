package salle.android.projects.registertest.restapi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import salle.android.projects.registertest.model.Playlist;

public interface PlaylistService {
    @GET("playlists")
    //@GET("me/playlists")
    Call<List<Playlist>> callPlaylist(@Header("Authorization") String token);
    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Playlist playlist, @Header("Authorization") String token);
    @PUT("playlists")
    Call<Playlist> addTrackToPlaylist(@Body Playlist playlist, @Header("Authorization") String token);
    @GET("playlists/{id}/follow")
    Call<Playlist> IsFollowed(@Path("id") Integer id, @Header("Authorization") String token);
    @PUT("playlists/{id}/follow")
    Call<Playlist> followPlaylist(@Path("id") Integer id, @Header("Authorization") String token);
}