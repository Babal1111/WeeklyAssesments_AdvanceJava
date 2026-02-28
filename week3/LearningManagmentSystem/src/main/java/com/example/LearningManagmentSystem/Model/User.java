package com.example.LearningManagmentSystem.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {

    private int id;
    private String name,email,password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    @NotBlank(message = "The name is mandatory")
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    @Email(message = "Give right/fromatted email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    @Size(min = 6,message = "Min 3 char in password")
    public void setPassword(String password) {
        this.password = password;
    }
}
