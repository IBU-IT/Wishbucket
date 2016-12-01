package com.ibuproject.wishbucket.controllers;

import com.ibuproject.wishbucket.models.*;
import com.ibuproject.wishbucket.views.*;

public class LoginController {
    
    public void startController() {
        // View the application's GUI
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    public void tryLogin(FbLoginModel fbLoginModel) {
        //call api, fetch data
    }

}
