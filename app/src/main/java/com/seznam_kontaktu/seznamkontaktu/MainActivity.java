package com.seznam_kontaktu.seznamkontaktu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.SplashScreen.SplashFragment;


public class MainActivity extends AppCompatActivity {

    Toolbar myToolbar;

    public static final String SPLESH = "splesh";
    public static final String CONTACT = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //show fragment, if the fragment has already existed, find by TAG
        if (savedInstanceState == null) {
            showFragmentWithoutBackStack(new SplashFragment(), SPLESH);
        } else {
            getSupportFragmentManager().findFragmentByTag(CONTACT);
        }
    }

    public void showFragmentWithBackStack(Fragment fragment, String TAG) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame_layout, fragment, TAG).commit();
    }
    public void showFragmentWithoutBackStack(Fragment fragment, String TAG) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment, TAG).commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


