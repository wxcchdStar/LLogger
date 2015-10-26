package wxc.android.loggerwriterdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import wxc.android.logwriter.L;

public class MainActivity extends ActionBarActivity {
    public static Set<Activity> sActivities = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate");
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = null;
                if (str.equals("Crash"));
            }
        });
        findViewById(R.id.btn_cat_log).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CatLogActivity.class));
            }
        });

        // test leak
        sActivities.add(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart");

        JSONObject json = new JSONObject();
        try {
            json.put("boolean", true);
            json.put("double", 1.0);
            json.put("int", 1);
            json.put("long", 1L);

            JSONArray jsonArr = new JSONArray();
            jsonArr.put(1);
            jsonArr.put(2);
            jsonArr.put(3);
            jsonArr.put(true);
            json.put("arr", jsonArr);

            L.d(null, json.toString(), " ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
    }
}
