package com.workshop.refactored.model;

public class Customer {
    private final int id;
    private final String name;
    private final String email;
    private final boolean premium;

    public Customer(int id, String name, String email, boolean premium) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.premium = premium;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isPremium() { return premium; }
}
