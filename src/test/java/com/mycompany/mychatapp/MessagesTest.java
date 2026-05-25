/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesTest {

    //This will test if all methods are correct
    private Messages message;

    @BeforeEach
    public void setUp() {

        message = new Messages("+27123456789", "Hi tonight");
    }

    // This will test the message ID

    @Test
    public void testCheckMessageID_Success() {

        assertTrue(message.checkMessageID());
    }

    // This will test if the recipients cellphone number is correct
    @Test
    public void testCheckRecipientCell_Success() {

        String expected =
                "Cell phone number successfully captured.";

        assertEquals(expected, message.checkRecipientCell());
    }

    //This will test if when the incorrect cellphone number is entered
    @Test
    public void testCheckRecipientCell_Failure() {

        Messages msg =
                new Messages("0812345678", "Hello");

        String expected =
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        assertEquals(expected, msg.checkRecipientCell());
    }

    // This will test if the message length is correct
    @Test
    public void testMessageLength_Success() {

        Messages msg =
                new Messages("+27123456789", "Hello there");

        String expected = "Message ready to send.";

        assertEquals(expected, msg.checkMessageLength());
    }

    //This will test if when the incorrect message legth is entered
    @Test
    public void testMessageLength_Failure() {

        String longText = "A".repeat(260);

        Messages msg =
                new Messages("+27123456789", longText);

        String expected =
                "Message exceeds 250 characters by 10, please reduce the size.";

        assertEquals(expected, msg.checkMessageLength());
    }

    // This will test the message hash method

    @Test
    public void testCreateMessageHash() {

        String hash = message.createMessageHash();

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    // This will test if option one is chosen
    @Test
    public void testSentMessage_Send() {

        String result = message.sentMessage(1);

        assertEquals("Message successfully sent.", result);
    }

    //This will test if option two is chosen
    @Test
    public void testSentMessage_Discard() {

        String result = message.sentMessage(2);

        assertEquals("Press 0 to delete the message.", result);
    }

    //This will test if option three is chosen
    @Test
    public void testSentMessage_Store() {

        String result = message.sentMessage(3);

        assertEquals("Message successfully stored.", result);
    }

    // This will test the total messages sent
    @Test
    public void testReturnTotalMessages() {

        // create messages first
        Messages msg1 = new Messages("+27111111111", "Test 1");
        Messages msg2 = new Messages("+27222222222", "Test 2");

        msg1.sentMessage(1);
        msg2.sentMessage(1);

        assertTrue(Messages.returnTotalMessages() >= 2);
    }

    // This will test the print message 
    @Test
    public void testPrintMessages() {

        assertDoesNotThrow(() -> {

            message.printMessages();
        });
    }
}