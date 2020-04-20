package salle.android.projects.registertest.restapi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Search;

public interface SearchService {
    @GET("search")
    Call<Search> callSearch(@Query("keyword") String keyword,@Header("Authorization") String token);
}
