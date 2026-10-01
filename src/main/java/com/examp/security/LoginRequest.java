package com.examp.security;

public class LoginRequest {
    String Username;
    String Password;

    public LoginRequest(){

    }

    public LoginRequest(String Password, String Username){
        this.Password=Password;
        this.Username=Username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
