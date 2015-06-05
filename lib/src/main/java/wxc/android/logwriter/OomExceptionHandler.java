package wxc.android.logwriter;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;

import java.io.File;


public class OomExceptionHandler {

    private static final String FILENAME = "out-of-memory.hprof";

    public static void uncaughtException(Context ctx, Thread thread, Throwable ex) {
        if (containsOom(ex)) {
            File heapDumpFile = new File(getOOMFileDir(ctx), FILENAME);
            try {
                Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
            } catch (Throwable ignored) {
            }
        }
    }

    private static String getOOMFileDir(Context ctx) {
        String packageName = ctx.getPackageName();
        String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + packageName + File.separator;
        } else {
            filePath = ctx.getFilesDir() + File.separator + packageName + File.separator;
        }
        return filePath;
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
