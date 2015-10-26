package wxc.android.loggerwriterdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import wxc.android.logwriter.L;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        L.Builder builder = new L.Builder();
        L.set(builder
                .addLogCat()
                .addLocalLog(this)
                .logCrash(this)
                .create());
    }
}
