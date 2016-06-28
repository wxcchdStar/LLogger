package wxc.android.logwriter.internal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wxc.android.logwriter.L;
import wxc.android.logwriter.internal.utils.Utils;

public abstract class BaseBoxPrinter implements IPrinter {

    protected ILogger mLogger;

    private int mStackOffset = -1;

    @Override
    public synchronized void print(Level level, Object... msg) {
        printMessages(level, msg);
    }

    protected void printMessages(Level level, Object... msg) {
        String threadInfo = Thread.currentThread().toString();
        String stackTrace = getStackTrace();
        String prefix = stackTrace + " " + threadInfo + " ";

        for (Object obj : msg) {
            printMsg(level, prefix, obj == null ? "null" : obj.toString());
        }
    }

    private void printMsg(Level level, String prefix, String msg) {
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObj = new JSONObject(msg);
                printLines(level, prefix, jsonObj.toString(4));
                return;
            } else if (msg.startsWith("[")) {
                JSONArray jsonArr = new JSONArray(msg);
                printLines(level, prefix, jsonArr.toString(4));
                return;
            }
        } catch (JSONException e) {
            // do nothing
        }
        printLines(level, prefix, msg);
    }

    protected void printLines(Level level, String prefix, String msg) {
        String[] lines = msg.split("\n");
        for (String line : lines) {
            log(level, prefix + line);
        }
    }

    private void log(Level level, String msg) {
        switch (level) {
            case VERBOSE:
                mLogger.v(L.TAG, msg);
                break;
            case DEBUG:
                mLogger.d(L.TAG, msg);
                break;
            case INFO:
                mLogger.i(L.TAG, msg);
                break;
            case WARN:
                mLogger.w(L.TAG, msg);
                break;
            case ERROR:
                mLogger.e(L.TAG, msg);
                break;
        }
    }

    protected String getStackTrace() {
        // new Throwable().getStackTrace(), 差2
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements != null) {
            if (mStackOffset < 0) {
                mStackOffset = Utils.computeStackOffset(elements);
            }
            return "(" + elements[mStackOffset].getFileName()
                    + ":" + elements[mStackOffset].getLineNumber() + ")";
        }
        return null;
    }

}
