package salle.android.projects.registertest.restapi.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import salle.android.projects.registertest.model.Track;

public interface TrackService {
    @GET("tracks")
    Call<List<Track>> getAllTracks();
    @GET("me/tracks")
     Call<List<Track>> getOwnTracks();
    @GET("users/{login}/tracks")
    Call<List<Track>> getUserTracks(@Path("login") String login);
    @POST("tracks")
    Call<ResponseBody> createTrack(@Body Track track);
    @PUT("tracks/{id}/like")
    Call<Track> setTrackLike(@Path("id") Integer trackId);
    @GET("tracks/{id}")
    Call<Track> getTracksFromId(@Path("id") Integer trackId);
}