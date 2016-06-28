package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.RecyclerView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactVH> {

    List<Contact> contact;
    Context context;

    public ContactsAdapter(Context context, List<Contact> contact) {
        this.contact = contact;
        this.context = context;
    }

    @Override
    public ContactVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_contact, parent, false);
        ContactVH viewHolder = new ContactVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactVH holder, int position) {
        holder.name.setText(contact.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contact.size();
    }

    class ContactVH extends RecyclerView.ViewHolder {
        @BindView(R.id.contact_name) TextView name;

        public ContactVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}