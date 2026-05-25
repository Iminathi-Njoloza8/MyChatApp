/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Messages {

    private static int messageCount = 0;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    // Constructor
    public Messages(String recipient, String message) {

        this.messageID = generateMessageID();
        messageCount++;
        this.messageNumber = messageCount;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }
    

    // This will generate the random message ID
    private String generateMessageID() {

        Random random = new Random();

        long number =
                1000000000L +
                (long) (random.nextDouble() * 9000000000L);

        return String.valueOf(number);
    }

    // This is a validation of message ID.
    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    // This will validate the recipients cellphone number.
    public String checkRecipientCell() {

        if (recipient.startsWith("+27")
                && recipient.length() <= 13) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // This will validate the length of the message .
    public String checkMessageLength() {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            int exceeded = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + exceeded
                    + ", please reduce the size.";
        }
    }

    // This will create a message hash.
    public String createMessageHash() {

        String[] words = message.split(" ");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    // This will print the details of the message.
    public void printMessages() {

        System.out.println("\n========= MESSAGE DETAILS =========");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + message);
    }

    // This will return the number of messages sent.
    public static int returnTotalMessages() {

        return messageCount;
    }

    // This will store the messages in JSON
    public void storeMessage() {

        String json = "{\n"
                + "\"messageID\":\"" + messageID + "\",\n"
                + "\"messageHash\":\"" + messageHash + "\",\n"
                + "\"recipient\":\"" + recipient + "\",\n"
                + "\"message\":\"" + message + "\"\n"
                + "}";

        try {

            try (FileWriter file = new FileWriter("messages.json", true)) {
                file.write(json);
                file.write("\n");
            }

            System.out.println("Message successfully stored.");

        } catch (IOException e) {

            System.out.println("Error storing message.");
        }
    }
    
    public String sentMessage(int option) {

    switch (option) {

        //If a message is sent.
        case 1 -> {
            return "Message successfully sent.";
            }

        //If a message is disregarded.
        case 2 -> {
            return "Press 0 to delete the message.";
            }

        //If the message is stored
        case 3 -> {
            storeMessage();
            return "Message successfully stored.";
            }

        //If invalid number is inputed.
        default -> {
            return "Invalid option.";
            }
    }
}
}
