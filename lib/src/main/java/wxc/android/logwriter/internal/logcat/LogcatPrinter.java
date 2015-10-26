package wxc.android.logwriter.internal.logcat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wxc.android.logwriter.internal.BaseBoxPrinter;
import wxc.android.logwriter.internal.Level;

public class LogcatPrinter extends BaseBoxPrinter {

    public LogcatPrinter() {
        mLogger = new AndroidLogger();
    }

    @Override
    protected synchronized void printMessages(Level level, Object... msg) {
        String threadInfo = Thread.currentThread().toString();
        String stackTrace = getStackTrace();

        List<Object> mergeList = new ArrayList<>();
        mergeList.add(threadInfo);
        mergeList.add(stackTrace);
        mergeList.addAll(msg == null ? new ArrayList<>() : Arrays.asList(msg));

        super.printMessages(level, mergeList.toArray());
    }

}
