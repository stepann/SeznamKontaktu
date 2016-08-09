package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.seznam_kontaktu.seznamkontaktu.Model.ContactItem;
import com.seznam_kontaktu.seznamkontaktu.R;

import java.util.ArrayList;
import java.util.List;

public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.ViewHolder> {

    ArrayList<String> spinnerItems;
    ArrayAdapter adapter;
    ContactItem contactItem;
    Context mContext;
    private List<ContactItem> items;

    public NewContactAdapter(List<ContactItem> contactItems) {
        this.items = contactItems;
    }

    public NewContactAdapter(Context context, List<ContactItem> contactItems) {
        this.mContext = context;
        this.items = contactItems;
        this.spinnerItems = new ArrayList<>();
        fillOptionsAdapter();
        this.adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_new_contact, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText phoneNumber;
        Spinner spinner;
        ImageButton deleteBtn;
        EditTextListener listener;

        public ViewHolder(View rowView) {
            super(rowView);
            phoneNumber = (EditText) rowView.findViewById(R.id.ed_phoneNumber);
            spinner = (Spinner) rowView.findViewById(R.id.spinner);
            deleteBtn = (ImageButton) rowView.findViewById(R.id.imgButtonMinus);
            listener = new EditTextListener(phoneNumber);
            phoneNumber.addTextChangedListener(listener);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.listener.updatePosition(position);
        contactItem = items.get(holder.getAdapterPosition());
        holder.phoneNumber.setText(contactItem.getItem());

        //delete item on the position
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.size() > 1) {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        //set data to spinner adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);

        /*index of selected item from ArrayAdapter
         returns -1 if nothing is selected
         */
        int selectedItem = adapter.getPosition(contactItem.getType());
        holder.spinner.setSelection(selectedItem);


        //spinner set value to List
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = holder.spinner.getSelectedItem().toString();
                items.get(position).setType(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //custom textWatcher to save data to items (List)
    private class EditTextListener implements TextWatcher {

        private int position;
        private EditText editText;

        public EditTextListener(EditText ed) {
            this.editText = ed;
        }

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            if (charSequence.length() == 0) return;

            if (editText.getId() == R.id.ed_phoneNumber) {
                if (String.valueOf(items.get(position).getItem()).equals(charSequence.toString())) {
                    //do nothing
                } else {
                    items.get(position).setItem(charSequence.toString());
                }

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    //set ContactItem types to spinner adapter
    private void fillOptionsAdapter() {
        spinnerItems.add("OSOBNÍ");
        spinnerItems.add("DOMŮ");
        spinnerItems.add("PRÁCE");
        spinnerItems.add("FAX");
    }

}

