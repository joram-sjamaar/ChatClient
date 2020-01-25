package handlers.commands;

import model.User;
import threads.Sender;

public class DirectMessageHandler {

    public static void handleDirectMessage(String line, User user) {

        line = line.replace("DM ", "");

        String username = line.split(" ")[0];

        String message = line.replace(String.format("%s ", username), "");

        System.out.printf("[%s] -> [%s (YOU)]: %s\n", username, user.getUsername(), message);

    }

    public static void printDirectMessage(String response, User user) {

        String username = response.split(" ")[0];

        String message = response.replace(String.format("%s ", username), "");

        System.out.printf("[%s (YOU)] -> [%s]: %s\n", user.getUsername(), username, message);

    }

    public static void send(Sender sender, String line) {

        // Strip command from information.
        // This leaves "<user> <message>"
        String directMessageInfo = line.replace("dm ", "");

        // Format to "CMD DM user> <message>"
        String format = String.format("CMD DM %s", directMessageInfo);

        sender.send(format);

    }

}
