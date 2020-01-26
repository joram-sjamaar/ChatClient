package handlers.commands;

import controllers.Controller;
import model.User;
import threads.Sender;
import util.Encryption;

public class CommandHandler {

    public static void handle(String command, Sender sender, User user, Controller controller) {

        command = command.replaceFirst(".", "");

        String command_name = command.split(" ")[0];

        switch (command_name) {

            /* **************
             Client commands
            *****************/
            case "logout":
                sender.logout();
                break;

            case "sent":
                user.printSentMessages();
                break;


            /* **************
             Server commands
            *****************/
            case "list":
                UserListHandler.request(sender);
                break;

            case "dm":
                DirectMessageHandler.send(sender, command, user);
                break;


            /* **************
             Group commands
            *****************/
            case "groups":
                GroupHandler.requestList(sender);
                break;

            case "create":
                GroupHandler.createGroup(sender, command);
                break;

            case "join":
                GroupHandler.joinGroup(sender, command);
                break;

            case "leave":
                GroupHandler.leaveGroup(sender, user);
                break;

            case "kick":
                GroupHandler.kickFromGroup(sender, command, user);
                break;


            /* **************
             File transfer
            *****************/
            case "transfer":
                FileTransferHandler.transfer(sender, command, user, controller);
                break;


            /* **************
                   Misc
            *****************/
            default:
                sender.send(String.format("CMD %s", command));
                break;

        }

    }

    public static void handleResponse(String line, User user) {

        String details = line.replace("CMD ", "");

        String[] parts = details.split(" ");

        String type = parts[0];

        switch (type) {

            case "GROUP":
                GroupHandler.handleResponse(details, user);
                break;

            default:
                // Unknown
                System.out.println(line);
                break;

        }

    }

    public static void handleOkResponse(String command, User user) {

        String command_name = command.split(" ")[0];

        String response = command.replace(String.format("%s ", command_name), "");

        switch (command_name) {

            case "LIST":
                UserListHandler.print(response);
                break;

            case "DM":
                DirectMessageHandler.handleOkResponse(response, user);
                break;

            case "GROUP":
                GroupHandler.handleOkResponse(response, user);
                break;

            default:
                System.out.println("err...");
                break;

        }

    }

}
