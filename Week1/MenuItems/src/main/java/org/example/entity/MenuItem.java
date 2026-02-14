package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private String category;

    @Column
    private boolean available;

    // Constructors
    public MenuItem() {
    }

    public MenuItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
