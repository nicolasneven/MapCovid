package com.example.mapcovid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding3 extends AppCompatActivity implements View.OnClickListener {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding3);

        next = (Button) findViewById(R.id.button5);
        next.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button5:
                startActivity(new Intent(getApplicationContext(), Onboarding4.class));
                overridePendingTransition(0, 0);
        }
    }

}
