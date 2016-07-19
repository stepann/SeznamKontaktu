package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact.NewContactFragment;

import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ContactListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "newcontact";
    private Contact contact;

    FloatingActionButton fabButton;
    SearchView searchView;
    RecyclerView recyclerView;
    ContactsAdapter contactsAdapter;
    List<Contact> mContact = new ArrayList<>();

    public static ContactListFragment newInstance() {
        Bundle args = new Bundle();
        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        searchView = (SearchView) view.findViewById(R.id.search_view);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fab_button);
        fabButton.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContact = SugarRecord.listAll(Contact.class);
        contactsAdapter = new ContactsAdapter(getActivity(), mContact);
        recyclerView.setAdapter(contactsAdapter);

        inputFilter();

        /*try {
            contact = DataCache.getInstance().pop(Contact.class);
        } catch (NullPointerException e) {
            Log.i("null", "nullpointer");
        }

        if(contact != null) {
            mContact.add(contact);
        }*/

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

    public void inputFilter() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactsAdapter.filterList(newText);
                return true;
            }
        });
    }

    @Override
    //Fab button listener
    public void onClick(View v) {
        ((MainActivity) getActivity()).showFragment(new NewContactFragment(), TAG);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            //TODO: sort by the first letter = A-Z
        }
        return super.onOptionsItemSelected(item);
    }

}


