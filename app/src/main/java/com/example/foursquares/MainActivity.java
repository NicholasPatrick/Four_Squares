package com.example.foursquares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button timeTrialButton = findViewById(R.id.timeTrialButton);
        Button clearThirtyButton = findViewById(R.id.clearThirtyButton);
        MainActivity mainActivity = this;

        timeTrialButton.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, TimeTrialActivity.class);
            startActivity(intent);
        });

        clearThirtyButton.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, ClearThirtyActivity.class);
            startActivity(intent);
        });
    }
}