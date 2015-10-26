package wxc.android.logwriter.internal.handler;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

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
        L.e("Crash:" + getCrashInfo(ex));

        OomExceptionHandler.uncaughtException(mContext, thread, ex);

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // TODO: 自定义提示方式
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * {@link Log#getStackTraceString(Throwable)}
     */
    private static String getCrashInfo(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
