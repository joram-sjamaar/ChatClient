package handlers;

import controllers.Controller;
import handlers.commands.CommandHandler;
import model.User;
import util.MessageType;

public class ResponseHandler {
    public static void handleResponse(String response, User user, Controller controller) {

        // Happy flow: Login response
        if (response.contains("HELO")) {
            String username = response.replace("+OK HELO ", "");

            user.setUsername(username);
            user.setLoggedIn(true);
            controller.setWaitingForLogin(false);

            System.out.printf("-=[ You are logged in under username: %s ]=-\n", user.getUsername());
        }

        // Happy flow: broadcast
        else if (response.contains("BCST")) {
            String message = response.replace("+OK BCST ", "");

            user.logSentMessage(message, MessageType.BROADCAST);

            System.out.printf("[%s (YOU)] %s\n", user.getUsername(), message);
        }

        // Happy flow: logout
        else if (response.contains("Goodbye")) {
            System.out.println("-=[ You have been logged out. ]=-");
            System.exit(0);
        }

        // Happy flow: Command
        else if (response.contains("CMD")) {
            String message = response.replace("+OK CMD ", "");
            CommandHandler.handleResponse(message, user);
        }

    }
}
