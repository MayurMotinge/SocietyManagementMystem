package com.mountreachsolution.societymanagementsystem.Residence;

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

public class ViewMaintenanceScreen extends AppCompatActivity {

    SearchView searchView_maintenance;
    List<pojo_All_Maintenance> list;
    AllMaintenanceAdapter adapter;
    TextView tv_no_record;
    ListView lv_all_maintenance;
    ProgressBar progress;
    Button btn_add_maintenance;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_maintenance_screen2);
        setTitle("View Residenent");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchView_maintenance = findViewById(R.id.searchview_maintenance);
        list = new ArrayList<>();
        lv_all_maintenance = findViewById(R.id.lv_all_maintenance);
        tv_no_record = findViewById(R.id.tv_no_record);
        progress = findViewById(R.id.progress);

        searchView_maintenance.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMaintenance(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMaintenance(newText);
                return false;
            }
        });
        getAllmaintenance();
    }

    private void searchMaintenance(String query) {

        List<pojo_All_Maintenance> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (pojo_All_Maintenance d : list) {
            if (d.getDate().toUpperCase().contains(query.toUpperCase()) || d.getTitle().toUpperCase().contains(query.toUpperCase())
                    || d.getCost().toUpperCase().contains(query.toUpperCase()) || d.getDescription().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new AllMaintenanceAdapter(tempcenterlist, ViewMaintenanceScreen.this, tv_no_record);
        lv_all_maintenance.setAdapter(adapter);
    }

    private void getAllmaintenance() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_role", "maintenance");
        client.post(Config.url_view_all_maintenance, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getAllMaintenance");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setText("No Maintenance");
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String date = jsonObject.getString("date");
                        String title = jsonObject.getString("title");
                        String cost = jsonObject.getString("cost");
                        String description = jsonObject.getString("description");

                        list.add(new pojo_All_Maintenance(id, date, title,
                                cost, description));
                    }

                    adapter = new AllMaintenanceAdapter(list, ViewMaintenanceScreen.this, tv_no_record);
                    lv_all_maintenance.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ViewMaintenanceScreen.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}