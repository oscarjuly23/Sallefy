package salle.android.projects.registertest.restapi.manager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.utils.Constants;
import salle.android.projects.registertest.utils.Session;


public abstract class BaseManager {
    protected Retrofit retrofit;

    public BaseManager(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(500, TimeUnit.SECONDS)
                .readTimeout(500, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                //String bearerToken = UserLoginManager.getInstance().getBearerToken();
                UserToken bearerToken = Session.getInstance().getUserToken();
                //QUITAR ESTO

                if(bearerToken == null){
                    Log.d("interceptor","Realizando login");
                    return chain.proceed(original);
                }

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + bearerToken.getIdToken()); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }
}