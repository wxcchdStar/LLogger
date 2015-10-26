package wxc.android.logwriter.internal.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wxc.android.logwriter.internal.BaseBoxPrinter;
import wxc.android.logwriter.internal.Level;

public class LocalPrinter extends BaseBoxPrinter {

    public LocalPrinter() {
        mLogger = new LocalLogger();
    }

    @Override
    protected void printMessages(Level level, Object... msg) {
        String stackTrace = getStackTrace();

        List<Object> mergeList = new ArrayList<>();
        mergeList.add(stackTrace);
        mergeList.addAll(msg == null ? new ArrayList<>() : Arrays.asList(msg));

        super.printMessages(level, mergeList.toArray());
    }
}
