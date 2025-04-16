package com.mountreachsolution.societymanagementsystem.Admin.Maintenance;

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

import com.mountreachsolution.societymanagementsystem.Comman.Config;
import com.mountreachsolution.societymanagementsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewMaintenanceOfficerScreen extends AppCompatActivity {

    SearchView searchView_maintenence_officer;
    List<pojo_Maintenance_Officer> list;
    ViewMaintenanceOfficerAdapter adapter;
    TextView tv_no_record;
    ListView lv_all_maintenence_officer;
    ProgressBar progress;
    Button btn_add_maintenence_officer;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_maintenence_screen);

        setTitle("View Maintenence Officer");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchView_maintenence_officer = findViewById(R.id.searchview_maintenence_officer);
        list = new ArrayList<>();
        lv_all_maintenence_officer = findViewById(R.id.lv_all_maintenence);
        tv_no_record = findViewById(R.id.tv_no_record);
        progress = findViewById(R.id.progress);
        btn_add_maintenence_officer = findViewById(R.id.btn_add_maintenence_officer);

        btn_add_maintenence_officer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewMaintenanceOfficerScreen.this, AddMaintenanceOfficerScreen.class));
                finish();
            }
        });

        searchView_maintenence_officer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchmaintenence(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchmaintenence(newText);
                return false;
            }
        });
        getMaintenenceOfficer();
    }

    private void searchmaintenence(String query) {

        List<pojo_Maintenance_Officer> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (pojo_Maintenance_Officer d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()) || d.getMobileno().toUpperCase().contains(query.toUpperCase())
                    || d.getEmail_id().toUpperCase().contains(query.toUpperCase()) || d.getUsername().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new ViewMaintenanceOfficerAdapter(tempcenterlist, ViewMaintenanceOfficerScreen.this, tv_no_record);
        lv_all_maintenence_officer.setAdapter(adapter);
    }

    private void getMaintenenceOfficer() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_role", "maintenance");
        client.post(Config.url_view_all_maintenence_officer, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getMaintenenceOfficer");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setText("No maintenence");
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile_no = jsonObject.getString("mobile_no");
                        String email_id = jsonObject.getString("email_id");
                        String username = jsonObject.getString("username");
                        String password = jsonObject.getString("password");

                        list.add(new pojo_Maintenance_Officer(id,name, mobile_no, email_id,
                                username,password));
                    }

                    adapter = new ViewMaintenanceOfficerAdapter(list, ViewMaintenanceOfficerScreen.this, tv_no_record);
                    lv_all_maintenence_officer.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ViewMaintenanceOfficerScreen.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}