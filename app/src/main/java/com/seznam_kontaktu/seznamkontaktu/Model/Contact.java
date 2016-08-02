package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

public class Contact extends SugarRecord {

    private String name;
    private String phoneNumber;
    private String email;
    private boolean favourite;
    private String imagePath;
    @Ignore private boolean visibleFirstLetter;
    public List<ContactItem> items;

    public Contact() {
        //empty constructor 
    }

    public Contact(String name, String number, String email, boolean favourite, String imagePath) {
        this.name = name;
        this.phoneNumber = number;
        this.email = email;
        this.favourite = favourite;
        this.imagePath = imagePath;
    }

    public void setName(String name) {this.name = name; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getEmail() { return email; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isFavourite() { return favourite; }

    public boolean isVisibleFirstLetter() {
        return visibleFirstLetter;
    }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public List<ContactItem> getItems() {
            return items;
    }

}
