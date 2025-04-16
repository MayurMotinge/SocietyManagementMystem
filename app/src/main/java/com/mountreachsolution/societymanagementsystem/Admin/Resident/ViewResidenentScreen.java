package com.mountreachsolution.societymanagementsystem.Admin.Resident;

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

public class ViewResidenentScreen extends AppCompatActivity {

    SearchView searchView_resident;
    List<pojo_All_Resident> list;
    AllResidentAdapter adapter;
    TextView tv_no_record;
    ListView lv_all_resident;
    ProgressBar progress;
    Button btn_add_resident;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_residenent_screen);

        setTitle("View Residenent");
        if (getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchView_resident = findViewById(R.id.searchview_resident);
        list = new ArrayList<>();
        lv_all_resident = findViewById(R.id.lv_all_resident);
        tv_no_record = findViewById(R.id.tv_no_record);
        progress = findViewById(R.id.progress);
        btn_add_resident = findViewById(R.id.btn_add_resident);

        btn_add_resident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewResidenentScreen.this, AddResidentScreen.class));
                finish();
            }
        });
        searchView_resident.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchresident(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchresident(newText);
                return false;
            }
        });
        getAllresident();
    }

    private void searchresident(String query) {

        List<pojo_All_Resident> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (pojo_All_Resident d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()) || d.getMobile_no().toUpperCase().contains(query.toUpperCase())
                    || d.getEmail_is().toUpperCase().contains(query.toUpperCase()) || d.getWing().toUpperCase().contains(query.toUpperCase())
                    || d.getFlat_no().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new AllResidentAdapter(tempcenterlist, ViewResidenentScreen.this,tv_no_record);
        lv_all_resident.setAdapter(adapter);
    }

    private void getAllresident() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_role","resident");
        client.post(Config.url_view_all_resident, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getAllResident");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setText("No Resident");
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile_no = jsonObject.getString("mobile_no");
                        String email_id = jsonObject.getString("email_id");
                        String wing = jsonObject.getString("wing");
                        String flat_no = jsonObject.getString("flat_no");
                        String username = jsonObject.getString("username");

                        list.add(new pojo_All_Resident(name, mobile_no, email_id,
                                wing, flat_no, username));
                    }

                    adapter = new AllResidentAdapter(list, ViewResidenentScreen.this, tv_no_record);
                    lv_all_resident.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ViewResidenentScreen.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewResidenentScreen.this, AdminHomeScreen.class));
        finish();
    }
}