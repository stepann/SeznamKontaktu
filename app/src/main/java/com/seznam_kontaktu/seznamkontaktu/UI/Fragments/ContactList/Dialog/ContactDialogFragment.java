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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact.NewContactFragment;

public class ContactDialogFragment extends DialogFragment {

    private static final String NEW_CONTACT = "newcontact";
    private static final int ACTION_CALL = 1;

    TextView mName, mNumber, mEmail;
    String name, number, email;

    public ContactDialogFragment() {
        //empty constructor
    }

    public static ContactDialogFragment newInstance(String name, String number, String email) {
        ContactDialogFragment fragment = new ContactDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("number", number);
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_detail_contact, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail_contact, null);

        mName = (TextView)view.findViewById(R.id.tv_name);
        mNumber = (TextView)view.findViewById(R.id.tv_number);
        mEmail = (TextView)view.findViewById(R.id.tv_email);

        name = getArguments().getString("name");
        number =getArguments().getString("number");
        email = getArguments().getString("email");

        mName.setText(name);
        mNumber.setText(number);
        mEmail.setText(email);

        builder.setNeutralButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialog_callContact, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPermissionToCall();
            }
        });
        builder.setPositiveButton(R.string.dialog_editContact, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).showFragmentWithBackStack(new NewContactFragment(), NEW_CONTACT);
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

    public void getPermissionToCall() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, ACTION_CALL);
        }
        else {
            callContact(number);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == ACTION_CALL) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE);
                if (showRationale) {
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private boolean shouldShowRequestPermissionRationale(ContactDialogFragment contactDialogFragment, String callPhone) {
        return true;
    }
}
