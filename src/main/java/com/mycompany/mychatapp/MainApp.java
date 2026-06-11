/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
            String username;
            String password;
            String phoneNumber;
            
            System.out.println("===== QUICKCHAT REGISTRATION =====");
            
            // USERNAME
            while (true) {
                
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                
                //To make sure that the username has the correct requirements.
                if (login.checkUserName(username)) {
                    break;
                } else {
                    System.out.println("Invalid username. Username must contain '_' and be no more than 5 characters.");
                }
            }
            
            // PASSWORD
            while (true) {
                
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                
                //To make sure that the password has the correct requiremnts.
                if (login.checkPasswordComplexity(password)) {
                    break;
                } else {
                    System.out.println("Invalid password.");
                    System.out.println("Password must contain:");
                    System.out.println("- 8 characters");
                    System.out.println("- Capital letter");
                    System.out.println("- Number");
                    System.out.println("- Special character");
                }
            }
            
            // PHONE NUMBER
            while (true) {
                
                System.out.print("Enter phone number (+27...): ");
                phoneNumber = scanner.nextLine();
                
                //To make sure that the cellphone number has an international code.
                if (login.checkCellPhoneNumber(phoneNumber)) {
                    break;
                } else {
                    System.out.println("Invalid phone number.");
                }
            }
            
            // REGISTER USER
            System.out.println(login.registerUser(username, password, phoneNumber));
            
            // LOGIN
            System.out.println("\n===== LOGIN =====");
            while (true) {

                //The user must add their login details before using the app.
                System.out.print("Enter username: ");
                String loginUser = scanner.nextLine();

                System.out.print("Enter password: ");
                String loginPass = scanner.nextLine();

                boolean success = login.loginUser(loginUser, loginPass);

                System.out.println(login.returnLoginStatus(success));

                //If login details are correct the user can use the app.
                if (success) {
                    break;
                    //If login deatils are incorrect the system must loop and ask the user to enter the correct login details.
                } else {
                    System.out.println("Please try again.\n");
                }
            }
            
            //Load stored messages from JSON
            Messages.loadStoredMessages();
            
            boolean successPart2 = true;
            //If login is successful the system will display "Welcome to QuickChat".
            if (successPart2) {
                
                System.out.println("\nWelcome to QuickChat.");
                
                int choice;
                
                do {
                    
                    //Gives the user three options for their next step.
                    System.out.println("\n===== MENU =====");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    
                    //Gives the user an option to choose a number from the options above.
                    System.out.print("Choose option: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    
                    //Gives the user an option to choose between many otions.
                    switch (choice) {
                        //If the user chooses an option to send a message the following will display.
                        case 1 -> {
                            System.out.print("How many messages would you like to send? ");
                            int totalMessages = scanner.nextInt();
                            scanner.nextLine();
                            
                            //This will increase the number of messages sent.
                            for (int i = 0; i < totalMessages; i++) {
                                
                                //Allows the user to type in a message.
                                System.out.println("\n===== NEW MESSAGE =====");
                                
                                System.out.print("Enter recipient number: ");
                                String recipient = scanner.nextLine();
                                
                                System.out.print("Enter message: ");
                                String text = scanner.nextLine();
                                
                                Messages msg = new Messages(recipient, text);
                              
                                // Recipient validation
                                String recipientResult = msg.checkRecipientCell();
                                
                                //Checks if cellphone number is correct.
                                if (recipientResult.equals("Cell phone number successfully captured.")){
                                    System.out.println(recipientResult);
                                }else {
                                    System.out.println(recipientResult);
                                    continue;
                                }
                                
                                // Message validation
                                String result = msg.checkMessageLength();

                                //This message will display when the message is ready to be sent.
                                if (result.equals("Message ready to send.")) {
                                    
                                    System.out.println(result);
                                    
                                    //Gives the user an option of sending the message, disregard it or store it.
                                    System.out.println("\nChoose option:");
                                    System.out.println("1) Send Message");
                                    System.out.println("2) Disregard Message");
                                    System.out.println("3) Store Message");
                                    
                                    int option = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    switch (option) {
                                        
                                        //If the user chooses to send the message.
                                        case 1 -> {
                                            System.out.println(msg.sentMessage(option));
                                            msg.printMessages();
                                        }
                                        
                                        //If the user chooses to disregard the message.
                                        case 2 -> {
                                            System.out.println(msg.sentMessage(option));
                                            
                                            int delete = scanner.nextInt();
                                            scanner.nextLine();
                                            
                                            if (delete == 0) {
                                                System.out.println("Message deleted.");
                                            }
                                        }
                                        
                                        //If the user chooses to store the message.
                                        case 3 -> {
                                            msg.sentMessage(option);
                                        }
                                        
                                        //If the user inputs an invalid number option.
                                        default -> {
                                            System.out.println("Invalid option.");
                                        }
                                    }

                                } else {
                                    
                                    System.out.println(result);
                                }
                            }
                            
                            //Wil show the total number of messages sent.
                            System.out.println("\nTotal messages sent: " + Messages.returnTotalMessages());
                        }
                        
                        //If the user chooses to see recently sent messages.
                        case 2 -> System.out.println("Coming Soon.");
                        
                        //If the suer chooses to quit the process.
                        case 3 -> System.out.println("Goodbye.");

                        //This will show the stored message
                        case 4 -> storedMessagesMenu(scanner);
                        
                        //If the user inputs an invalid number option.
                        default -> System.out.println("Invalid option.");
                    }
                    
                } while (choice != 3);
            }
        }
    }
    
    public static void storedMessagesMenu(Scanner scanner) {

        int option;
        do {
            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("1) Display all stored messages");
            System.out.println("2) Display longest stored message");
            System.out.println("3) Search by Message ID");
            System.out.println("4) Search by Recipient");
            System.out.println("5) Delete by Message Hash");
            System.out.println("6) Display Full Report");
            System.out.println("7) Return to main menu");
            System.out.print("Choose option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1 ->
                    Messages.displayStoredSendersRecipients();

                case 2 ->
                    Messages.displayLongestStoredMessage();

                case 3 -> {
                    System.out.print("Enter Message ID: ");
                    String id = scanner.nextLine();
                    Messages.searchByMessageID(id);
                }

                case 4 -> {
                    System.out.print("Enter Recipient: ");
                    String recipient = scanner.nextLine();
                    Messages.searchByRecipient(recipient);
                }

                case 5 -> {
                    System.out.print("Enter Message Hash: ");
                    String hash = scanner.nextLine();
                    Messages.deleteByHash(hash);
                }

                case 6 ->
                    Messages.displayStoredReport();

                case 7 ->
                    System.out.println("Returning to main menu.");

                default ->
                    System.out.println("Invalid option.");
            }
        } while (option != 7);
    }
}


