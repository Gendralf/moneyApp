package com.example.moneyapp;


public class User {
    private int id;
    private float balance;
    private String name;
    private String phone;

    public User(int id, float balance, String name, String phone) {
        this.id = id;
        this.balance = balance;
        this.name = name;
        this.phone = phone;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
