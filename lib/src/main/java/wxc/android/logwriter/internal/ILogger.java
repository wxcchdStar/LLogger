package wxc.android.logwriter.internal;

public interface ILogger {

    public void v(String tag, String msg);

    public void i(String tag, String msg);

    public void d(String tag, String msg);

    public void w(String tag, String msg);

    public void e(String tag, String msg);

}
