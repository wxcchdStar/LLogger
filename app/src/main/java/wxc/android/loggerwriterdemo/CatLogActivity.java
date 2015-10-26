package wxc.android.loggerwriterdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CatLogActivity extends ActionBarActivity {
    private boolean mDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_log);

        final String logPath = getLogPath(this);
        final TextView logTv = (TextView) findViewById(R.id.tv_log);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        final View contentView = findViewById(R.id.content);

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(params[0])));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return sb.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                if (isFinishing() || mDestroy) return;
                logTv.setText(result);
                contentView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }.execute(logPath);

        Toast.makeText(this, logPath, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mDestroy = true;
        super.onDestroy();
    }

    public static String getLogPath(Context ctx) {
        String packageName = ctx.getPackageName();
        String logPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logPath = Environment.getExternalStorageDirectory() + File.separator + packageName + File.separator;
        } else {
            logPath = ctx.getFilesDir() + File.separator + packageName + File.separator;
        }
        return logPath + "log.txt";
    }
}
