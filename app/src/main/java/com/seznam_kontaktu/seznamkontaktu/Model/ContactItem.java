package com.seznam_kontaktu.seznamkontaktu.Model;

public class ContactItem {

    String content;
    Integer type;
    private Contact contact;

    public static final Integer HOME = 0;
    public static final Integer WORK = 1;
    public static final Integer EMAIL = 2;
    public static final Integer FAX = 3;

    public ContactItem() {
        //empty constructor
    }

    public ContactItem(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Integer getHOME() {
        return HOME;
    }

    public static Integer getWORK() { return WORK; }

    public static Integer getFAX() { return FAX; }

    public static Integer getEMAIL() {
        return EMAIL;
    }

    public String getContent() { return content; }

    public Integer getType() { return type; }

    public Contact getContact() { return contact; }

    public void setContact(Contact contact) { this.contact = contact; }

}
