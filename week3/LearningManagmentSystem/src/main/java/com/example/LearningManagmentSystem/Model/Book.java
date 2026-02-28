package com.example.LearningManagmentSystem.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Book {

    private int id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Author should not be blank")
    private String author;

    @Min(value = 1, message = "Price should be greater than 0")
    private double price;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}