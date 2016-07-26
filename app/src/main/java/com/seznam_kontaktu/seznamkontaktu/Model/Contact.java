package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class Contact extends SugarRecord {

    public String name;
    private String number;
    private String email;
    private boolean favourite;
    @Ignore private boolean visibleFirstLetter;

    public Contact() {
    }

    public Contact(String name, String number, String email, boolean favourite) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.favourite = favourite;
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
        this.favourite = isFavourite;
    }

    public void setVisibleFirstLetter(Boolean visibleFirstLetter) {
        this.visibleFirstLetter = visibleFirstLetter;
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
        return favourite;
    }

    public boolean IsVisibleFirstLetter() {
        return visibleFirstLetter;
    }

}
