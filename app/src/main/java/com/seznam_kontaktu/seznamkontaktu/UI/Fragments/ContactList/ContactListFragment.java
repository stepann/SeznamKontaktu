package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
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
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;

import com.seznam_kontaktu.seznamkontaktu.Adapter.ContactsRecyclerAdapter;
import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact.NewContactFragment;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.Dialog.ContactDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;

public class ContactListFragment extends Fragment implements View.OnClickListener {

    private static final String NEW_CONTACT = "newcontact";
    private static final String DIALOG = "dialog";
    boolean isSortByA = false;

    FloatingActionButton fabButton;
    SearchView searchView;
    RecyclerView recyclerView;
    String name, number, email;


    private List<Contact> mContact = new ArrayList<>();
    private List<Contact> mContactFiltered = new ArrayList<>();

    private ContactsRecyclerAdapter mAdapter;

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


        fabButton = (FloatingActionButton) view.findViewById(R.id.fab_button);
        fabButton.setOnClickListener(this);

        searchView = (SearchView) view.findViewById(R.id.search_view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContact = SugarRecord.listAll(Contact.class);

        mAdapter = new ContactsRecyclerAdapter(getActivity(), mContact);
        recyclerView.setAdapter(mAdapter);

        //item click
        mAdapter.setOnItemClickListener(new ContactsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                Contact contact = mContact.get(position);

                name = contact.getName();
                number = contact.getNumber();
                email = contact.getEmail();

                showAlertDialog();
            }
        });

        searchFilter();
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
    public void onClick(View v) {
        ((MainActivity) getActivity()).showFragmentWithBackStack(new NewContactFragment(), NEW_CONTACT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter)
            sortList();
            return super.onOptionsItemSelected(item);
    }

    public void searchFilter() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filterList(newText);
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showAlertDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        ContactDialogFragment contactDialogFragment = ContactDialogFragment.newInstance(name, number, email);
        contactDialogFragment.show(fragmentManager, DIALOG);
    }

    public void sortList() {
        if (mContact.size() > 0) {
            if (!isSortByA) {
                Collections.sort(mContact, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact contact1, Contact contact2) {
                        return contact1.getName().compareToIgnoreCase(contact2.getName());
                    }
                });
                isSortByA = true;
            } else {
                Collections.reverse(mContact);
                isSortByA = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}

