package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;

public class Contact extends SugarRecord {

    private String mName;
    private String mNumber;
    private String mEmail;

    public Contact() {
    }


    public Contact(String name, String number, String email) {
        this.mName = name;
        this.mNumber = number;
        this.mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }
}
