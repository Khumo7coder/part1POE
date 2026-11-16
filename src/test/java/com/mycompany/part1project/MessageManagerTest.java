package com.mycompany.part1project;
import java.util.Arrays;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class MessageManagerTest {

    @TempDir
    Path tempDir;

    private MessageManager manager;
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    void setUp() {
        manager = new MessageManager(
                tempDir.resolve("messages.json")
        );

        message1 = new Message(0, "0838884567",
                "+27834557896", "Did you get the cake?");

        message2 = new Message(1, "0838884567",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");

        message3 = new Message(2, "0838884567",
                "+27834484567", "Yohoooo, I am at your gate.");

        message4 = new Message(3, "0838884567",
                "0838884567", "It is dinner time !");

        message5 = new Message(4, "0838884567",
                "+27838884567", "Ok, I am leaving without you.");

        manager.addSentMessage(message1);
        manager.addDisregardedMessage(message3);
        manager.addSentMessage(message4);

        message2.storeMessage(tempDir.resolve("messages.json"));
        message5.storeMessage(tempDir.resolve("messages.json"));

        manager.loadStoredMessages();
    }

    @Test
    void sentMessagesArrayIsCorrectlyPopulated() {
        Message[] sentMessages = manager.getSentMessages();

        assertEquals(2, sentMessages.length);
        assertEquals("Did you get the cake?",
                sentMessages[0].getMessage());
        assertEquals("It is dinner time !",
                sentMessages[1].getMessage());
    }

    @Test
    void disregardedAndStoredArraysAreCorrectlyPopulated() {
        assertEquals(1, manager.getDisregardedMessages().length);
        assertEquals(2, manager.getStoredMessages().length);
    }

    @Test
    void messageHashAndIDArraysArePopulated() {
        assertEquals(5, manager.getMessageIDs().length);
        assertEquals(5, manager.getMessageHashes().length);
    }

    @Test
    void longestStoredMessageIsDisplayed() {
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                manager.getLongestStoredMessage()
        );
    }

    @Test
    void searchMessageIDDisplaysRecipientAndMessage() {
        String result = manager.searchMessageByID(
                message4.getMessageID()
        );

        assertEquals(
                "Recipient: 0838884567\nMessage: It is dinner time !",
                result
        );
    }

    @Test
    void searchRecipientDisplaysAllMatchingMessages() {
        String result = manager.searchMessagesByRecipient(
                "+27838884567"
        );

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."
        ));

        assertTrue(result.contains(
                "Ok, I am leaving without you."
        ));
    }

    @Test
    void deleteMessageUsingHash() {
        String result = manager.deleteMessageByHash(
                message2.getMessageHash()
        );

        assertTrue(result.contains("successfully deleted"));
        assertEquals(1, manager.getStoredMessages().length);
    }

    @Test
    void storedMessageReportDisplaysRequiredDetails() {
        String report = manager.displayMessageReport();

        assertTrue(report.contains("Message ID:"));
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Sender: 0838884567"));
    }

    @Test
    void storedSenderAndRecipientAreDisplayed() {
        String result =
                manager.displayStoredSendersAndRecipients();

        assertTrue(result.contains("Sender: 0838884567"));
        assertTrue(result.contains("Recipient: +27838884567"));
    }
    @Test
void deletedMessageIsAlsoRemovedFromJSONFile() {
    manager.deleteMessageByHash(message2.getMessageHash());

    MessageManager reloadedManager = new MessageManager(
            tempDir.resolve("messages.json")
    );

    assertEquals(1, reloadedManager.getStoredMessages().length);

    assertEquals(
            "Ok, I am leaving without you.",
            reloadedManager.getStoredMessages()[0].getMessage()
    );
}
@Test
void deletedMessageIsRemovedFromStoredMessagesAndIndexes() {
    manager.deleteMessageByHash(message2.getMessageHash());

    assertEquals(1, manager.getStoredMessages().length);

    assertEquals(
            "Ok, I am leaving without you.",
            manager.getStoredMessages()[0].getMessage()
    );

    assertFalse(
            Arrays.asList(manager.getMessageIDs())
                    .contains(message2.getMessageID())
    );

    assertFalse(
            Arrays.asList(manager.getMessageHashes())
                    .contains(message2.getMessageHash())
    );
}
}