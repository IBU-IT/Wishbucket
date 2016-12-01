package com.ibuproject.wishbucket.views;

import com.ibuproject.wishbucket.controllers.LoginController;

import javax.swing.*;
import java.awt.*;


public class LoginView extends JFrame {

    private LoginController loginController = new LoginController();

    private JPanel panel;
    private JTextField usernameField;
    private JTextField passwordField;

    public LoginView() throws HeadlessException {

        setContentPane(panel);
        setSize(500, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("LoginPage");

        //When CTA is pressed call loginController.tryLogin(fbLoginModel)
    }

}
