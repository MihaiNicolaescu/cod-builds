package com.nicolaescugroup.codbuilds.Objects;

public class User {

    public String username;
    public String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){
        this.username = null;
        this.password = null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
