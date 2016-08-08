package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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

import butterknife.ButterKnife;

public class ContactListFragment extends Fragment implements View.OnClickListener {

    private static final String NEW_CONTACT = "newcontact";
    private static final String DIALOG = "dialog";

    FloatingActionButton fabButton;
    EditText searchView;
    ImageButton btnClearText;
    RecyclerView recyclerView;

    String name;
    Long positionID;
    Contact contact;

    private List<Contact> mContact;
    private List<Contact> dataSource;

    private ContactsRecyclerAdapter mAdapter;

    public static ContactListFragment newInstance() {
        Bundle args = new Bundle();
        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContact = new ArrayList<>();
        dataSource = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        searchView = (EditText) view.findViewById(R.id.search);

        fabButton = (FloatingActionButton) view.findViewById(R.id.fab_button);
        fabButton.setOnClickListener(this);

        btnClearText = (ImageButton) view.findViewById(R.id.ib_clearText);
        btnClearText.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContact = SugarRecord.listAll(Contact.class);
        mAdapter = new ContactsRecyclerAdapter(getContext(), mContact);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        sortList();

        //click on row item
        mAdapter.setOnItemClickListener(new ContactsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (dataSource.isEmpty()) contact = mContact.get(position);
                else contact = dataSource.get(position);

                name = contact.getName();
                positionID = contact.getId();
                showAlertDialog();
            }
        });

        addTextListener();

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
        switch (v.getId()) {
            case R.id.fab_button:
                getFragmentManager().popBackStack();
                ((MainActivity) getActivity()).showFragmentWithBackStack(new NewContactFragment().newInstance(0L, false), NEW_CONTACT);
                break;
            case R.id.ib_clearText:
                searchView.setText(null);
                searchView.clearFocus();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter)
            reverseList();
        return super.onOptionsItemSelected(item);
    }

    public void addTextListener() {
        searchView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                dataSource.clear();
                query = query.toString().toLowerCase();
                for (int i = 0; i < mContact.size(); i++) {
                    final String text = (mContact.get(i).getName().toLowerCase());
                    if (text.contains(query)) {
                        dataSource.add(mContact.get(i));
                    }
                }
                recyclerViewRefresh();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showAlertDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        ContactDialogFragment contactDialogFragment = ContactDialogFragment.newInstance(positionID);
        contactDialogFragment.show(fragmentManager, DIALOG);
    }

    public void recyclerViewRefresh() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactsRecyclerAdapter(getContext(), dataSource);
        recyclerView.setAdapter(mAdapter);
    }

    public void sortList() {
        Collections.sort(mContact, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public void reverseList() {
        if (!dataSource.isEmpty()) {
            Collections.reverse(dataSource);
            mAdapter.notifyDataSetChanged();
        } else {
            Collections.reverse(mContact);
            mAdapter.notifyDataSetChanged();
        }
    }

}

