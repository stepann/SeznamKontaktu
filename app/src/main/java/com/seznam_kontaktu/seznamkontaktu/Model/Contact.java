package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

public class Contact extends SugarRecord {

    private String name;
    private String email;
    private boolean favourite;
    private String imagePath;
    @Ignore private boolean visibleFirstLetter;

    public Contact() {
        //empty constructor
    }

    public Contact(String name, String email, boolean favourite, String imagePath) {
        this.name = name;
        this.email = email;
        this.favourite = favourite;
        this.imagePath = imagePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public void setVisibleFirstLetter(Boolean visibleFirstLetter) {
        this.visibleFirstLetter = visibleFirstLetter;
    }
    public String getName() {
        return name;
    }

    public boolean isFavourite() { return favourite; }

    public boolean isVisibleFirstLetter() {
        return visibleFirstLetter;
    }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    List<Contact> getItems() {
        return Contact.find(Contact.class, "contact = ?", String.valueOf(getId()));
    }
}
