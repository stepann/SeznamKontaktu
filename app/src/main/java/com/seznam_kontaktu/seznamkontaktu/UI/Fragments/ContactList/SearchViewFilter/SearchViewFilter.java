package com.seznam_kontaktu.seznamkontaktu.UI.Fragments.ContactList.SearchViewFilter;

import android.widget.Filter;

import com.seznam_kontaktu.seznamkontaktu.Model.Contact;
import com.seznam_kontaktu.seznamkontaktu.Adapter.ContactsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchViewFilter extends Filter {

    private List<Contact> contactList;
    private List<Contact> filteredContactList;
    private ContactsRecyclerAdapter adapter;

    public SearchViewFilter(List<Contact> contactList, ContactsRecyclerAdapter adapter) {
        this.adapter = adapter;
        this.contactList = contactList;
        this.filteredContactList = new ArrayList();
    }

    @Override
    public FilterResults performFiltering(CharSequence constraint) {
        filteredContactList.clear();
        final FilterResults results = new FilterResults();

        for (final Contact item : contactList) {
            if (item.getName().trim().contains(constraint) || item.getName().toLowerCase().trim().contains(constraint)) {
                filteredContactList.add(item);
            }
        }
        results.values = filteredContactList;
        results.count = filteredContactList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.setList(filteredContactList);
        adapter.notifyDataSetChanged();
    }
}