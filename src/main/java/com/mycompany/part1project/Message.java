package com.mycompany.part1project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Message {

    private String messageID;
    private String sender;
    private String recipient;
    private String message;
    private int messageNumber;

    private static int totalMessagesSent = 0;

    // Gson uses this constructor when reading JSON messages.
    public Message() {
    }

    public Message(int messageNumber, String recipient, String message) {
        this(messageNumber, "", recipient, message);
    }

    public Message(int messageNumber, String sender,
            String recipient, String message) {

        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;

        long randomID = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L
        );

        this.messageID = String.valueOf(randomID);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

public String checkRecipientCell() {
    if (recipient != null
            && recipient.matches("^(\\+27[6-8]\\d{8}|08\\d{8})$")) {
        return "Cell phone number successfully captured.";
    }

    return "Cell phone number is incorrectly formatted. Please enter a valid South African cellphone number.";
}
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

    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");

        String firstWord = words[0].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", ""
        );

        String lastWord = words[words.length - 1].replaceAll(
                "^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", ""
        );

        return (firstTwoDigits + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {
        Scanner input = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        return SentMessage(input.nextLine().trim());
    }

    public String SentMessage(String choice) {
        return SentMessage(choice, Path.of("messages.json"));
    }

    public String SentMessage(String choice, Path filePath) {
        switch (choice) {
            case "1":
                if (!"Message ready to send.".equals(
                        checkMessageLength())) {
                    return checkMessageLength();
                }

                if (!"Cell phone number successfully captured.".equals(
                        checkRecipientCell())) {
                    return checkRecipientCell();
                }

                totalMessagesSent++;
                return "Message successfully sent.";

            case "2":
                return "Press 0 to delete the message.";

            case "3":
                return storeMessage(filePath);

            default:
                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + message + "\n";
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getSender() {
        return sender == null ? "Not available" : sender;
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

    public String getMessageHash() {
        return createMessageHash();
    }

    public String storeMessage() {
        return storeMessage(Path.of("messages.json"));
    }

    public String storeMessage(Path filePath) {
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            Message[] oldMessages = new Message[0];

            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                oldMessages = gson.fromJson(
                        Files.readString(filePath,
                                StandardCharsets.UTF_8),
                        Message[].class
                );

                if (oldMessages == null) {
                    oldMessages = new Message[0];
                }
            }

            Message[] updatedMessages =
                    new Message[oldMessages.length + 1];

            for (int i = 0; i < oldMessages.length; i++) {
                updatedMessages[i] = oldMessages[i];
            }

            updatedMessages[updatedMessages.length - 1] = this;

            Path parent = filePath.toAbsolutePath().getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.writeString(
                    filePath,
                    gson.toJson(updatedMessages),
                    StandardCharsets.UTF_8
            );

            return "Message successfully stored.";

        } catch (IOException | RuntimeException e) {
            return "Unable to store message: " + e.getMessage();
        }
    }
}