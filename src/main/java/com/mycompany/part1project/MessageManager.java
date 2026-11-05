package com.mycompany.part1project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MessageManager {

    private Message[] sentMessages = new Message[0];
    private Message[] disregardedMessages = new Message[0];
    private Message[] storedMessages = new Message[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    private final Path jsonFile;

    public MessageManager() {
        this(Path.of("messages.json"));
    }

    public MessageManager(Path jsonFile) {
        this.jsonFile = jsonFile;
        loadStoredMessages();
    }

    private Message[] addMessage(Message[] oldMessages, Message message) {
        Message[] newMessages = Arrays.copyOf(
                oldMessages, oldMessages.length + 1
        );
        newMessages[newMessages.length - 1] = message;
        return newMessages;
    }

    public void addSentMessage(Message message) {
        sentMessages = addMessage(sentMessages, message);
        addToIndexes(message);
    }

    public void addDisregardedMessage(Message message) {
        disregardedMessages = addMessage(disregardedMessages, message);
        addToIndexes(message);
    }

    private void addToIndexes(Message message) {
        if (!contains(messageIDs, message.getMessageID())) {
            messageIDs = Arrays.copyOf(messageIDs, messageIDs.length + 1);
            messageIDs[messageIDs.length - 1] = message.getMessageID();

            messageHashes = Arrays.copyOf(
                    messageHashes, messageHashes.length + 1
            );
            messageHashes[messageHashes.length - 1] =
                    message.getMessageHash();
        }
    }

    private boolean contains(String[] values, String searchValue) {
        for (String value : values) {
            if (value.equals(searchValue)) {
                return true;
            }
        }
        return false;
    }

    // Reads stored messages from the JSON file.
    public String loadStoredMessages() {
        try {
            if (!Files.exists(jsonFile) || Files.size(jsonFile) == 0) {
                storedMessages = new Message[0];
                rebuildIndexes();
                return "No stored messages were found.";
            }

            Gson gson = new Gson();

            Message[] loadedMessages = gson.fromJson(
                    Files.readString(jsonFile, StandardCharsets.UTF_8),
                    Message[].class
            );

            storedMessages = loadedMessages == null
                    ? new Message[0] : loadedMessages;

            rebuildIndexes();
            return "Stored messages loaded successfully.";

        } catch (IOException | RuntimeException e) {
            storedMessages = new Message[0];
            rebuildIndexes();
            return "Unable to read stored messages: " + e.getMessage();
        }
    }

    private void rebuildIndexes() {
        messageIDs = new String[0];
        messageHashes = new String[0];

        for (Message message : sentMessages) {
            addToIndexes(message);
        }

        for (Message message : disregardedMessages) {
            addToIndexes(message);
        }

        for (Message message : storedMessages) {
            addToIndexes(message);
        }
    }

    public Message[] getSentMessages() {
        return Arrays.copyOf(sentMessages, sentMessages.length);
    }

    public Message[] getDisregardedMessages() {
        return Arrays.copyOf(disregardedMessages,
                disregardedMessages.length);
    }

    public Message[] getStoredMessages() {
        return Arrays.copyOf(storedMessages, storedMessages.length);
    }

    public String[] getMessageHashes() {
        return Arrays.copyOf(messageHashes, messageHashes.length);
    }

    public String[] getMessageIDs() {
        return Arrays.copyOf(messageIDs, messageIDs.length);
    }

    // Displays the sender and recipient of every stored message.
    public String displayStoredSendersAndRecipients() {
        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {
            result.append("Sender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n\n");
        }

        return result.length() == 0
                ? "No stored messages found." : result.toString();
    }

    // Finds the longest stored message.
    public String getLongestStoredMessage() {
        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        Message longestMessage = storedMessages[0];

        for (int i = 1; i < storedMessages.length; i++) {
            if (storedMessages[i].getMessage().length()
                    > longestMessage.getMessage().length()) {
                longestMessage = storedMessages[i];
            }
        }

        return longestMessage.getMessage();
    }

    // Searches for a message using its message ID.
    public String searchMessageByID(String messageID) {
        for (Message message : sentMessages) {
            if (message.getMessageID().equals(messageID)) {
                return "Recipient: " + message.getRecipient()
                        + "\nMessage: " + message.getMessage();
            }
        }

        for (Message message : storedMessages) {
            if (message.getMessageID().equals(messageID)) {
                return "Recipient: " + message.getRecipient()
                        + "\nMessage: " + message.getMessage();
            }
        }

        for (Message message : disregardedMessages) {
            if (message.getMessageID().equals(messageID)) {
                return "Recipient: " + message.getRecipient()
                        + "\nMessage: " + message.getMessage();
            }
        }

        return "Message ID not found.";
    }

    // Searches sent and stored messages for a recipient.
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                result.append(message.getMessage()).append("\n");
            }
        }

        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient)) {
                result.append(message.getMessage()).append("\n");
            }
        }

        return result.length() == 0
                ? "No messages found for this recipient."
                : result.toString().trim();
    }

    // Deletes a stored message using its hash.
    public String deleteMessageByHash(String messageHash) {
        int foundIndex = -1;

        for (int i = 0; i < storedMessages.length; i++) {
            if (storedMessages[i].getMessageHash()
                    .equalsIgnoreCase(messageHash)) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex == -1) {
            return "Message hash not found.";
        }

        String deletedMessage = storedMessages[foundIndex].getMessage();

        Message[] updatedMessages =
                new Message[storedMessages.length - 1];

        int newIndex = 0;

        for (int i = 0; i < storedMessages.length; i++) {
            if (i != foundIndex) {
                updatedMessages[newIndex] = storedMessages[i];
                newIndex++;
            }
        }

        storedMessages = updatedMessages;

        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            Files.writeString(
                    jsonFile,
                    gson.toJson(storedMessages),
                    StandardCharsets.UTF_8
            );

            rebuildIndexes();

            return "Message: \"" + deletedMessage
                    + "\" successfully deleted.";

        } catch (IOException e) {
            return "Unable to update the JSON file: " + e.getMessage();
        }
    }

    // Displays the full report of sent and stored messages.
    public String displayMessageReport() {
        StringBuilder report = new StringBuilder();

        report.append("===== MESSAGE REPORT =====\n");

        report.append("--- SENT MESSAGES ---\n");
        addMessagesToReport(report, sentMessages);

        report.append("--- STORED MESSAGES ---\n");
        addMessagesToReport(report, storedMessages);

        if (sentMessages.length == 0 && storedMessages.length == 0) {
            report.append("No messages found.\n");
        }

        return report.toString();
    }

    private void addMessagesToReport(
            StringBuilder report, Message[] messages) {

        for (Message message : messages) {
            report.append("Message ID: ")
                    .append(message.getMessageID())
                    .append("\nMessage Hash: ")
                    .append(message.getMessageHash())
                    .append("\nSender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\nMessage: ")
                    .append(message.getMessage())
                    .append("\n------------------------------\n");
        }
    }
}