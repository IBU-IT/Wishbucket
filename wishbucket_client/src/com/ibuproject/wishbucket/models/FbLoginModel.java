package com.ibuproject.wishbucket.models;

import com.ibuproject.wishbucket.models.*;
import com.ibuproject.wishbucket.views.*;

public class FbLoginModel {

    private String username;
    private String password;

    public FbLoginModel() {
    }

    public FbLoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
