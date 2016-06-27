package com.seznam_kontaktu.seznamkontaktu.UI.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.R;

import butterknife.ButterKnife;

public class SplashFragment extends Fragment {

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
                ((MainActivity) getActivity()).showFragment(new ContactListFragment(), "CONTACT");
            }
        }, 2000);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putBoolean("splash", true);
        super.onSaveInstanceState(outState);
    }
}

