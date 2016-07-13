package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.ContactListFragment;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.SplashScreen.SplashFragment;

import butterknife.ButterKnife;

public class NewContactFragment extends Fragment {


    EditText name, number, email;
    String mName, mNumber, mEmail;

    public NewContactFragment() {
        // Required empty public constructor
    }
    public static NewContactFragment newInstance(String param1, String param2) {
        NewContactFragment fragment = new NewContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //set title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.new_contact);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);

        name = (EditText)view.findViewById(R.id.ed_name);
        number = (EditText)view.findViewById(R.id.ed_number);
        email = (EditText)view.findViewById(R.id.ed_email);

        mName = name.getText().toString();
        mNumber = number.getText().toString();
        mEmail = email.getText().toString();

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_contact_menu, menu);
    }
    boolean isFavourite = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                //TODO: safe editTexts and return to ContactListFragment
                /*Contact contact = new Contact(mName,mNumber,mEmail,isFavourite);
                contact.save();*/
                break;
            case R.id.action_favourite:
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getContext(), "isFavourite is: " + isFavourite, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(isFavourite) {
            menu.getItem(0).setIcon(R.drawable.ic_star_white_24dp);
            isFavourite = false;
        }
        else {
            menu.getItem(0).setIcon(R.drawable.ic_star_border_white_24dp);
            isFavourite = true;
        }
        super.onPrepareOptionsMenu(menu);
    }


}
