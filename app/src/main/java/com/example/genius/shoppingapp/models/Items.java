package com.example.genius.shoppingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by genius on 12/2/2016.
 */

public class Items extends RealmObject {
//        @PrimaryKey
    private int id;

    private String shope_name;
    private double amount;
    private int count;
    private Date date;

    public Items() {

    }

    public Items(String shope_name) {
        this.shope_name = shope_name;
    }

    //
    public Items(int id, String shope_name, double amount, int count, Date date) {
        this.id = id;
        this.shope_name = shope_name;
        this.amount = amount;
        this.count = count;
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getShope_name() {
        return shope_name;
    }

    public int getCount() {
        return count;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShope_name(String shope_name) {
        this.shope_name = shope_name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

