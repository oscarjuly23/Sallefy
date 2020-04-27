package salle.android.projects.registertest.restapi.manager;

import android.content.Context;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import salle.android.projects.registertest.model.Search;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.restapi.callback.SearchCallback;
import salle.android.projects.registertest.restapi.service.SearchService;
import salle.android.projects.registertest.utils.Constants;
import salle.android.projects.registertest.utils.Session;

public class SearchManager extends BaseManager{

    private static final String TAG = "SearchManager";
    private Context mContext;
    private static SearchManager searchManager;
    private Retrofit mRetrofit;
    private SearchService mService;

    public static SearchManager getInstance (Context context) {
        if (searchManager == null) {
            searchManager = new SearchManager(context);
        }
        return searchManager;
    }

    public SearchManager(Context context) {
        mContext = context;
        mService = retrofit.create(SearchService.class);
    }

    /********************   SHOW SEARCH    ********************/
    public synchronized void getMySearch(String keyword,final SearchCallback searchCallback) {
              Call<Search> call = mService.callSearch(keyword);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    searchCallback.getSearchs(response.body());
                } else {
                    Log.d(TAG, "Error " + code);
                    searchCallback.getSearchsFailed(new Throwable("ERROR " + code +", " + response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                searchCallback.onFailure(t);
            }
        });
    }
}
