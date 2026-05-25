/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

public class Login {
    

    private String username;
    private String password;

    // Username Validation
    //Checks if the username has an underscore and is no more than 5 characters in length
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Password Validation
    //Checks if the password has a capital letter, has a number, has a special character, and makes sure it is atleast 8 characters long
    public boolean checkPasswordComplexity(String password) {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) { 
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // Phone Number Validation
    //Checks if the cellphone number is 12 characters in length and has the correct international code
    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("+27") && phoneNumber.length() == 12;
    }

    // Register User
    //This is where the user registers themselves into the app
    public String registerUser(String username, String password, String phoneNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted. Must contain '_' and be ≤ 5 characters.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password must be ≥ 8 chars, include capital letter, number, and special character.";
        }

        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Phone number must start with +27 and be 12 digits long.";
        }

        this.username = username;
        this.password = password;

        return "User registered successfully.";
    }

    // Login
    //This is where the user inputs their login information
    public boolean loginUser(String username, String password) {
        return this.username != null &&
               this.password != null &&
               this.username.equals(username) &&
               this.password.equals(password);
    }

    // Login Status Message
    //This is what the user will see after they have entered their login details correctly
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}


    

