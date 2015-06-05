# LogWriter
A tool that write log to local.

You use this for saving app's log, incule crash log.

# Getting started
In your Application class:
``` java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(this);
    }
}
```

Than in your code:
``` java
Logger.d("tag" "log message");
```
 
 Lastly the **logger.py** is a convenient script that comment or uncomment Logger statement.  

**WARNNING**: The Logger statement must be on a single line!

# Tips
1. If the sdcard exists, the log path is **/sdcard/#{package_name}/log.txt**.
2. If the sdcard doesn't exists, the log path is **/data/data/#{package_name}/files/#{package_name}/log.txt**.
3. You need to declare the **WRITE_EXTERNAL_STORAGE** permission in the AndroidManifest.xml.
4. It save recently 8 days's log at most.
