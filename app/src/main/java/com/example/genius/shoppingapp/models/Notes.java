package com.example.genius.shoppingapp.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by genius on 12/29/2016.
 */

public class Notes extends RealmObject {

    int id;
    String notes;
    Date date;



    public Notes() {
    }

    public Notes(int id,Date date, String notes) {
        this.id = id;
        this.notes = notes;
        this.date=date;
    }

    public Notes(int id, Date date) {
        this.id = id;
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
