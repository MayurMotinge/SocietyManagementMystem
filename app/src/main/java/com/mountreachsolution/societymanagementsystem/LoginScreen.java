package com.mountreachsolution.societymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.AddResidentScreen;
import com.mountreachsolution.societymanagementsystem.Admin.AdminHomeScreen;
import com.mountreachsolution.societymanagementsystem.Comman.Config;
import com.mountreachsolution.societymanagementsystem.Maintenance.MaintenanceHomeScreen;
import com.mountreachsolution.societymanagementsystem.Residence.HomeScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginScreen extends AppCompatActivity {

    RelativeLayout relativeLayout;
    EditText et_username,et_password;
    CheckBox chk_show_password;
    Button btn_sign_in;
    ProgressBar progress;

    boolean doubletap = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        setTitle("Login Here");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        if(preferences.getBoolean("adminlogin",false))
        {
            startActivity(new Intent(LoginScreen.this, AdminHomeScreen.class));
            finish();
        }

        if(preferences.getBoolean("residentlogin",false))
        {
            startActivity(new Intent(LoginScreen.this, HomeScreen.class));
            finish();
        }

        if(preferences.getBoolean("maintenancelogin",false))
        {
            startActivity(new Intent(LoginScreen.this, MaintenanceHomeScreen.class));
            finish();
        }

        init();

    }

    private void init() {

        relativeLayout = findViewById(R.id.r1);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        chk_show_password = findViewById(R.id.chk_login_show_hide);
        btn_sign_in = findViewById(R.id.btn_login_login);
        progress = findViewById(R.id.progress);

        chk_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void log_in(View view)
    {
        if (et_username.getText().toString().isEmpty())
        {
            et_username.setError("Please Enter Username");
        }
        else if (et_password.getText().toString().isEmpty())
        {
            et_password.setError("Please Enter Password");
        }
        else if (et_password.getText().toString().length()<= 4)
        {
            et_password.setError("Password must be greater then 4");
        }
        else {
            login();
        }
    }

    private void login()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",et_username.getText().toString());
        params.put("password",et_password.getText().toString());

        client.post(Config.url_login,params,new JsonHttpResponseHandler() {

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
                    if (aa.equals("1"))
                    {
                        editor.putString("username",et_username.getText().toString()).commit();
                        String user_role = response.getString("user_role");
//                        String company_name = response.getString("company_name");
                        if (user_role.equals("resident"))
                        {
                            editor.putString("user_role",user_role).commit();
                            Toast.makeText(LoginScreen.this, ""+user_role, Toast.LENGTH_SHORT).show();
                            editor.putBoolean("residentlogin",true).commit();
                            Toast.makeText(LoginScreen.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                            finish();
                        }
                        else if (user_role.equals("admin"))
                        {
                            editor.putString("user_role",user_role).commit();
                            Toast.makeText(LoginScreen.this, ""+user_role, Toast.LENGTH_SHORT).show();
                            editor.putBoolean("adminlogin",true).commit();
                            Toast.makeText(LoginScreen.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreen.this, AdminHomeScreen.class));
                            finish();
                        }
                        else if (user_role.equals("maintenance"))
                        {
                            editor.putString("user_role",user_role).commit();
//                            editor.putString("company_name",company_name).commit();
//                            Toast.makeText(LoginScreen.this, ""+user_role+ " "+company_name, Toast.LENGTH_SHORT).show();
                            editor.putBoolean("maintenancelogin",true).commit();
                            editor.putString("username",et_username.getText().toString()).commit();
                            Toast.makeText(LoginScreen.this, "Login Successfully Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreen.this, MaintenanceHomeScreen.class));
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginScreen.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(LoginScreen.this, "Colud Not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void sing_up(View view)
    {
        startActivity(new Intent(LoginScreen.this, AddResidentScreen.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (doubletap)
        {
            super.onBackPressed();
        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Double Tap to Exit App", Snackbar.LENGTH_LONG)
                    .setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            },2000);
        }
    }
}