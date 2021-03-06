package salle.android.projects.registertest.restapi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Track;

public interface GenreService {
    @GET("genres/{id}")
    Call<Genre> getGenreById(@Path("id") Integer id, @Header("Authorization") String token);
    @GET("genres")
    Call<List<Genre>> getAllGenres();
    @GET("genres/{id}/tracks")
    Call<List<Track>> getTracksByGenre(@Path("id") Integer id);
}