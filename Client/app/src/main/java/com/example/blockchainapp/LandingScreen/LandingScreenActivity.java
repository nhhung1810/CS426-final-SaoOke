package com.example.blockchainapp.LandingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.blockchainapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class LandingScreenActivity extends AppCompatActivity {
    private ImageView iv_star;
    private ImageView iv_chicken;

    private int[] star;
    private int[] chicken;

    int counter;
    int period;

    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        initialize();

        Intent intent = getIntent();
        int timeLimit = intent.getIntExtra("timeLimit", 3000);

        counter = 0;
        period = 100;

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                iv_star.setImageResource(star[counter % 20]);
                iv_chicken.setImageResource(chicken[counter/2 % 6]);
                ++counter;
                if (counter * period >= timeLimit)
                    finish();
            }
        };
        timer.schedule(timerTask, 0, period);
    }

    private void initialize() {
        iv_star = findViewById(R.id.iv_star);
        iv_chicken = findViewById(R.id.iv_chicken);

        star = new int[20];
        star[0] = R.drawable.star00;
        star[1] = R.drawable.star01;
        star[2] = R.drawable.star02;
        star[3] = R.drawable.star03;
        star[4] = R.drawable.star04;
        star[5] = R.drawable.star05;
        star[6] = R.drawable.star06;
        star[7] = R.drawable.star07;
        star[8] = R.drawable.star08;
        star[9] = R.drawable.star09;
        star[10] = R.drawable.star10;
        star[11] = R.drawable.star11;
        star[12] = R.drawable.star12;
        star[13] = R.drawable.star13;
        star[14] = R.drawable.star14;
        star[15] = R.drawable.star15;
        star[16] = R.drawable.star16;
        star[17] = R.drawable.star17;
        star[18] = R.drawable.star18;
        star[19] = R.drawable.star19;

        chicken = new int[6];
        chicken[0] = R.drawable.chicken00;
        chicken[1] = R.drawable.chicken01;
        chicken[2] = R.drawable.chicken02;
        chicken[3] = R.drawable.chicken03;
        chicken[4] = R.drawable.chicken04;
        chicken[5] = R.drawable.chicken05;
    }
}