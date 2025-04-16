package com.mountreachsolution.societymanagementsystem.Admin.Resident;

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

public class AddResidentScreen extends AppCompatActivity {


    EditText et_name, et_mobile_no, et_email, et_wing, et_flat_no, et_username, et_password;
    Button btn_add_resident;

    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resident_screen);
        setTitle("Add Resident");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        progress = findViewById(R.id.progress);

        et_name = findViewById(R.id.et_resident_name);
        et_mobile_no = findViewById(R.id.et_resident_mobile_no);
        et_email = findViewById(R.id.et_resident_email);
        et_wing = findViewById(R.id.et_resident_wing);
        et_flat_no= findViewById(R.id.et_resident_flat_no);
        et_username = findViewById(R.id.et_resident_username);
        et_password = findViewById(R.id.et_resident_password);
        btn_add_resident = findViewById(R.id.btn_add_resident);

        btn_add_resident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().isEmpty()) {
                    et_name.setError("Please Enter Your Name");
                } else if (et_mobile_no.getText().toString().isEmpty()) {
                    et_mobile_no.setError("Please Enter Your Mobile Number");
                } else if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Please Enter Your Mail Id");
                } else if (et_wing.getText().toString().isEmpty()) {
                    et_wing.setError("Enter Wing");
                } else if (et_flat_no.getText().toString().isEmpty()) {
                    et_flat_no.setError("Enter  Flat No");
                } else if (et_username.getText().toString().isEmpty()) {
                    et_username.setError("Enter Your Username");
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Enter Your Password");
                } else {
                    addResident();
                }
            }
        });
    }

    public void addResident() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name", et_name.getText().toString());
        params.put("mobile_no", et_mobile_no.getText().toString());
        params.put("email", et_email.getText().toString());
        params.put("wing", et_wing.getText().toString());
        params.put("flat_no", et_flat_no.getText().toString());
        params.put("username", et_username.getText().toString());
        params.put("password", et_password.getText().toString());

        client.post(Config.url_addResident, params, new JsonHttpResponseHandler() {
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
                        Toast.makeText(AddResidentScreen.this, "Resident Added Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddResidentScreen.this, ViewResidenentScreen.class));
                        finish();
                    } else {
                        Toast.makeText(AddResidentScreen.this, "Already resident added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddResidentScreen.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddResidentScreen.this, LoginScreen.class));
        finish();
    }
}