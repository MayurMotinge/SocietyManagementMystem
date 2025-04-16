package com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.societymanagementsystem.Admin.AdminHomeScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback.AllComplaint_FeedbackAdapter;
import com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback.pojo_All_Complaint_Feedback;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.AddResidentScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.AllResidentAdapter;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.ViewResidenentScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.pojo_All_Resident;
import com.mountreachsolution.societymanagementsystem.Comman.Config;
import com.mountreachsolution.societymanagementsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewComplaints_FeedbackScreen extends AppCompatActivity {

    SearchView searchView_complaint_feedback;
    List<pojo_All_Complaint_Feedback> list;
    AllComplaint_FeedbackAdapter adapter;
    TextView tv_no_record;
    ListView lv_all_complaint_feedback;
    ProgressBar progress;
    Button btn_add_complaint_feedback;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints__feedback_screen);
        setTitle("View Complaints");
        setTitle("View Residenent");
        if (getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchView_complaint_feedback = findViewById(R.id.searchview_complaint_feedback);
        list = new ArrayList<>();
        lv_all_complaint_feedback = findViewById(R.id.lv_all_complaint_feedback);
        tv_no_record = findViewById(R.id.tv_no_record);
        progress = findViewById(R.id.progress);


        searchView_complaint_feedback.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchComplaintFeedback(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchComplaintFeedback(newText);
                return false;
            }
        });
        getAllComplaintFeedback();
    }

    private void searchComplaintFeedback(String query) {

        List<pojo_All_Complaint_Feedback> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (pojo_All_Complaint_Feedback d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()) || d.getWing().toUpperCase().contains(query.toUpperCase())
                    || d.getFlat_no().toUpperCase().contains(query.toUpperCase()) || d.getType().toUpperCase().contains(query.toUpperCase())
                    || d.getComplaint_feedback().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new AllComplaint_FeedbackAdapter(tempcenterlist, ViewComplaints_FeedbackScreen.this,tv_no_record);
        lv_all_complaint_feedback.setAdapter(adapter);
    }

    private void getAllComplaintFeedback() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Config.url_view_all_complaint_feedback, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getAllComplaintFeedback");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setText("No Complaint/Feedback");
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String wing = jsonObject.getString("wing");
                        String flat_no = jsonObject.getString("flat_no");
                        String type = jsonObject.getString("type");
                        String description = jsonObject.getString("message");

                        list.add(new pojo_All_Complaint_Feedback(id,name,
                                wing, flat_no, type,description));
                    }

                    adapter = new AllComplaint_FeedbackAdapter(list, ViewComplaints_FeedbackScreen.this, tv_no_record);
                    lv_all_complaint_feedback.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ViewComplaints_FeedbackScreen.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewComplaints_FeedbackScreen.this, AdminHomeScreen.class));
        finish();
    }
}