package com.example.volunteerplus;

public class User {
    public String id, EmailAddress, Password, FirstName, LastName, BirthDay, InviteCode;

    public User() {
    }

    public User(String id, String emailAddress, String password, String firstName, String lastName, String birthDay, String inviteCode) {
        this.id = id;
        EmailAddress = emailAddress;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        BirthDay = birthDay;
        InviteCode = inviteCode;
    }
}
