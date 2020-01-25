package handlers.commands;

import model.User;
import threads.Sender;

public class CommandHandler {

    public static void handle(String command, Sender sender, User user) {

        command = command.replace(".", "");

        String command_name = command.split(" ")[0];

        switch (command_name) {

            case "logout":
                sender.logout();
                break;

            case "sent":
                user.printSentMessages();
                break;

            case "list":
                UserListHandler.request(sender);
                break;

            case "groups":
                GroupHandler.requestList(sender);
                break;

            case "dm":
                DirectMessageHandler.send(sender, command);
                break;

            default:
                sender.send(String.format("CMD %s", command));
                break;

        }

    }

    public static void handleResponse(String command, User user) {

        String command_name = command.split(" ")[0];

        String response = command.replace(String.format("%s ", command_name), "");

        switch (command_name) {

            case "LIST":
                UserListHandler.print(response);
                break;

            case "DM":
                DirectMessageHandler.printDirectMessage(response, user);
                break;

            case "GROUP":
                GroupHandler.handleResponse(response);
                break;

            default:
                System.out.println("err...");
                break;

        }

    }

}
