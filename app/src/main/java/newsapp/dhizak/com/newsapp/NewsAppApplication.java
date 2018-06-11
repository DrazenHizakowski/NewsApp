package newsapp.dhizak.com.newsapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Dražen Hižak on 10/06/2018.
 */

public class NewsAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.ENABLE_LOGGING){
            Stetho.initializeWithDefaults(this);
        }
    }
}
