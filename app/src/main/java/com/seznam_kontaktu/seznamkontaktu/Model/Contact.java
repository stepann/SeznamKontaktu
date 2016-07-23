package com.seznam_kontaktu.seznamkontaktu.Model;

import android.databinding.Bindable;

import com.orm.SugarRecord;

import butterknife.BindView;

public class Contact extends SugarRecord {

    public String name;
    private String number;
    private String email;
    private boolean isFavourite;

    public Contact() {
    }

    public Contact(String name, String number, String email, boolean isFavourite) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.isFavourite = isFavourite;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public boolean getFavourite() {
        return isFavourite;
    }
}
