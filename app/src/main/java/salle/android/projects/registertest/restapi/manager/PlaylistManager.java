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

public class PlaylistManager extends BaseManager {
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
        mService = retrofit.create(PlaylistService.class);
    }

    /********************   SHOW PLAYLIST    ********************/

    public synchronized void getAllPlaylist(final PlaylistCallback playlistCallback) {
        Call<List<Playlist>> call = mService.callPlaylist();
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
        Call<Playlist> call = mService.createPlaylist(playlist);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    callback.onCreateSuccess(response.body());
                } else {
                    Log.d(TAG, "Error " + code);
                    callback.onCreateFailed(new Throwable("ERROR " + code +", " + response.errorBody().toString()));
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
        Call<Playlist> call = mService.addTrackToPlaylist(playlist);
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
        Call<Playlist> call = mService.IsFollowed(playlistID);
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
        Call<Playlist> call = mService.followPlaylist(playlistID);
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

    /********************   GET PLAYLIST   ********************/
    public synchronized void getPlaylist (int playlistID, final PlaylistCallback playlistCallback) {
        Call<Playlist> call = mService.getPlaylistFromID(playlistID);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful()) {
                    playlistCallback.getPlaylist(response.body());
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