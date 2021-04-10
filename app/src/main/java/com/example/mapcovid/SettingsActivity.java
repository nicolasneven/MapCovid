package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class SettingsActivity extends AppCompatActivity implements OnClickListener {

    RelativeLayout relativeLayout;
    Button viewmore;
    ValueAnimator mAnimator;

    /*
    Button location;
    Button notification;
    Button about;
    public SettingsActivity(){
        location = new Button(this);
        location = createLocationButton();
        notification = new Button(this);
        notification = createNotificationButton();
        about = new Button(this);
        about = createNotificationButton();
    }

    public Button createLocationButton(){
        return (Button) location;
    }
    public Button createNotificationButton(){
        return (Button) notification;
    }
    public Button createAboutButton(){
        return (Button) about;
    }
    */
    Button location;
    public Button createLocationButton(){
        location = new Button(this);
        return (Button) location;
    }

    Button notification;
    public Button createNotificationButton(){
        notification = new Button(this);
        return (Button) notification;
    }

    Button about;
    public Button createAboutButton(){
        about = new Button(this);
        return (Button) about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setSelectedItemId(R.id.settingsicon);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mapicon:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mediaicon:
                        startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.historyicon:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settingsicon:
                        return true;
                }
                return false;
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.expandable);

        viewmore = (Button) findViewById(R.id.viewmore);

        viewmore.setOnClickListener((View.OnClickListener) this);

        relativeLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        relativeLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        relativeLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        relativeLayout.measure(widthSpec, heightSpec);

                        mAnimator = slideAnimator(0, relativeLayout.getMeasuredHeight());
                        return true;
                    }
                });

        Context context = getApplicationContext();

        final Button button = findViewById(R.id.clearall);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteCache(context);
                Toast.makeText(getApplicationContext(),"Data has been cleared!",Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void expand() {

        relativeLayout.setVisibility(View.VISIBLE);
        mAnimator.start();
    }

    public boolean expandTest() {

        relativeLayout.setVisibility(View.VISIBLE);
        mAnimator.start();

        return true;
    }

    private void collapse() {
        int finalHeight = relativeLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
                layoutParams.height = value;
                relativeLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewmore:

                if (relativeLayout.getVisibility() == View.GONE) {
                    expand();
                } else {
                    collapse();
                }

                break;

        }
    }

}