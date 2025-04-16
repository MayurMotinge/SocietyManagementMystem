package com.mountreachsolution.societymanagementsystem.Residence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback.ViewComplaints_FeedbackScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Maintenance.ViewMaintenanceOfficerScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Notice.ViewNoticeScreen;
import com.mountreachsolution.societymanagementsystem.Admin.Resident.ViewResidenentScreen;
import com.mountreachsolution.societymanagementsystem.LoginScreen;
import com.mountreachsolution.societymanagementsystem.R;

public class HomeScreen extends AppCompatActivity {

    boolean doubletap = false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CardView cardView11, cardView22, cardView33, cardView44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Resident App");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        cardView11 = findViewById(R.id.cardview11);
        cardView22 = findViewById(R.id.cardview22);
        cardView33 = findViewById(R.id.cardview33);
        cardView44 = findViewById(R.id.cardview44);
    }

    public void card_view11(View view) {
        startActivity(new Intent(HomeScreen.this, ViewMaintenanceScreen.class));
    }

    public void card_view22(View view) {
        startActivity(new Intent(HomeScreen.this, AddComplaint_FeedbackScreen.class));
        finish();
    }

    public void card_view33(View view) {
        startActivity(new Intent(HomeScreen.this, ViewNoticeScreen.class));
    }

    public void card_view44(View view) {
        logout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        androidx.appcompat.app.AlertDialog.Builder ad = new androidx.appcompat.app.AlertDialog.Builder(this);
        ad.setTitle("Logout");
        ad.setMessage("Are you sure for logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("residentlogin", false).commit();
                startActivity(new Intent(HomeScreen.this, LoginScreen.class));
                finish();
            }
        }).create().show();
    }


    @Override
    public void onBackPressed() {
        if (doubletap) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Please Press again to exit the app", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }
    }
}