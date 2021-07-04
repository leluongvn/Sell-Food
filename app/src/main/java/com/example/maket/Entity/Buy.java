package com.example.maket.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "buy_db")
public class Buy {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "price")
    private Double price;
    @ColumnInfo(name = "day")
    private int day;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;



    public Buy(Double price, int day, int month, int year) {
        this.price = price;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
