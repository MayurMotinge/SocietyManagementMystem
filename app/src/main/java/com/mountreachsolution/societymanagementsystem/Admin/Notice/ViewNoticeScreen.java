package com.mountreachsolution.societymanagementsystem.Admin.Notice;

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

public class ViewNoticeScreen extends AppCompatActivity {

    SearchView searchView_notice;
    List<pojo_All_Notice> list;
    AllNoticeAdapter adapter;
    TextView tv_no_record;
    ListView lv_all_notice;
    ProgressBar progress;
    Button btn_add_notice;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String user_role;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice_screen);

        setTitle("View Residenent");
        if (getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        user_role = preferences.getString("user_role","");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchView_notice = findViewById(R.id.searchview_notice);
        list = new ArrayList<>();
        lv_all_notice = findViewById(R.id.lv_all_notice);
        tv_no_record = findViewById(R.id.tv_no_record);
        progress = findViewById(R.id.progress);
        btn_add_notice = findViewById(R.id.btn_add_notice);

        if (user_role.equals("admin"))
        {
            btn_add_notice.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_add_notice.setVisibility(View.GONE);
        }
        btn_add_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewNoticeScreen.this, AddNoticeScreen.class));
            }
        });
        searchView_notice.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchnotice(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchnotice(newText);
                return false;
            }
        });
        getAllnotice();
    }

    private void searchnotice(String query) {

        List<pojo_All_Notice> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (pojo_All_Notice d : list) {
            if (d.getDate().toUpperCase().contains(query.toUpperCase()) || d.getTime().toUpperCase().contains(query.toUpperCase())
                    || d.getTitle().toUpperCase().contains(query.toUpperCase()) || d.getDescription().toUpperCase().contains(query.toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new AllNoticeAdapter(tempcenterlist, ViewNoticeScreen.this,tv_no_record);
        lv_all_notice.setAdapter(adapter);
    }

    private void getAllnotice() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Config.url_view_all_notice, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getAllNotice");
                    if (jsonArray.isNull(0)) {
                        tv_no_record.setVisibility(View.VISIBLE);
                        tv_no_record.setText("No notice");
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");


                        list.add(new pojo_All_Notice(id, date, time,
                                title, description));
                    }

                    adapter = new AllNoticeAdapter(list, ViewNoticeScreen.this, tv_no_record);
                    lv_all_notice.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ViewNoticeScreen.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}