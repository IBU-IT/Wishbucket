package com.ibuproject.wishbucket;

import com.ibuproject.wishbucket.controllers.*;

public class Main {

    public static void main(String[] args) {

        LoginController loginController = new LoginController();

        // Start application
        loginController.startController();
    }
}
