package com.seznam_kontaktu.seznamkontaktu.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.RecyclerView.ContactsAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

public class ContactListFragment extends Fragment {

    RecyclerView recyclerView;
    ContactsAdapter contactsAdapter;
    List<Contact> contacts = new ArrayList<>();


    public static ContactListFragment newInstance() {

        Bundle args = new Bundle();

        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contacts = Contact.listAll(Contact.class);
        contactsAdapter = new ContactsAdapter(getActivity(), contacts);
        recyclerView.setAdapter(contactsAdapter);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //show actionBar
        ((MainActivity) getActivity()).getSupportActionBar().show();
        //show title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

