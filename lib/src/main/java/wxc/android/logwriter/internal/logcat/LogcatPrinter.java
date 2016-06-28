package wxc.android.logwriter.internal.logcat;

import wxc.android.logwriter.internal.BaseBoxPrinter;

public class LogcatPrinter extends BaseBoxPrinter {

    public LogcatPrinter() {
        mLogger = new AndroidLogger();
    }

}
