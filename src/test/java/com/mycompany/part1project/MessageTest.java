package com.mycompany.part1project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testMessageID() {
        Message message = new Message(
                1, "+27718693002", "Hello QuickChat"
        );

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    @Test
    void testRecipientCellValid() {
        Message message = new Message(
                1, "+27718693002", "Hello"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    void testRecipientCellInvalid() {
        Message message = new Message(
                1, "0718693002", "Hello"
        );

        assertNotEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    void testMessageLengthValid() {
        Message message = new Message(
                1, "+27718693002", "Hello QuickChat"
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    void testMessageLengthTooLong() {
        String longMessage = "A".repeat(251);

        Message message = new Message(
                1, "+27718693002", longMessage
        );

        assertTrue(
                message.checkMessageLength().contains(
                        "Message exceeds 250 characters"
                )
        );
    }

    @Test
    void testMessageHash() {
        Message message = new Message(
                1, "+27718693002", "Hello world"
        );

        String hash = message.createMessageHash();

        assertNotNull(hash);
        assertTrue(hash.contains(":"));
        assertTrue(hash.endsWith(":HELLOWORLD"));
    }

    @Test
    void testSendMessage() {
        Message message = new Message(
                1, "+27718693002", "Hello QuickChat"
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage("1")
        );
    }

    @Test
    void testDisregardMessage() {
        Message message = new Message(
                1, "+27718693002", "Hello QuickChat"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage("2")
        );
    }

    @Test
    void testStoreMessage() {
        Message message = new Message(
                1, "+27718693002", "Stored QuickChat message"
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage("3")
        );
    }

    @Test
    void testTotalMessagesSent() {
        int before = new Message(
                0, "+27718693002", "First message"
        ).returnTotalMessagess();

        Message message = new Message(
                1, "+27718693002", "Count this message"
        );

        message.SentMessage("1");

        int after = message.returnTotalMessagess();

        assertEquals(before + 1, after);
    }
}