package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;

public class ContactItem extends SugarRecord {

    String item;
    Integer type;
    Contact contact;

    public ContactItem() {
    }

    public ContactItem(String item, Integer type) {
        this.item = item;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
