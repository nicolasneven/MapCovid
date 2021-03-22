package com.example.mapcovid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding2 extends AppCompatActivity implements View.OnClickListener {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding2);

        next = (Button) findViewById(R.id.button4);
        next.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button4:
                startActivity(new Intent(getApplicationContext(), Onboarding3.class));
                overridePendingTransition(0, 0);
        }
    }

}
