package com.mycompany.part1project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testValidUsername() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testInvalidUsername() {
        Login user = new Login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testValidPassword() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {
        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "John",
                "Doe"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testValidCellPhoneNumber() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellPhoneNumber() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "John",
                "Doe"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUnsuccessful() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertFalse(user.loginUser("wrong", "wrong"));
    }

    @Test
    public void testSuccessfulRegistrationMessage() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertEquals("Username successfully captured.", user.registerUser());
    }

    @Test
    public void testInvalidUsernameRegistrationMessage() {
        Login user = new Login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Doe"
        );

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser()
        );
    }

    @Test
    public void testInvalidPasswordRegistrationMessage() {
        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "John",
                "Doe"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.registerUser()
        );
    }
}