/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.part1project;

 import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private int messageNumber;

    private static int totalMessagesSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        long randomID = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L
        );

        this.messageID = String.valueOf(randomID);
    }

    // Checks that the message ID contains no more than ten characters.
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Validates a South African number using the +27 country code.
    // The assignment's supplied example is 12 characters long,
    // despite another instruction saying no more than ten characters.
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Checks the length of the message.
    public String checkMessageLength() {
        if (message == null) {
            return "Please enter a message of less than 250 characters.";
        }

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int excessCharacters = message.length() - 250;

        return "Message exceeds 250 characters by "
                + excessCharacters + "; please reduce the size.";
    }

    // Creates the message hash.
    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", ""
        );

        String lastWord = words[words.length - 1].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", ""
        );

        return (firstTwoDigits + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Allows the user to send, disregard, or store a message.
    public String SentMessage() {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        String choice = input.nextLine().trim();

        return SentMessage(choice);
    }

    // This version also makes the choice easier to test.
    public String SentMessage(String choice) {
        switch (choice) {
            case "1":
                if (!"Message ready to send.".equals(checkMessageLength())) {
                    return checkMessageLength();
                }

                if (!"Cell phone number successfully captured."
                        .equals(checkRecipientCell())) {
                    return checkRecipientCell();
                }

                sentMessages.add(this);
                totalMessagesSent++;

                return "Message successfully sent.";

            case "2":
                return "Press 0 to delete the message.";

            case "3":
                return "Message successfully stored.";

            default:
                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    // Returns the details of the messages sent during this run.
    public String printMessages() {
        StringBuilder output = new StringBuilder();

        for (Message sentMessage : sentMessages) {
            output.append("Message ID: ")
                    .append(sentMessage.messageID)
                    .append("\nMessage Hash: ")
                    .append(sentMessage.createMessageHash())
                    .append("\nRecipient: ")
                    .append(sentMessage.recipient)
                    .append("\nMessage: ")
                    .append(sentMessage.message)
                    .append("\n\n");
        }

        return output.toString();
    }

    // Returns the total number of messages sent.
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageNumber() {
        return messageNumber;
    }
}
