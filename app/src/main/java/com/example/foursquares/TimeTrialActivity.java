package com.example.foursquares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.Locale;

public class TimeTrialActivity extends AppCompatActivity {
    int score;
    long startTime;
    TextView timeText;
    TextView scoreText;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trial);
        timeText = findViewById(R.id.timeRemaining);
        scoreText = findViewById(R.id.scoreText);
        handler = new Handler();
        handler.postDelayed(runnable, 0);
        reset();
        updateScore();
    }

    public void solved() {
        if (5 * 60 * 1000 - (SystemClock.uptimeMillis() - startTime) > 0) {
            score++;
            updateScore();
        }
    }

    public void reset() {
        score = 0;
        startTime = SystemClock.uptimeMillis();
        updateScore();
    }

    public void updateScore() {
        scoreText.setText(String.format(Locale.US, "Score: %d", this.score));
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long remainingMillis = 5 * 60 * 1000 - (SystemClock.uptimeMillis() - startTime);
            if (remainingMillis < 0) {
                remainingMillis = 0;
            }
            long minutes = remainingMillis / 60000;
            double seconds = remainingMillis % 60000 / 1000.0;
            timeText.setText(String.format(Locale.US, "%d:%05.2f", minutes, seconds));
            if (remainingMillis > 0) {
                handler.postDelayed(this, 0);
            }
        }
    };
}