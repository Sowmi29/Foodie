package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SCREEN = 5000;
    // Variables
    Animation top,btm;
    ImageView img;
    TextView name,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        top = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btm = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img = findViewById(R.id.imageView4);
        name = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        img.setAnimation(top);
        name.setAnimation(btm);
        slogan.setAnimation(btm);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        },SCREEN);


    }
}