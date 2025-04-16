package com.mountreachsolution.societymanagementsystem.Residence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class AddComplaint_FeedbackScreen extends AppCompatActivity {

    EditText et_name, et_wing, et_flat_no, et_message;
    Spinner type_of_message;
    Button btn_add_complaint_feedback;

    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint__feedback_screen);

        et_name = findViewById(R.id.et_add_complaint_feedback_name);
        et_wing = findViewById(R.id.et_add_complaint_feedback_wing);
        et_flat_no = findViewById(R.id.et_add_complaint_feedback_flat_no);
        et_message = findViewById(R.id.et_add_complaint_feedback_message);
        type_of_message = findViewById(R.id.spinner_type);
        btn_add_complaint_feedback = findViewById(R.id.btn_add_add_complaint_feedback);
        progress = findViewById(R.id.progress);

        btn_add_complaint_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    et_name.setError("Please Enter Your Name");
                } else if (TextUtils.isEmpty(et_wing.getText().toString())) {
                    et_wing.setError("Please Enter Your Wing");
                } else if (TextUtils.isEmpty(et_flat_no.getText().toString())) {
                    et_flat_no.setError("Please Enter Your Flat Number");
                } else if (type_of_message.getSelectedItem().toString().equals("Select Your Type")) {
                    et_name.setError("Please Select Your Type");
                } else if (TextUtils.isEmpty(et_message.getText().toString())) {
                    et_message.setError("Please Enter Your Message");
                } else {
                    addFeedback_Complaint();
                }

            }
        });
    }


    private void addFeedback_Complaint() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name", et_name.getText().toString());
        params.put("wing", et_wing.getText().toString());
        params.put("flat_no", et_flat_no.getText().toString());
        params.put("type", type_of_message.getSelectedItem().toString());
        params.put("message", et_message.getText().toString());

        client.post(Config.url_addFeedback, params, new JsonHttpResponseHandler() {
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
                        Toast.makeText(AddComplaint_FeedbackScreen.this, "Feedback Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddComplaint_FeedbackScreen.this, HomeScreen.class));
                        finish();
                    } else {
                        Toast.makeText(AddComplaint_FeedbackScreen.this, "Already Feedback added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddComplaint_FeedbackScreen.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddComplaint_FeedbackScreen.this, HomeScreen.class));
        finish();
    }
}