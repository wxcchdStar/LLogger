package wxc.android.logwriter.internal.local;

import wxc.android.logwriter.internal.BaseBoxPrinter;

public class LocalPrinter extends BaseBoxPrinter {

    public LocalPrinter() {
        mLogger = new LocalLogger();
    }

}
