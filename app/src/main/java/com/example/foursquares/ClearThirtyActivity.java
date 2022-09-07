package com.example.foursquares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.Locale;

public class ClearThirtyActivity extends AppCompatActivity {
    int score;
    long startTime;
    TextView timeText, scoreText;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_thirty);
        timeText = findViewById(R.id.timeElapsed);
        scoreText = findViewById(R.id.scoreOutOfThirty);
        reset();
        handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    public void solved() {
        score++;
        scoreText.setText(String.format(Locale.US, "Solves: %d/30", score));
    }

    public void reset() {
        startTime = SystemClock.uptimeMillis();
        score = 0;
        scoreText.setText(String.format(Locale.US, "Solves: %d/30", score));
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long elapsedMillis = SystemClock.uptimeMillis() - startTime;
            long minutes = elapsedMillis / 60000;
            double seconds = elapsedMillis % 60000 / 1000.0;
            timeText.setText(String.format(Locale.US, "%d:%05.2f", minutes, seconds));
            if (score < 30) {
                handler.postDelayed(this, 0);
            }
        }
    };
}