package com.example.noteapp.models;

public class User {
    private String _id;
    private String firstName, lastName, email, password;

    public User(String _id, String firstName, String lastName, String email, String password) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
