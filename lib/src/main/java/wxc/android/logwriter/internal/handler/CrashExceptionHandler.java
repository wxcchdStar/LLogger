package wxc.android.logwriter.internal.handler;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import wxc.android.logwriter.L;

public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    public CrashExceptionHandler(Context ctx) {
        mContext = ctx.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static void init(Context ctx) {
        new CrashExceptionHandler(ctx);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        L.e("Crash:" + Log.getStackTraceString(ex));

        OomExceptionHandler.uncaughtException(mContext, thread, ex);

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // TODO: 自定义提示方式
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

}
