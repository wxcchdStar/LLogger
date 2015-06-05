package wxc.android.loggerwriterdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import wxc.android.logwriter.Logger;

/**
 * Created by Chenhd on 2015/4/8.
 */
public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    public static Set<Activity> sActivitys = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = null;
                if (str.equals("Crash")) return;
            }
        });
        findViewById(R.id.btn_cat_log).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CatLogActivity.class));
            }
        });

        // test leak
        sActivitys.add(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
    }
}
