package wxc.android.loggerwriterdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import wxc.android.logwriter.Logger;

/**
 * Created by Chenhd on 2015/4/8.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(this);
        LeakCanary.install(this);
    }
}
