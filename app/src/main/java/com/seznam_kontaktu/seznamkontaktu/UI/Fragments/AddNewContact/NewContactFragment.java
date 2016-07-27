package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.AddNewContact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.orm.SugarRecord;
import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.ContactListFragment;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewContactFragment extends Fragment {

    public static final String CONTACT = "contact";
    private static final int RESULT_LOAD_IMAGE = 1;

    EditText etName, etNumber, etEmail;
    CircleImageView ivAvatar;

    String mName, mNumber, mEmail, picturePath;
    boolean isFavourite;
    boolean editingContact;

    Long positionID;

    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance(Long position, boolean isEditing) {
        NewContactFragment fragment = new NewContactFragment();
        Bundle args = new Bundle();
        args.putLong("position", position);
        args.putBoolean("isEditing", isEditing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);

        editingContact = getArguments().getBoolean("isEditing");
        positionID = getArguments().getLong("position");

        ivAvatar = (de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.iv_person_image);
        etName = (EditText) view.findViewById(R.id.ed_name);
        etNumber = (EditText) view.findViewById(R.id.ed_number);
        etEmail = (EditText) view.findViewById(R.id.ed_email);

        if (editingContact) {
            Contact contact = Contact.findById(Contact.class, positionID);
            String name = contact.getName();
            String number = contact.getNumber();
            String email = contact.getEmail();
            picturePath = contact.getImageUri();
            isFavourite = contact.getFavourite();

            etName.setText(name);
            etNumber.setText(number);
            etEmail.setText(email);
            ivAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //set title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.create_new_contact);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_contact_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (checkInputs()) {
                    if (editingContact) {
                        //edit contact
                        Contact contact = SugarRecord.findById(Contact.class, positionID);
                        contact.setName(mName);
                        contact.setNumber(mNumber);
                        contact.setEmail(mEmail);
                        contact.setFavourite(isFavourite);
                        contact.setImageUri(picturePath);
                        contact.save();
                    } else {
                        //save new contact
                        Contact contact = new Contact();
                        contact.setName(mName);
                        contact.setNumber(mNumber);
                        contact.setEmail(mEmail);
                        contact.setFavourite(isFavourite);
                        contact.setImageUri(picturePath);
                        contact.save();
                    }
                    getFragmentManager().popBackStack(); //delete backStack
                    ((MainActivity) getActivity()).showFragmentWithoutBackStack(new ContactListFragment(), CONTACT);
                }
                break;

            case R.id.action_favourite:
                isFavourite = !isFavourite;
                getActivity().invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (isFavourite) {
            menu.getItem(0).setIcon(R.drawable.ic_star_white_24dp);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_star_border_white_24dp);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public boolean checkInputs() {
        mName = etName.getText().toString();
        mNumber = etNumber.getText().toString();
        mEmail = etEmail.getText().toString();

        if (mName.isEmpty() || mName.trim().length() <= 2) {
            etName.setError(getText(R.string.empty_name));
            return false;
        }
        if (mNumber.isEmpty()) {
            etNumber.setError(getText(R.string.empty_number));
            return false;
        }
        if (!mEmail.isEmpty() && !mEmail.contains("@")) {
            etEmail.setError(getText(R.string.invalid_email));
            return false;
        }
        return true;
    }
    //permission PHOTO/MEDIA/FILES
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean shouldShowRequestPermissionRationale(NewContactFragment newContactFragment, String readExternalStorage) {
        return true;
    }

    //select image from gallery
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            ivAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            cursor.close();
        }
    }
}
