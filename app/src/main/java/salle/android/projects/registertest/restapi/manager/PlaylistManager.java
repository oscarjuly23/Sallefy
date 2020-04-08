package salle.android.projects.registertest.restapi.manager;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.service.PlaylistService;
import salle.android.projects.registertest.utils.Constants;
import salle.android.projects.registertest.utils.Session;

public class PlaylistManager {
    private static final String TAG = "PlaylistManager";
    private Context mContext;
    private static PlaylistManager sPlaylistManager;
    private Retrofit mRetrofit;
    private PlaylistService mService;

    public static PlaylistManager getInstance (Context context) {
        if (sPlaylistManager == null) {
            sPlaylistManager = new PlaylistManager(context);
        }
        return sPlaylistManager;
    }

    public PlaylistManager(Context context) {
        mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(PlaylistService.class);
    }

    /********************   SHOW PLAYLIST    ********************/

    public synchronized void getAllPlaylist(final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mService.callPlaylist( "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistCallback.onShowPlaylist(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    playlistCallback.onShowPlaylistFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                playlistCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    /********************   NEW PLAYLIST    ********************/

    public synchronized void createPlaylist (Playlist playlist, final PlaylistCallback callback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<Playlist> call = mService.createPlaylist(playlist, "Bearer " +  userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    callback.onCreateSuccess(response.body());
                } else {
                    callback.onCreateFailed(new Throwable(response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    /********************   ADD SONG TO A PLAYLIST    ********************/

    public synchronized void addSong (Playlist playlist, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<Playlist> call = mService.addTrackToPlaylist(playlist, "Bearer " +  userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {

            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    playlistCallback.onUpdateSucces(response.body());
                } else {
                    try {
                        playlistCallback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                playlistCallback.onFailure(t);
            }
        });
    }

    /********************   CHECK IF PLAYLIST IS FOLLOWED    ********************/
    public synchronized void isFollowingPlaylist (int playlistID, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<Playlist> call = mService.IsFollowed(playlistID, "Bearer " +  userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {

            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    playlistCallback.getIsFollowed(response.body());
                } else {
                    try {
                        playlistCallback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                playlistCallback.onFailure(t);
            }
        });
    }


    /********************   FOLLOW PLAYLIST    ********************/
    public synchronized void followPlaylist (int playlistID, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<Playlist> call = mService.followPlaylist(playlistID, "Bearer " +  userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {

            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    playlistCallback.onFollowSucces(response.body());
                } else {
                    try {
                        playlistCallback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                playlistCallback.onFailure(t);
            }
        });
    }
}