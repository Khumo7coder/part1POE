package com.mycompany.part1project;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("===== Registration =====");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your cellphone number: ");
        String cellPhoneNumber = input.nextLine();

        Login user = new Login(
                username, password, cellPhoneNumber,
                firstName, lastName
        );

        System.out.println();
        System.out.println(user.registerUser());

        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println();
            System.out.println("===== Login =====");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            boolean loginSuccessful =
                    user.loginUser(loginUsername, loginPassword);

            System.out.println(
                    user.returnLoginStatus(loginSuccessful)
            );

            if (loginSuccessful) {
                quickChat(input, cellPhoneNumber);
            }
        }

        input.close();
    }

    public static void quickChat(Scanner input) {
        quickChat(input, "");
    }

    public static void quickChat(Scanner input, String sender) {

        MessageManager manager = new MessageManager();

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("===== QuickChat Menu =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            String option = input.nextLine().trim();

            switch (option) {

                case "1":

                    int numberOfMessages = 0;
                    boolean validNumber = false;

                    while (!validNumber) {
                        System.out.print(
                                "How many messages would you like to send? "
                        );

                        String numberInput = input.nextLine().trim();

                        try {
                            numberOfMessages =
                                    Integer.parseInt(numberInput);

                            if (numberOfMessages > 0) {
                                validNumber = true;
                            } else {
                                System.out.println(
                                        "Please enter a number greater than zero."
                                );
                            }

                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Please enter a valid whole number."
                            );
                        }
                    }

                    for (int i = 0; i < numberOfMessages; i++) {

                        System.out.println();
                        System.out.println(
                                "Message " + (i + 1)
                                + " of " + numberOfMessages
                        );

                        System.out.print(
                                "Enter recipient cellphone number: "
                        );
                        String recipient = input.nextLine().trim();

                        System.out.print("Enter your message: ");
                        String messageText = input.nextLine();

                        Message message = new Message(
                                i, sender, recipient, messageText
                        );

                        if (!message.checkMessageID()) {
                            System.out.println("Invalid message ID.");
                            continue;
                        }

                        if (!message.checkRecipientCell().equals(
                                "Cell phone number successfully captured."
                        )) {
                            System.out.println(
                                    message.checkRecipientCell()
                            );
                            continue;
                        }

                        if (!message.checkMessageLength().equals(
                                "Message ready to send."
                        )) {
                            System.out.println(
                                    message.checkMessageLength()
                            );
                            continue;
                        }

                        System.out.println(
                                "Message ID: " + message.getMessageID()
                        );

                        System.out.println(
                                "Message Hash: "
                                + message.createMessageHash()
                        );

                        System.out.println(
                                "Recipient: " + message.getRecipient()
                        );

                        System.out.println(
                                "Message: " + message.getMessage()
                        );

                        System.out.println();
                        System.out.println("Choose an option:");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println(
                                "3. Store Message to send later"
                        );

                        System.out.print("Your choice: ");
                        String choice = input.nextLine().trim();

                        System.out.println(
                                message.SentMessage(choice)
                        );

                        if (choice.equals("1")) {
                            manager.addSentMessage(message);

                        } else if (choice.equals("2")) {
                            manager.addDisregardedMessage(message);

                        } else if (choice.equals("3")) {
    System.out.println(manager.storeMessage(message));
}
                    }

                    System.out.println(
                            "Total messages successfully sent: "
                            + new Message(0, "", "")
                                    .returnTotalMessagess()
                    );

                    break;

                case "2":

                    Message[] sentMessages =
                            manager.getSentMessages();

                    if (sentMessages.length == 0) {
                        System.out.println(
                                "No messages have been sent in this session."
                        );
                    } else {
                        for (Message message : sentMessages) {
                            System.out.println(message.printMessages());
                        }
                    }

                    break;

                case "3":

                    running = false;

                    System.out.println(
                            "Thank you for using QuickChat."
                    );

                    break;

                case "4":

                    storedMessagesMenu(input, manager);
                    break;

                default:

                    System.out.println(
                            "Invalid option. Please choose 1, 2, 3, or 4."
                    );
            }
        }
    }

    // Part 3 Stored Messages menu.
    public static void storedMessagesMenu(
            Scanner input, MessageManager manager) {

        boolean inStoredMenu = true;

        while (inStoredMenu) {

            System.out.println();
            System.out.println("===== Stored Messages =====");
            System.out.println("1. Display sender and recipient");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search for a message ID");
            System.out.println("4. Search messages for a recipient");
            System.out.println("5. Delete a message using its hash");
            System.out.println("6. Display stored message report");
            System.out.println("7. Back to main menu");

            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    System.out.println(
                            manager.displayStoredSendersAndRecipients()
                    );
                    break;

                case "2":
                    System.out.println(
                            manager.getLongestStoredMessage()
                    );
                    break;

                case "3":
                    System.out.print("Enter the message ID: ");
                    System.out.println(
                            manager.searchMessageByID(
                                    input.nextLine().trim()
                            )
                    );
                    break;

                case "4":
                    System.out.print("Enter the recipient number: ");
                    System.out.println(
                            manager.searchMessagesByRecipient(
                                    input.nextLine().trim()
                            )
                    );
                    break;

                case "5":
                    System.out.print("Enter the message hash: ");
                    System.out.println(
                            manager.deleteMessageByHash(
                                    input.nextLine().trim()
                            )
                    );
                    break;

                case "6":
                    System.out.println(
                            manager.displayMessageReport()
                    );
                    break;

                case "7":
                    inStoredMenu = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1 to 7."
                    );
            }
        }
    }
}