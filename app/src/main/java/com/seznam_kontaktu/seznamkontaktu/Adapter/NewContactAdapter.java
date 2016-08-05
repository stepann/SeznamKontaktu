package com.seznam_kontaktu.seznamkontaktu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.seznam_kontaktu.seznamkontaktu.Model.ContactItem;
import com.seznam_kontaktu.seznamkontaktu.R;

import java.util.List;

public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.ViewHolder> {

    ArrayAdapter<String> adapter;
    public String[] spinnerItems;
    private List<ContactItem> items;
    Context mContext;

    public NewContactAdapter(Context context, List<ContactItem> contactItems) {
        this.mContext = context;
        this.items = contactItems;
        this.spinnerItems = new String[]{"PRÁCE", "OSOBNÍ", "DOMŮ", "FAX"};
        this.adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText phoneNumber;
        Spinner spinner;
        public ImageButton deleteBtn;

        public ViewHolder(final View rowView) {
            super(rowView);
            phoneNumber = (EditText) rowView.findViewById(R.id.ed_phoneNumber);
            spinner = (Spinner) rowView.findViewById(R.id.spinner);
            deleteBtn = (ImageButton) rowView.findViewById(R.id.imgButtonMinus);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_new_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ContactItem contactItem = items.get(position);
        holder.phoneNumber.setText(contactItem.getItem());

        //delete item on position
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });
        //spinner set data to adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

