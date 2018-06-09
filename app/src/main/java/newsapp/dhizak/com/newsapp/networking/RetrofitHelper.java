package newsapp.dhizak.com.newsapp.networking;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import newsapp.dhizak.com.newsapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Dražen Hižak on 08/06/2018.
 */

public class RetrofitHelper {

    private static final String TAG ="RetroFitHelper";
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if(BuildConfig.ENABLE_LOGGING){
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
                builder.addNetworkInterceptor(new StethoInterceptor());
            }
            builder.readTimeout(1, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);
            OkHttpClient okClient = builder.build();

            instance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOST)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonConverterTypeFactory()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return instance ;
}
}
