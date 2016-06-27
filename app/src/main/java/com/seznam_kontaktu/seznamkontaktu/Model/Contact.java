package com.seznam_kontaktu.seznamkontaktu.Model;

import com.orm.SugarRecord;

public class Contact extends SugarRecord {

    private String NAME;
    private String NUMBER;
    private String EMAIL;

    public Contact() {
    }

    public Contact(String NAME, String EMAIL, String NUMBER) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.NUMBER = NUMBER;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }
}

