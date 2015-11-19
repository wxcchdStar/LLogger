package wxc.android.logwriter.internal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wxc.android.logwriter.L;
import wxc.android.logwriter.internal.utils.Utils;

public abstract class BaseBoxPrinter implements IPrinter {

    /**
     * Drawing toolbox
     */
    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char MIDDLE_CORNER = '╟';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    protected ILogger mLogger;

    private int mStackOffset = -1;

    @Override
    public synchronized void print(Level level, Object... msg) {
        printTopBorder(level);
        printMessages(level, msg);
        printBottomBorder(level);
    }

    protected void printTopBorder(Level level) {
        log(level, TOP_BORDER);
    }

    protected void printMessages(Level level, Object... msg) {
        for (int i = 0; i < msg.length; i++) {
            printMsg(level, msg[i] == null ? "null" : msg[i].toString());

            if (i != msg.length - 1) {
                printMiddleBorder(level);
            }
        }
    }

    protected void printMiddleBorder(Level level) {
        log(level, MIDDLE_BORDER);
    }

    protected void printBottomBorder(Level level) {
        log(level, BOTTOM_BORDER);
    }

    private void printMsg(Level level, String msg) {
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObj = new JSONObject(msg);
                printLines(level, jsonObj.toString(4));
                return;
            } else if (msg.startsWith("[")) {
                JSONArray jsonArr = new JSONArray(msg);
                printLines(level, jsonArr.toString(4));
                return;
            }
        } catch (JSONException e) {
            // do nothing
        }
       printLines(level, msg);
    }

    protected void printLines(Level level, String msg) {
        String[] lines = msg.split("\n");
        for (String line : lines) {
            log(level, HORIZONTAL_DOUBLE_LINE + line);
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
            return elements[mStackOffset].toString();
        }
        return null;
    }

}
