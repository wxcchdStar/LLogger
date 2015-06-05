package wxc.android.logwriter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.slf4j.LoggerFactory;

/**
 * 问: Logger能做什么？
 * 答: 1.Logger将在输出Log到logcat的同时保存Log及Crash异常到本地(v1.0.0)
 *     2.Logger可以在应用OOM时将其堆进行转存，即在本地生成hprof文件，方便分析问题(v1.1.0)
 *     // 3.Logger集成了LeakCanary，帮助我们检测内存泄露(v1.1.0)
 */
public final class Logger {
	private static org.slf4j.Logger sLog = LoggerFactory.getLogger(Logger.class);

    public static void init(Application app) {
		Context ctx = app.getApplicationContext();
        LogConfigurations.startLogger(ctx);
        CrashExceptionHandler.getInstance(ctx).init();
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
        sLog.trace(tag + "\t" + msg);
    }

	public static void i(String tag, String msg) {
        Log.i(tag, msg);
		sLog.info(tag + "\t" + msg);
	}

	public static void d(String tag, String msg) {
        Log.d(tag, msg);
		sLog.debug(tag + "\t" + msg);
	}

	public static void w(String tag, String msg) {
        Log.w(tag, msg);
		sLog.warn(tag + "\t" + msg);
	}

	public static void e(String tag, String msg) {
        Log.e(tag, msg);
		sLog.error(tag + "\t" + msg);
	}

}
