package com.example.mapcovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Onboarding1 extends AppCompatActivity implements View.OnClickListener {

    Button next;
    public Onboarding1(){
        next = new Button(this);
        next = createButton();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding1);

        next = (Button) findViewById(R.id.button3);
        next.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                startActivity(new Intent(getApplicationContext(), Onboarding2.class));
                overridePendingTransition(0, 0);
        }
    }
    public Button createButton(){
       return (Button) next;
    }

}
