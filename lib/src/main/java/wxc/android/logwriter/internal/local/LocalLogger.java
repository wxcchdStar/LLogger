package wxc.android.logwriter.internal.local;

import org.slf4j.LoggerFactory;

import wxc.android.logwriter.internal.ILogger;

public class LocalLogger implements ILogger {

    private org.slf4j.Logger mLog = LoggerFactory.getLogger(LocalLogger.class);

    @Override
    public void v(String tag, String msg) {
        mLog.trace(tag + "\t" + msg);
    }

    @Override
    public void i(String tag, String msg) {
        mLog.info(tag + "\t" + msg);
    }

    @Override
    public void d(String tag, String msg) {
        mLog.debug(tag + "\t" + msg);
    }

    @Override
    public void w(String tag, String msg) {
        mLog.warn(tag + "\t" + msg);
    }

    @Override
    public void e(String tag, String msg) {
        mLog.error(tag + "\t" + msg);
    }

}
