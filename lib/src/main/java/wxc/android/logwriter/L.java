package wxc.android.logwriter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import wxc.android.logwriter.internal.IPrinter;
import wxc.android.logwriter.internal.Level;
import wxc.android.logwriter.internal.handler.CrashExceptionHandler;
import wxc.android.logwriter.internal.local.LocalConfigurator;
import wxc.android.logwriter.internal.local.LocalPrinter;
import wxc.android.logwriter.internal.logcat.LogcatPrinter;

public final class L {

    public static final String TAG = "LLogger";

    private static volatile L sLogger;

    private List<IPrinter> mPrinters;

    private L() {
    }

    private static L get() {
        if (sLogger == null) {
            synchronized (L.class) {
                if (sLogger == null) {
                    sLogger = new Builder().addLogCat().create();
                }
            }
        }
        return sLogger;
    }

    public static void set(L logger) {
        if (sLogger != null) {
            throw new IllegalStateException("L already has an instance.");
        }
        sLogger = logger;
    }

    public static void v(Object... msg) {
        get().log(Level.VERBOSE, msg);
    }

    public static void d(Object... msg) {
        get().log(Level.DEBUG, msg);
    }

    public static void i(Object... msg) {
        get().log(Level.INFO, msg);
    }

    public static void w(Object... msg) {
        get().log(Level.WARN, msg);
    }

    public static void e(Object... msg) {
        get().log(Level.ERROR, msg);
    }

    private void log(Level level, Object... msg) {
        for (IPrinter printer : mPrinters) {
            printer.print(level, msg);
        }
    }

    public static class Builder {
        private List<IPrinter> mPrinters = new ArrayList<>();

        public Builder addLogCat() {
            mPrinters.add(new LogcatPrinter());
            return this;
        }

        public Builder addLocalLog(Context ctx) {
            LocalConfigurator.openWrite(ctx);
            mPrinters.add(new LocalPrinter());
            return this;
        }

        public Builder logCrash(Context ctx) {
            CrashExceptionHandler.init(ctx);
            return this;
        }

        public L create() {
            L logger = new L();
            logger.mPrinters = mPrinters;
            return logger;
        }
    }

}
