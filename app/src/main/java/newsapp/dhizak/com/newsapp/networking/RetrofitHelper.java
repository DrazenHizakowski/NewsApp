package newsapp.dhizak.com.newsapp.networking;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import newsapp.dhizak.com.newsapp.BuildConfig;
import newsapp.dhizak.com.newsapp.networking.util.GsonDateTypeAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class RetrofitHelper {

    private static final String TAG = "RetroFitHelper";
    private static Retrofit retrofitInstance;

    public static Retrofit getInstance() {
        if (retrofitInstance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.ENABLE_LOGGING) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
                builder.addNetworkInterceptor(new StethoInterceptor());
            }
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            OkHttpClient okClient = builder.build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonConverterTypeFactory()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    private static Gson GsonConverterTypeFactory() {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Date.class, new GsonDateTypeAdapter());
        return gson.create();
    }
}
