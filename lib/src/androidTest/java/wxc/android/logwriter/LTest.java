package wxc.android.logwriter;

import android.app.Application;
import android.test.ApplicationTestCase;

public class LTest extends ApplicationTestCase<Application> {

    public LTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testD() {
        L.d(null);
        L.d(null, null);

        Integer i = null;
        L.d(i, 1);
        L.d(2);
        L.d(new Object());
    }
}
