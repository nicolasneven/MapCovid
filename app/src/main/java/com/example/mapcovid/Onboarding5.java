package com.example.mapcovid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding5 extends AppCompatActivity implements View.OnClickListener {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding5);

        next = (Button) findViewById(R.id.button7);
        next.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button7:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                overridePendingTransition(0, 0);
        }
    }

}
