package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.Dialog;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orm.SugarRecord;
import com.seznam_kontaktu.seznamkontaktu.Adapter.DialogAdapter;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.Model.ContactItem;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact.NewContactFragment;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.ContactListFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactDialogFragment extends DialogFragment {

    private static final int ACTION_CALL = 1;

    TextView mName, mEmail;
    String name, number, email;
    RecyclerView recyclerView;
    long positionID;

    List<ContactItem> items;
    DialogAdapter mAdapter;

    public ContactDialogFragment() {
        //empty constructor
    }

    public static ContactDialogFragment newInstance(Long position) {
        ContactDialogFragment fragment = new ContactDialogFragment();
        Bundle args = new Bundle();
        args.putLong("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_detail_contact, container, false);

        //item click
        mAdapter.setOnItemClickListener(new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                getPermissionToCall(position);
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        items = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail_contact, null);

        mName = (TextView)view.findViewById(R.id.tv_name);
        mEmail = (TextView)view.findViewById(R.id.tv_email);
        positionID = getArguments().getLong("position");

        //get name and email from Contact
        Contact contact = Contact.findById(Contact.class, positionID);
        name = contact.getName();
        email = contact.getEmail();

        //set them to the views
        mName.setText(name);
        mEmail.setText(email);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = ContactItem.find(ContactItem.class, "contact = ?", String.valueOf(positionID));
        mAdapter = new DialogAdapter(getContext(), items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        builder.setNeutralButton(R.string.dialog_deleteContact, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Contact contact = SugarRecord.findById(Contact.class, positionID);
                ContactItem.deleteAll(ContactItem.class, "contact = ?", String.valueOf(positionID));
                contact.delete();
                getFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ContactListFragment()).commit();
            }
        });
        builder.setPositiveButton(R.string.dialog_editContact, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame_layout, new NewContactFragment().newInstance(positionID, true)).commit();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void callContact(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    public void getPermissionToCall(int position) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, ACTION_CALL);
        } else {
            String contactItem = items.get(position).getItem();
            callContact(contactItem);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == ACTION_CALL) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private boolean shouldShowRequestPermissionRationale(ContactDialogFragment contactDialogFragment, String callPhone) {
        return true;
    }
}
