package com.example.groceryproject.Data;

public class Info {

    //Creating variables that will be used in the input dialog box for entering groceries
    String date;
    String text;
    int amount;
    double price;
    String id;

    public Info(){

    }

    //Constructor
    public Info(String date, String text, int amount, double price, String id) {
        this.date = date;
        this.text = text;
        this.amount = amount;
        this.price = price;
        this.id = id;
    }

    //Setters and getters

    public String getDate() {
        return  date;
    }

    public void setDate() {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
