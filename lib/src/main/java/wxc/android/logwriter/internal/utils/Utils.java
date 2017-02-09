package wxc.android.logwriter.internal.utils;

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

}
