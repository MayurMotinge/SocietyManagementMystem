package com.mountreachsolution.societymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView img_logo;
    TextView txt_title;

    Animation blink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_logo = findViewById(R.id.img_logo);
        txt_title = findViewById(R.id.txt_title);

        blink = AnimationUtils.loadAnimation(this,R.anim.blink);
        img_logo.setAnimation(blink);

        new Thread()
        {
            public void run()
            {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally {
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                    finish();
                }
            }
        }.start();
    }
}