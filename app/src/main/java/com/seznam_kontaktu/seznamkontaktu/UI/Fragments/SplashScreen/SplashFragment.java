package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.SplashScreen;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.ContactListFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import butterknife.ButterKnife;

public class SplashFragment extends Fragment {

    public static final String CONTACT = "contact";

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //((MainActivity) getActivity()).showFragment(new ContactListFragment(), CONTACT);
                getFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ContactListFragment(), CONTACT).commit();

            }
        }, 1500);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

