package wxc.android.logwriter.internal.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import wxc.android.logwriter.L;

public class Utils {

    public static int computeStackOffset(StackTraceElement[] elements) {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != null) {
                    String className = elements[i].getClassName();
                    String methodName = elements[i].getMethodName();
                    if (L.class.getName().equals(className) && "log".equals(methodName)) {
                        return i + 2;
                    }
                }
            }
        }
        return -1;
    }

    public static String getLogDir(Context ctx) {
        String packageName = ctx.getPackageName();
        String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = ctx.getExternalFilesDir("log") + File.separator;
        } else {
            filePath = ctx.getFilesDir() + File.separator + packageName + File.separator;
        }
        return filePath;
    }
}
