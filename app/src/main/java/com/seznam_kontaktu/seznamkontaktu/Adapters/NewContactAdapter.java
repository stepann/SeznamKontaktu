package com.seznam_kontaktu.seznamkontaktu.Adapters;

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

import java.util.List;

public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.BindingHolder> {

    private List<Contact> mContacts;
    Context mContext;

    public NewContactAdapter(Context context, List<Contact> contact) {
        this.mContext = context;
        this.mContacts = contact;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(final View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            //implement click listener here
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
        final Contact contact = mContacts.get(position);

        holder.getBinding().setVariable(BR.contact, contact);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}
