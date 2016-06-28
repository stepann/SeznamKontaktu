package com.seznam_kontaktu.seznamkontaktu;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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
        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //show fragment, if the fragment has already existed, find by TAG
        if(savedInstanceState == null) {
            showFragment(new SplashFragment(), SPLESH);
        } else {
            getSupportFragmentManager().findFragmentByTag(CONTACT);
        }
    }

    public void showFragment(Fragment fragment, String TAG) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment, TAG).commit();
    }
    @Override
    //Menu item icons
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}