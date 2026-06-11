/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesTest {

    //This will test if all methods are correct
    private Messages message;

    @BeforeEach
    public void setUp() throws IOException {

        Messages.resetPart3Data();
        Path testMessageFile = Path.of("target", "test-messages.json");
        Files.deleteIfExists(testMessageFile);
        Messages.setMessageFileForTesting(testMessageFile);
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

    @Test
    public void testSentMessagesArray_correctlyPopulated() {

        Messages msg1 =
                new Messages("+27834557896", "Did you get the cake?");
        Messages msg4 =
                new Messages("0838884567", "It is dinner time!", "0838884567");

        msg1.sentMessage(1);
        msg4.sentMessage(1);

        assertTrue(Messages.getSentMessages().contains("Did you get the cake?"));
        assertTrue(Messages.getSentMessages().contains("It is dinner time!"));
    }

    @Test
    public void testDisplayLongestMessage_returnsCorrectMessage() {

        Messages.getStoredMessages().add("Did you get the cake?");
        Messages.getStoredMessages().add("Where are you? You are late! I have asked you to be on time.");
        Messages.getStoredMessages().add("Yohoooo, I am at your gate.");
        Messages.getStoredMessages().add("It is dinner time!");
        Messages.getStoredMessages().add("Ok, I am leaving without you.");

        String expected =
                "Where are you? You are late! I have asked you to be on time.";

        assertEquals(expected, Messages.displayLongestStoredMessage());
    }

    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {

        Messages msg4 =
                new Messages("0838884567", "It is dinner time!", "0838884567");
        msg4.sentMessage(1);

        String result = Messages.searchByMessageID("0838884567");

        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {

        Messages msg2 =
                new Messages("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Messages msg5 =
                new Messages("+27838884567", "Ok, I am leaving without you.");

        msg2.sentMessage(1);
        msg5.sentMessage(1);

        String result = Messages.searchByRecipient("+27838884567");

        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash_removesCorrectMessage() {

        Messages msg2 =
                new Messages("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage(1);

        String result = Messages.deleteByHash(msg2.getMessageHash());

        assertEquals("Message: Where are you? You are late! I have asked you to be on time. successfully deleted.", result);
        assertFalse(Messages.getSentMessages().contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testDisplayReport_containsRequiredFields() {

        Messages msg1 =
                new Messages("+27834557896", "Did you get the cake?");
        msg1.sentMessage(1);

        String report = Messages.displayStoredReport();

        assertTrue(report.contains(msg1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}
