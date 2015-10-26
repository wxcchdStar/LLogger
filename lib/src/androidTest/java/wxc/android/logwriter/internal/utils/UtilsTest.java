package wxc.android.logwriter.internal.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testComputeStackOffset() {
        Assert.assertEquals(-1, Utils.computeStackOffset(null));
        Assert.assertEquals(-1, Utils.computeStackOffset(new StackTraceElement[2]));
        Assert.assertEquals(-1, Utils.computeStackOffset(Thread.currentThread().getStackTrace()));
        Assert.assertEquals(-1, Utils.computeStackOffset(new Throwable().getStackTrace()));
    }

}
