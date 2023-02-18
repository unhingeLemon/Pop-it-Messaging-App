package com.example.mobile_programming_project;

public class User {

    public String  email,id,username;

    public User(){}

    public User(String username, String email,String id){
        this.username = username;
        this.email = email;
        this.id = id;

    }
    public String getUsername() {
        return username;
    }
    public String getId() {
        return id;
    }
    public String getEmail(){
        return email;
    }


}
