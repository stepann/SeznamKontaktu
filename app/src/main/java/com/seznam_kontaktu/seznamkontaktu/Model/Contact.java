package com.seznam_kontaktu.seznamkontaktu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Contact extends SugarRecord /*implements Parcelable*/{

    public String name;
    private String number;
    private String email;
    private boolean isFavourite;

    public Contact() {
    }

    public Contact(String name, String email, String number, Boolean isFavourite) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean getFavourite() {
        return isFavourite;
    }

    //Parcelable
    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(email);
        dest.writeBooleanArray(new boolean[]{isFavourite});
    }

    protected Contact(Parcel in) {
        name = in.readString();
        number = in.readString();
        email = in.readString();
        isFavourite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };*/
}
