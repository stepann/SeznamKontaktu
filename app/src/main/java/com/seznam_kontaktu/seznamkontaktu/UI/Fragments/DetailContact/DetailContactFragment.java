package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.DetailContact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seznam_kontaktu.seznamkontaktu.R;

public class DetailContactFragment extends Fragment {

    public static DetailContactFragment newInstance() {

        Bundle args = new Bundle();
        DetailContactFragment fragment = new DetailContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_contact, container, false);
    }
}
