package com.seznam_kontaktu.seznamkontaktu.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.R;
import com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.SearchViewFilter.SearchViewFilter;

import java.util.List;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.BindingHolder> {

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        ContactsRecyclerAdapter.listener = listener;
    }

    private List<Contact> mContacts;
    private List<Contact> mContactsFiltered;
    SearchViewFilter filter;
    Context mContext;

    public ContactsRecyclerAdapter(Context context, List<Contact> contact) {
        this.mContext = context;
        this.mContacts = contact;
        this.mContactsFiltered = contact;
        filter = new SearchViewFilter(mContacts, this);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(final View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_contact, parent, false);
        BindingHolder holder = new BindingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Contact contact = mContactsFiltered.get(position);
        holder.getBinding().setVariable(BR.contact, contact);
        holder.getBinding().executePendingBindings();
    }
    @Override
    public int getItemCount() {
        return mContactsFiltered.size();
    }

    public void setList(List<Contact> contact) {
        this.mContacts = contact;
    }

    public void filterList(String text) {
        filter.filter(text);
    }
  }
