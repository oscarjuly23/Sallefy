package salle.android.projects.registertest.restapi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import salle.android.projects.registertest.model.UserLogin;
import salle.android.projects.registertest.model.UserToken;

public interface UserTokenService {
    @POST("authenticate")
    Call<UserToken> loginUser(@Body UserLogin login);
}