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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.orm.SugarRecord;
import com.seznam_kontaktu.seznamkontaktu.Adapter.NewContactAdapter;
import com.seznam_kontaktu.seznamkontaktu.MainActivity;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.Model.ContactItem;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.ContactListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewContactFragment extends Fragment {

    public static final String CONTACT = "contact";
    private static final int RESULT_LOAD_IMAGE = 1;

    EditText etName, etEmail, etPhone;
    Button addButton;
    Spinner spinner;

    CircleImageView ivAvatar;
    RecyclerView recyclerView;
    NewContactAdapter mAdapter;
    ArrayAdapter<String> adapterItems;

    String mName, mEmail, picturePath;
    boolean isFavourite;
    boolean editingContact;
    Long positionID;

    List<ContactItem> itemList;

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
        itemList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);

        etName = (EditText)view.findViewById(R.id.ed_name);
        etEmail = (EditText)view.findViewById(R.id.ed_email);
        etPhone = (EditText) view.findViewById(R.id.ed_phoneNumber);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        addButton = (Button) view.findViewById(R.id.btn);

        ivAvatar = (CircleImageView)view.findViewById(R.id.iv_person_image);

        //spinner adapter
        String item[] = {"PRÁCE", "OSOBNÍ", "DOMŮ", "FAX"};
        adapterItems = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, item);
        adapterItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterItems);

        //recyclerView init
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewContactAdapter(getContext(), itemList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        editingContact = getArguments().getBoolean("isEditing");
        positionID = getArguments().getLong("position");

        if (editingContact) {
            getContact();
            fillViews();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.add(new ContactItem());
                mAdapter.notifyDataSetChanged();
            }
        });

        //check permission
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
                        saveEditedContact();
                    } else {
                        saveNewContact();
                    }
                    //delete fragments from backstack and show contact list
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

    /* input checking
     * @return true if inputs are OK
     */
    public boolean checkInputs() {
        mName = etName.getText().toString();
        mEmail = etEmail.getText().toString();

        if (mName.isEmpty() || mName.trim().length() <= 2) {
            etName.setError(getText(R.string.empty_name));
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
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            ivAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            cursor.close();
        }
    }
    //get contact from database
    public void getContact() {
        Contact contact = Contact.findById(Contact.class, positionID);
        itemList = ContactItem.find(ContactItem.class, "contact = ?", String.valueOf(contact.getId()));
        mName = contact.getName();
        mEmail = contact.getEmail();
        picturePath = contact.getImagePath();
        isFavourite = contact.isFavourite();
    }

    //set text to EditTexts and load avatar
    public void fillViews() {
        etName.setText(mName);
        etEmail.setText(mEmail);
        if (picturePath != null) ivAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

    //save edited contact to position by ID
    public void saveEditedContact() {
        Contact contact = SugarRecord.findById(Contact.class, positionID);
        contact.setName(mName);
        contact.setEmail(mEmail);
        contact.setFavourite(isFavourite);
        contact.setImagePath(picturePath);
        contact.save();
    }

    public void saveNewContact() {
        Contact contact = new Contact(mName, mEmail, isFavourite, picturePath);
        itemList.size();

        /* for (int i = 0; i < itemList.size(); i++) {
            ContactItem contactItem = new ContactItem();
            contactItem.setContact(contact);
            contactItem.setItem();
            contactItem.setType();
        }*/

        contact.save();
    }

}

