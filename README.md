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
