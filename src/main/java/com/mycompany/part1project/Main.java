/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
            username,
            password,
            cellPhoneNumber,
            firstName,
            lastName
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
            quickChat(input);
        }
    }

    input.close();
}

public static void quickChat(Scanner input) {

    System.out.println();
    System.out.println("Welcome to QuickChat.");

    boolean running = true;

    while (running) {

        System.out.println();
        System.out.println("===== QuickChat Menu =====");
        System.out.println("1. Send Messages");
        System.out.println("2. Show recently sent messages");
        System.out.println("3. Quit");
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
                        numberOfMessages = Integer.parseInt(numberInput);

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
                            "Message " + (i + 1) + " of "
                                    + numberOfMessages
                    );

                    System.out.print(
                            "Enter recipient cellphone number: "
                    );
                    String recipient = input.nextLine().trim();

                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    Message message = new Message(
                            i,
                            recipient,
                            messageText
                    );

                    System.out.println();

                    if (!message.checkMessageID()) {
                        System.out.println("Invalid message ID.");
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
                    System.out.println("3. Store Message to send later");
                    System.out.print("Your choice: ");

                    String choice = input.nextLine().trim();

                    System.out.println(
                            message.SentMessage(choice)
                    );
                }

                System.out.println();
                System.out.println(
                        "Total messages successfully sent: "
                                + new Message(0, "", "")
                                        .returnTotalMessagess()
                );

                break;

            case "2":
                System.out.println("Coming Soon.");
                break;

            case "3":
                running = false;
                System.out.println("Thank you for using QuickChat.");
                break;

            default:
                System.out.println(
                        "Invalid option. Please choose 1, 2, or 3."
                );
        }
    }
}
}