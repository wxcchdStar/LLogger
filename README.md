# LLogger
A tool that write log to local and format logcat's display.

You use this for saving app's log, incule crash log.

# Getting started
``` java
L.d("message");
L.d("message1", "message2", 3);
```
 
 Lastly the **logger.py** is a convenient script that comment or uncomment Logger statement.  

**WARNNING**: The Logger statement must be on a single line!

# Tips
1. If the sdcard exists, the log path is **/sdcard/#{package_name}/log.txt**.
2. If the sdcard doesn't exists, the log path is **/data/data/#{package_name}/files/#{package_name}/log.txt**.
3. You need to declare the **WRITE_EXTERNAL_STORAGE** permission in the AndroidManifest.xml.
4. It save recently 8 days's log at most.

Please forgive my poor English.
