package salle.android.projects.registertest.restapi.manager;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.service.TrackService;
import salle.android.projects.registertest.utils.Constants;
import salle.android.projects.registertest.utils.Session;

public class TrackManager extends BaseManager {

    private static final String TAG = "TrackManager";
    private Context mContext;
    private static TrackManager sTrackManager;
    private Retrofit mRetrofit;
    private TrackService mTrackService;


    public static TrackManager getInstance (Context context) {
        if (sTrackManager == null) {
            sTrackManager = new TrackManager(context);
        }

        return sTrackManager;
    }

    public TrackManager(Context context) {
        mContext = context;
        mTrackService = retrofit.create(TrackService.class);
    }

    public synchronized void createTrack(Track track, final TrackCallback trackCallback) {
        Call<ResponseBody> call = mTrackService.createTrack(track);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    trackCallback.onCreateTrack();
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getAllTracks(final TrackCallback trackCallback) {
            Call<List<Track>> call = mTrackService.getAllTracks();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getUserTracks(String login, final TrackCallback trackCallback) {
              Call<List<Track>> call = mTrackService.getUserTracks(login);
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onUserTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getOwnTracks(final TrackCallback trackCallback) {
        Call<List<Track>> call = mTrackService.getOwnTracks();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    trackCallback.onPersonalTracksReceived((ArrayList<Track>) response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void likeTrack (int trackID, final TrackCallback trackCallback) {
        Call<Track> call = mTrackService.setTrackLike(trackID);
        call.enqueue(new Callback<Track>() {

            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    trackCallback.onLikeSuccess(response.body());
                } else {
                    try {
                        trackCallback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                trackCallback.onFailure(t);
            }
        });
    }
}