package wxc.android.logwriter;

import android.content.Context;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Chenhd on 2015/4/8.
 */
public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static CrashExceptionHandler sInstance;

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    public static CrashExceptionHandler getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new CrashExceptionHandler(ctx);
        }
        return sInstance;
    }

    public CrashExceptionHandler(Context ctx) {
        mContext = ctx;
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger.e("Crash", "App crashed at " + thread + ": ");
        if (ex != null) {
            String crashInfo = getCrashInfo(ex);
            Logger.e("Crash", crashInfo);
        }

        OomExceptionHandler.uncaughtException(mContext, thread, ex);

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // TODO: 自定义提示方式
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    private static String getCrashInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }
}
