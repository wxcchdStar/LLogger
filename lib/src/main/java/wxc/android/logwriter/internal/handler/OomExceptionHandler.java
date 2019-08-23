package wxc.android.logwriter.internal.handler;

import android.content.Context;
import android.os.Debug;

import java.io.File;

import wxc.android.logwriter.internal.utils.Utils;


public class OomExceptionHandler {

    private static final String FILENAME = "out-of-memory.hprof";

    public static void uncaughtException(Context ctx, Thread thread, Throwable ex) {
        if (containsOom(ex)) {
            File heapDumpFile = new File(Utils.getLogDir(ctx), FILENAME);
            try {
                Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
            } catch (Throwable ignored) {
            }
        }
    }

    private static boolean containsOom(Throwable ex) {
        if (ex instanceof OutOfMemoryError) {
            return true;
        }
        while ((ex = ex.getCause()) != null) {
            if (ex instanceof OutOfMemoryError) {
                return true;
            }
        }
        return false;
    }
}
