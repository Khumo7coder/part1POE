/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1project;

/**
 *
 * @author Student
 */


public class Login {

    String username;
    String password;
    String cellPhoneNumber;
    String firstName;
    String lastName;

    public Login(String username, String password, String cellPhoneNumber,
                 String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName() {

        if (username.contains("_") && username.length() <= 5) {
            return true;
        }

        return false;
    }

    public boolean checkPasswordComplexity() {

        if (password.length() < 8) {
            return false;
        }

        boolean capital = false;
        boolean number = false;
        boolean special = false;

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                capital = true;
            }

            if (Character.isDigit(c)) {
                number = true;
            }

            if (!Character.isLetterOrDigit(c)) {
                special = true;
            }
        }

        return capital && number && special;
    }

    /*
     * Regex reference:
     * Oracle Java Documentation - Pattern
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html
     */
    public boolean checkCellPhoneNumber() {

        return cellPhoneNumber.matches("^\\+27\\d{9}$");
    }

    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Username successfully captured.";
    }

    public boolean loginUser(String username, String password) {

        if (this.username.equals(username)
                && this.password.equals(password)) {
            return true;
        }

        return false;
    }

    public String returnLoginStatus(boolean loginSuccessful) {

        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
    

