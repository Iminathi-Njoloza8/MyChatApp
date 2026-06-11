package com.mycompany.mychatapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Messages {

    private static Path messageFile = Paths.get("messages.json");
    private static int messageCount = 0;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static List<String> sentMessages = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    private static List<String> processedMessages = new ArrayList<>();
    private static List<String> processedRecipients = new ArrayList<>();
    private static List<String> sentMessageHashes = new ArrayList<>();
    private static List<String> sentRecipients = new ArrayList<>();
    private static List<String> storedMessageIDs = new ArrayList<>();
    private static List<String> storedMessageHashes = new ArrayList<>();
    private static List<String> storedRecipients = new ArrayList<>();

    public Messages(String recipient, String message) {
        this.messageID = generateMessageID();
        messageCount++;
        this.messageNumber = messageCount;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    Messages(String recipient, String message, String messageID) {
        messageCount++;
        this.messageNumber = messageCount;
        this.recipient = recipient;
        this.message = message;
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int exceeded = message.length() - 250;
        return "Message exceeds 250 characters by " + exceeded + ", please reduce the size.";
    }

    public String createMessageHash() {
        String trimmedMessage = message.trim();
        String[] words = trimmedMessage.isEmpty() ? new String[]{""} : trimmedMessage.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    public void printMessages() {
        System.out.println("\n========= MESSAGE DETAILS =========");
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + message);
    }

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public void storeMessage() {
        String json = "{\n"
                + "\"messageID\":\"" + escapeJson(messageID) + "\",\n"
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\",\n"
                + "\"recipient\":\"" + escapeJson(recipient) + "\",\n"
                + "\"message\":\"" + escapeJson(message) + "\"\n"
                + "}";

        try {
            Files.writeString(messageFile, json + System.lineSeparator(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error storing message.");
        }
    }

    public String sentMessage(int option) {
        switch (option) {
            case 1 -> {
                addProcessedMessage();
                sentMessages.add(message);
                sentMessageHashes.add(messageHash);
                sentRecipients.add(recipient);
                return "Message successfully sent.";
            }
            case 2 -> {
                disregardedMessages.add(message);
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                addProcessedMessage();
                storeMessage();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    private void addProcessedMessage() {
        processedMessages.add(message);
        processedRecipients.add(recipient);
        messageHashes.add(messageHash);
        messageIDs.add(messageID);
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public static List<String> getSentMessages() {
        return sentMessages;
    }

    public static List<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public static List<String> getStoredMessages() {
        return storedMessages;
    }

    public static List<String> getMessageHashes() {
        return messageHashes;
    }

    public static List<String> getMessageIDs() {
        return messageIDs;
    }

    public static String displayStoredSendersRecipients() {
        if (storedMessages.isEmpty()) {
            return printAndReturn("No stored messages found.");
        }

        StringBuilder report = new StringBuilder("=== Stored Messages ===\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            report.append("Message ID: ").append(storedMessageIDs.get(i)).append('\n');
            report.append("Recipient: ").append(storedRecipients.get(i)).append('\n');
            report.append("Message: ").append(storedMessages.get(i)).append('\n');
            if (i < storedMessages.size() - 1) {
                report.append("------------------------\n");
            }
        }
        return printAndReturn(report.toString());
    }

    public static String displayLongestStoredMessage() {
        String longest = "";
        for (String storedMessage : storedMessages) {
            if (storedMessage.length() > longest.length()) {
                longest = storedMessage;
            }
        }

        if (longest.isEmpty()) {
            return printAndReturn("No stored messages found.");
        }
        return printAndReturn(longest);
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return printAndReturn("Recipient: " + processedRecipients.get(i)
                        + "\nMessage: " + processedMessages.get(i));
            }
        }

        for (int i = 0; i < storedMessageIDs.size(); i++) {
            if (storedMessageIDs.get(i).equals(id)) {
                return printAndReturn("Recipient: " + storedRecipients.get(i)
                        + "\nMessage: " + storedMessages.get(i));
            }
        }

        return printAndReturn("Message not found.");
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        appendRecipientMatches(results, recipient, processedRecipients, processedMessages);
        appendRecipientMatches(results, recipient, storedRecipients, storedMessages);

        if (results.isEmpty()) {
            return printAndReturn("No messages found for recipient: " + recipient);
        }
        return printAndReturn(results.toString().trim());
    }

    private static void appendRecipientMatches(StringBuilder results, String recipient,
            List<String> recipients, List<String> messages) {
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                results.append(messages.get(i)).append('\n');
            }
        }
    }

    public static String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedMessage = processedMessages.remove(i);
                processedRecipients.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                int sentIndex = sentMessageHashes.indexOf(hash);
                if (sentIndex >= 0) {
                    sentMessages.remove(sentIndex);
                    sentMessageHashes.remove(sentIndex);
                    sentRecipients.remove(sentIndex);
                }
                return printAndReturn("Message: " + deletedMessage + " successfully deleted.");
            }
        }

        for (int i = 0; i < storedMessageHashes.size(); i++) {
            if (storedMessageHashes.get(i).equals(hash)) {
                String deletedMessage = storedMessages.remove(i);
                storedRecipients.remove(i);
                storedMessageHashes.remove(i);
                storedMessageIDs.remove(i);
                return printAndReturn("Message: " + deletedMessage + " successfully deleted.");
            }
        }

        return printAndReturn("Hash not found.");
    }

    public static String displayStoredReport() {
        if (sentMessages.isEmpty()) {
            return printAndReturn("No sent messages found.");
        }

        StringBuilder report = new StringBuilder("=== Message Report ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("Message Hash: ").append(sentMessageHashes.get(i)).append('\n');
            report.append("Recipient: ").append(sentRecipients.get(i)).append('\n');
            report.append("Message: ").append(sentMessages.get(i)).append('\n');
            report.append("------------------------\n");
        }
        return printAndReturn(report.toString().trim());
    }

    // Reads the simple JSON objects written by storeMessage() into the stored message arrays.
    public static void loadStoredMessages() {
        storedMessages.clear();
        storedMessageIDs.clear();
        storedMessageHashes.clear();
        storedRecipients.clear();

        if (!Files.exists(messageFile)) {
            return;
        }

        try {
            String jsonContent = Files.readString(messageFile).trim();
            if (jsonContent.isEmpty()) {
                return;
            }

            Matcher objectMatcher = Pattern.compile("\\{([^}]*)\\}", Pattern.DOTALL).matcher(jsonContent);
            while (objectMatcher.find()) {
                addStoredMessage(objectMatcher.group(1));
            }
        } catch (Exception e) {
            System.out.println("No stored messages could be loaded.");
        }
    }

    private static void addStoredMessage(String jsonObject) {
        storedMessageIDs.add(extractJsonValue(jsonObject, "messageID"));
        storedMessageHashes.add(extractJsonValue(jsonObject, "messageHash"));
        storedRecipients.add(extractJsonValue(jsonObject, "recipient"));
        storedMessages.add(extractJsonValue(jsonObject, "message"));
    }

    private static String extractJsonValue(String jsonObject, String key) {
        String pattern = "\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"])*)\"";
        Matcher matcher = Pattern.compile(pattern).matcher(jsonObject);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }
        return "";
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static String unescapeJson(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);

            if (escaped) {
                switch (current) {
                    case 'n' -> result.append('\n');
                    case 'r' -> result.append('\r');
                    case '"' -> result.append('"');
                    case '\\' -> result.append('\\');
                    default -> result.append(current);
                }
                escaped = false;
            } else if (current == '\\') {
                escaped = true;
            } else {
                result.append(current);
            }
        }

        if (escaped) {
            result.append('\\');
        }
        return result.toString();
    }

    private static String printAndReturn(String output) {
        System.out.println(output);
        return output;
    }

    static void resetPart3Data() {
        messageCount = 0;
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        processedMessages.clear();
        processedRecipients.clear();
        sentMessageHashes.clear();
        sentRecipients.clear();
        storedMessageIDs.clear();
        storedMessageHashes.clear();
        storedRecipients.clear();
    }

    static void setMessageFileForTesting(Path testMessageFile) {
        messageFile = testMessageFile;
    }
}
