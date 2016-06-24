package com.seznam_kontaktu.seznamkontaktu.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //infate the layout
        View contactView = layoutInflater.inflate(R.layout.item_contact, parent, false);

        //View holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {

        //Get the data model based on position
        Contact contact = mContacts.get(position);

        //Set textview - name
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        //public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.contact_name);
            //image here?
        }

    }
    private List<Contact> mContacts;
    private Context mContext;

    public ContactsAdapter(Context context, List<Contact> contacts) {
        mContext = context;
        mContacts = contacts;
    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


}