package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity implements OnClickListener {

    RelativeLayout relativeLayout;
    Button viewmore;
    ValueAnimator mAnimator;
    private Switch toggleDark;
    public boolean isDark = false;


    /* Following code was used for white box tests
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
        createNotificationChannel("234");
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


        toggleDark = (Switch) findViewById(R.id.switch3);
        toggleDark.toggle();




        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        //String[] items = new String[]{"BLUE", "PINK"};
        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);







        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        WebView twitter = (WebView) findViewById(R.id.timeline_webview);
        boolean tgpref = preferences.getBoolean("tgpref", true);  //default is false
        toggleDark.setChecked(tgpref);

        toggleDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toggleDark.toggle();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("tgpref", toggleDark.isChecked()); // value to store
                editor.commit();
                //toggleDark.setChecked(tgpref);

                if(toggleDark.isChecked()) {
                    isDark = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }
                else {
                    isDark = false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

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
                //show storage amount
                long size = getApplicationContext().getFilesDir().getTotalSpace();
                double display_size = (double) size / 1000000000;

                //two decimal places
                double a = display_size; // just assigning your decimal to a variable
                a=a*100;              // this sets a to 354.555555
                a=Math.floor(a);      // this sets a to 354
                a=a/100;              // this sets a to 3.54 and thus removing all your 5's

                String display_storage = "MapCovid takes up " + a + " MB";
                TextView txtView = (TextView) findViewById(R.id.Storage);
                txtView.setText(display_storage);
                Toast.makeText(getApplicationContext(),"Data has been cleared!",Toast.LENGTH_LONG).show();
            }
        });

        //test notification button
        Button b1=(Button)findViewById(R.id.notification);
        String CHANNEL_ID = "234";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String tittle= "MapCovid Notification Test";
                //String subject= "Notification Button";
                //String body= "Button Pressed";

                // Create an explicit intent for an Activity in your app
                //Intent intent = new Intent(this, AlertDetails.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("MapCovid Test Notification")
                        .setContentText("Test Notification Button was pressed!")
                        //.setContentText(storage)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                int notificationId = 0;
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId, builder.build());

                /*
                int NOTIFICATION_ID = 234;
                String CHANNEL_ID = "my_channel_01";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    CharSequence name = "my_channel";
                    String Description = "This is my channel";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    mChannel.setDescription(Description);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    mChannel.setShowBadge(false);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.notification).build();

                int NOTIFICATION_ID = 234;

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(NOTIFICATION_ID, notify);
                 */
            }
        });

        //show storage amount
        long size = getApplicationContext().getFilesDir().getTotalSpace();
        double display_size = (double) size / 1000000000;

        //two decimal places
        double a = display_size; // just assigning your decimal to a variable
        a=a*100;              // this sets a to 354.555555
        a=Math.floor(a);      // this sets a to 354
        a=a/100;              // this sets a to 3.54 and thus removing all your 5's

        String display_storage = "MapCovid takes up " + a + " MB";
        TextView txtView = (TextView) findViewById(R.id.Storage);
        txtView.setText(display_storage);
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
    public boolean getDark(){
        return isDark;
    }

    private void createNotificationChannel(String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Channel for Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
