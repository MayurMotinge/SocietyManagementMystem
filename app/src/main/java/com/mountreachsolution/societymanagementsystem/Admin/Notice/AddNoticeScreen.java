package com.mountreachsolution.societymanagementsystem.Admin.Notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.societymanagementsystem.Comman.Config;
import com.mountreachsolution.societymanagementsystem.LoginScreen;
import com.mountreachsolution.societymanagementsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddNoticeScreen extends AppCompatActivity {

    EditText et_date, et_time, et_title, et_description;
    Button btn_add_notice;

    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice_screen);
        setTitle("Add Notice");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        progress = findViewById(R.id.progress);

        et_date = findViewById(R.id.et_notice_date);
        et_time = findViewById(R.id.et_notice_time);
        et_title = findViewById(R.id.et_notice_title);
        et_description = findViewById(R.id.et_notice_description);
        btn_add_notice = findViewById(R.id.btn_add_notice);

        btn_add_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_date.getText().toString().isEmpty()) {
                    et_date.setError("Please Enter Your Date");
                } else if (et_time.getText().toString().isEmpty()) {
                    et_time.setError("Please Enter Your Time");
                } else if (et_title.getText().toString().isEmpty()) {
                    et_title.setError("Please Enter Your Title");
                } else if (et_description.getText().toString().isEmpty()) {
                    et_title.setError("Please Enter Your Description");
                } else {
                    addnotice();
                }
            }
        });
    }

    public void addnotice() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("date", et_date.getText().toString());
        params.put("time", et_time.getText().toString());
        params.put("title", et_title.getText().toString());
        params.put("description", et_description.getText().toString());

        client.post(Config.url_addnotice, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progress.setVisibility(View.GONE);
                try {
                    String aa = response.getString("success");

                    if (aa.equals("1")) {
                        Toast.makeText(AddNoticeScreen.this, "Notice Added Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNoticeScreen.this, ViewNoticeScreen.class));
                        finish();
                    } else {
                        Toast.makeText(AddNoticeScreen.this, "Already notice added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddNoticeScreen.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddNoticeScreen.this, LoginScreen.class));
        finish();
    }
}