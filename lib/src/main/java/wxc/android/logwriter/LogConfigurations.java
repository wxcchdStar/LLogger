package wxc.android.logwriter;

import android.content.Context;
import android.os.Environment;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.StatusPrinter;

public final class LogConfigurations {

    /**
     * 1. 如果有外部存储卡，则log文件放在SDCard根目录下的${package_name}文件夹内，文件名为${package_name}.log；
     * 2. 如果没有外部存储卡，则log文件放在/data/data/${package_name}/files/${package_name}文件夹内，文件名为${package_name}.log。
     * 3. 需要声明WRITE_EXTERNAL_STORAGE权限
     * 4. 可以保留包括今天在内的8天的log文件
     * @param ctx
     */
	public static void startLogger(Context ctx) {
        String packageName = ctx.getPackageName();
        String logName = "log.txt";
        String logPattern = "log.%d.txt";

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.reset();

		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
		rollingFileAppender.setAppend(true);
		rollingFileAppender.setContext(lc);
		
		String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + packageName + File.separator;
        } else {
            filePath = ctx.getFilesDir() + File.separator + packageName + File.separator;
        }

		rollingFileAppender.setFile(filePath + logName);

		TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
		rollingPolicy.setFileNamePattern(filePath + logPattern);
		rollingPolicy.setMaxHistory(7);
		// parent and context required!
		rollingPolicy.setParent(rollingFileAppender); 
		rollingPolicy.setContext(lc);
		rollingPolicy.start();

		rollingFileAppender.setRollingPolicy(rollingPolicy);

		// text pattern
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setPattern("%d{HH:mm:ss} [%thread] %-5level - %msg%n");
		encoder.setContext(lc);
		encoder.start();

		rollingFileAppender.setEncoder(encoder);
		rollingFileAppender.start();
		
		// add the newly created appenders to the root logger;
		// qualify Logger to disambiguate from org.slf4j.Logger
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ALL);
		root.addAppender(rollingFileAppender);

        // print any status messages (warnings, etc) encountered in logback config
        StatusPrinter.print(lc);
	}

	public static void stopLogger() {
		// assume SLF4J is bound to logback-classic in the current environment
	    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
	    loggerContext.stop();
	}

}
