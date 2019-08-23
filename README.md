# LLogger
A tool that write log to local and format logcat's display.

You use this for saving app's log, include crash log.

# Getting started
In your `build.gradle`:

```
allprojects {
    repositories {
		...
	    maven { url "https://jitpack.io" }
    }
}

...

dependencies {
    implementation 'com.github.wxcchdStar:LLogger:v1.3.0'
}
```
In your `Application` class:
``` java
L.Builder builder = new L.Builder();
L.set(builder.addLogCat().addLocalLog(this).logCrash(this).create());
```

In your class:
``` java
L.d("message");
L.d("message1", "message2", 3);
```

# Proguard
```
-keep public class wxc.android.logwriter.L { *; }
```

# Tips
1. If the sdcard exists, the log path is /Android/data/#{package_name}/log/log.txt**.
2. If the sdcard doesn't exists, the log path is **/data/data/#{package_name}/files/#{package_name}/log.txt**.
3. It save recently 8 days's log at most.
