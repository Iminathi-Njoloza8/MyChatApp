/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {
    
    //This will test if all methods are correct
    Login login = new Login();
    
    @Test
    public void testValidUsername() {
        //This makes sure that the username han an underscore and has a length of no more than 5 characters
        assertTrue(login.checkUserName("kyl_1"));
    }
    
    @Test
    public void testInvalidUsername_NoUnderscore() {
        //This alerts the user thst the username has no unserscore
        assertFalse(login.checkUserName("kyl"));
    }
    
    @Test
    public void testInvalidUsername_TooLong() {
        //This alerts the user that the username is too long
        assertFalse(login.checkUserName("kyle!!!!!!!!!!"));
    }
    
    @Test
    public void testValidPassword() {
       //This is when the password is 8 characters long, has a capital, has a number, and has a special character.
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testInvalidPassword_NoCapital() {
        //When there are no capital letters.
        assertFalse(login.checkPasswordComplexity("pass123!"));
    }
    
    @Test
    public void testInvalidPassword_NoNumber() {
        //When there are no numbers.
        assertFalse(login.checkPasswordComplexity("Password!"));
    }
    
    @Test
    public void testInvalidPassword_NoSpecialChar() {
        //When there are no special characters.
        assertFalse(login.checkPasswordComplexity("Pass1234"));
    }
    
    @Test
    public void testValidPhoneNumber() {
        //When the phone number has the correct international code
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }
    
    @Test
    public void testInvalidPhoneNumber_NoSAcode() {
        //When the phone number does not contain the correct international code.
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
    
    @Test
    public void testInvalidPhoneNumber_WrongLength() {
        //When the phone number is not the correct length.
        assertFalse(login.checkCellPhoneNumber("+2712345678"));
    }
    
    //Register and login tests
    @Test
    public void testRegisterUser_Success() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("User registered successfully.", result);
    }
    
    @Test
    public void testLogin_Success() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    
    @Test
    public void testLogin_Failure() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongPass"));
    }
    
}
