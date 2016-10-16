package com.example.zt.httpforzha;

/**
 * Created by zt on 16/9/30.
 */
public class User {

    public String email;
    public String username;

    @Override
    public String toString() {
        return username + " : " + email;
    }
}
