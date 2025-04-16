package com.mountreachsolution.societymanagementsystem.Maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.societymanagementsystem.Comman.Config;
import com.mountreachsolution.societymanagementsystem.R;
import com.mountreachsolution.societymanagementsystem.Residence.HomeScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddMaintenance extends AppCompatActivity {
    EditText et_date, et_title, et_cost, et_description;
    Button btn_add_maintenance;

    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);

        setTitle("Add Maintenance");
        et_date = findViewById(R.id.et_add_maintenance_date);
        et_title = findViewById(R.id.et_add_maintenance_Title);
        et_cost = findViewById(R.id.et_add_maintenance_cost);
        et_description = findViewById(R.id.et_add_maintenance_description);
        btn_add_maintenance = findViewById(R.id.btn_add_add_maintenance);
        progress = findViewById(R.id.progress);

        btn_add_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_date.getText().toString())) {
                    et_date.setError("Please Enter Date");
                } else if (TextUtils.isEmpty(et_title.getText().toString())) {
                    et_title.setError("Please Enter Title");
                } else if (TextUtils.isEmpty(et_cost.getText().toString())) {
                    et_cost.setError("Please Enter Cost");
                }  else if (TextUtils.isEmpty(et_description.getText().toString())) {
                    et_description.setError("Please Enter Description");
                } else {
                    addMaintenance();
                }

            }
        });
    }

    private void addMaintenance() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("date", et_date.getText().toString());
        params.put("title", et_title.getText().toString());
        params.put("cost", et_cost.getText().toString());
        params.put("description", et_description.getText().toString());

        client.post(Config.url_addMaintenance, params, new JsonHttpResponseHandler() {
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
                        Toast.makeText(AddMaintenance.this, "Maintenance Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddMaintenance.this, MaintenanceHomeScreen.class));
                        finish();
                    } else {
                        Toast.makeText(AddMaintenance.this, "Already Maintenance added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddMaintenance.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddMaintenance.this, MaintenanceHomeScreen.class));
        finish();
    }
}