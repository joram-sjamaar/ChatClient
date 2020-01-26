package handlers.commands;

import model.Recipient;
import model.User;
import threads.Sender;
import util.Encryption;

public class DirectMessageHandler {

    public static void handle(String line, User user) {

        line = line.replace("DM ", "");

        String type = line.split(" ")[0];

        switch (type) {

            case "MSG":
                handleDirectMessage(line, user);
                break;

            case "ENCRYPT":
                handleEncryption(line.replace("DM ", ""), user);
                break;

        }

    }

    public static void handleOkResponse(String line, User user) {

        line = line.replace("DM ", "");

        String type = line.split(" ")[0];

        switch (type) {

            case "MSG":
                printDirectMessage(line, user);
                break;

            case "ENCRYPT":
                handleEncryption(line, user);
                break;

        }

    }

    private static void handleDirectMessage(String line, User user) {

        line = line.replace("MSG ", "");

        String username = line.split(" ")[0];

        String message = line.replace(String.format("%s ", username), "");

        if (user.hasRecipient(username)) {

            Recipient recipient = user.getRecipient(username);

            message = Encryption.decrypt(message, recipient.getPrivateKey());

        }

        System.out.printf("[%s] -> [%s (YOU)]: %s\n", username, user.getUsername(), message);

    }

    public static void printDirectMessage(String response, User user) {

        response = response.replace("MSG ", "");

        String username = response.split(" ")[0];

        String message = response.replace(String.format("%s ", username), "");

        if (user.hasRecipient(username)) {

            Recipient recipient = user.getRecipient(username);

            message = Encryption.decrypt(message, recipient.getPrivateKey());

        }

        System.out.printf("[%s (YOU)] -> [%s]: %s\n", user.getUsername(), username, message);

    }

    public static void send(Sender sender, String line, User user) {

        // Strip command from information.
        // This leaves "<user> <message>"
        String directMessageInfo = line.replace("dm ", "");

        String recipientName = directMessageInfo.split(" ")[0];

        String message = directMessageInfo.replace(String.format("%s ", recipientName), "");

        if (user.hasRecipient(recipientName)) {

            Recipient recipient = user.getRecipient(recipientName);

            message = Encryption.encrypt(message, recipient.getPrivateKey());

            // Format to "CMD DM <user> <message>"
            String format = String.format("CMD DM %s %s", recipientName, message);

            sender.send(format);

        } else {

            // Format to "CMD DM <user> <message>"
            String format = String.format("CMD DM %s %s", recipientName, message);

            sender.send(format);

            requestEncryption(sender, recipientName);

        }

    }

    private static void requestEncryption(Sender sender, String username) {

        // Format to "CMD DM ENCRYPT <user>"
        String format = String.format("CMD DM ENCRYPT %s", username);

        sender.send(format);

    }

    private static void handleEncryption(String line, User user) {

        line = line.replace("ENCRYPT ", "");

        String username = line.split(" ")[0];

        if (!username.equals(user.getUsername())) {

            String key = line.split(" ")[1];

            user.addRecipient(username, key);

            System.out.printf("Your DM's with %s are now encrypted.\n", username);

        }

    }

}
