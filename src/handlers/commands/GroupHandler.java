package handlers.commands;

import handlers.MessageHandler;
import model.Group;
import model.User;
import threads.Sender;

public class GroupHandler {

    public static void requestList(Sender sender) {

        // Format to "CMD GROUP LIST"
        String format = "CMD GROUP LIST";

        sender.send(format);

    }

    public static void handleOkResponse(String line, User user) {

        String command_name = line.split(" ")[0];

        String details = line.replace(String.format("%s ", command_name), "");

        switch (command_name) {

            case "LIST":
                printGroupList(details);
                break;

            case "CREATE":
                printCreatedGroup(details);
                break;

            case "JOIN":
                handleJoinedGroup(details, user);
                break;

            case "LEAVE":
                handleLeftGroup(details, user);
                break;

            case "KICK":
                printKicked(details);
                break;

            default:
                System.out.println("Received a non-implemented feature");
                break;

        }

    }

    public static void handleResponse(String line, User user) {

        String details = line.replace("GROUP ", "");

        String[] parts = details.split(" ");

        String type = parts[0];

        details = details.replace(String.format("%s ", type), "");

        switch (type) {

            case "KICK":
                handleKick(user, details);
                break;

            case "MSG":
                printMessage(details, user);
                break;

            default:
                // Unknown
                System.out.println(line);
                break;

        }

    }

    public static void messageGroup(Group group, Sender sender, String message) {

        String groupName = group.getGroupName();

        // Format for protocol
        // CMD GROUP MSG <groupName> <message>
        String format = String.format("CMD GROUP MSG %s %s", groupName, message);

        sender.send(format);

    }

    public static void printMessage(String line, User user) {

        String details = line.replace("MSG ", "");

        String groupName = user.getGroup().getGroupName();

        String username = details.split(" ")[0];

        String message = details.replace(String.format("%s ", username), "");

        System.out.printf("(Group: %s) [%s] %s\n", groupName, username, message);

    }

    public static void createGroup(Sender sender, String line) {

        // Remove command from line
        String groupName = line.replace("create ", "");

        // Format for protocol
        // CMD GROUP CREATE <groupName>
        String format = String.format("CMD GROUP CREATE %s", groupName);

        sender.send(format);

    }

    public static void joinGroup(Sender sender, String line) {

        // Remove command from line
        String groupName = line.replace("join ", "");

        // Format for protocol
        // CMD GROUP CREATE <groupName>
        String format = String.format("CMD GROUP JOIN %s", groupName);

        sender.send(format);

    }

    public static void leaveGroup(Sender sender, User user) {

        // Remove command from line
        String groupName = user.getGroup().getGroupName();

        // Format for protocol
        // CMD GROUP LEAVE <groupName>
        String format = String.format("CMD GROUP LEAVE %s", groupName);

        sender.send(format);

    }

    public static void kickFromGroup(Sender sender, String line, User user) {

        // Remove command from line
        String username = line.replace("kick ", "");

        // Format for protocol
        // CMD GROUP KICK <groupName> <username>
        String format = String.format("CMD GROUP KICK %s %s", user.getGroup().getGroupName(), username);

        sender.send(format);

    }

    private static void printKicked(String line) {

        String[] parts = line.split(" ");

        String groupName = parts[0];

        String username = parts[1];

        System.out.printf("You kicked '%s' from group '%s'\n", username, groupName);

    }

    private static void handleKick(User user, String details) {

        String[] parts = details.split(" ");

        user.setInGroup(false);

        user.setGroup(null);

        System.out.printf("You have been kicked from group '%s' by '%s'\n", parts[0], parts[1]);

    }

    private static void printGroupList(String list) {

        String[] groups = list.split(" ");

        System.out.println("List of groups:");

        for (String group : groups) {

            String[] parts = group.split(":");

            System.out.printf("\t- %s (%s participants)\n", parts[0], parts[1]);

        }

    }

    private static void printCreatedGroup(String groupName) {

        System.out.printf("Created group '%s' successfully\n", groupName);

    }

    private static void handleJoinedGroup(String groupName, User user) {

        user.setInGroup(true);

        user.setGroup(new Group(groupName));

        printJoinedGroup(groupName);

    }

    private static void handleLeftGroup(String groupName, User user) {

        user.setInGroup(false);

        user.setGroup(null);

        printLeftGroup(groupName);

    }

    private static void printJoinedGroup(String groupName) {

        System.out.printf("Joined group '%s' successfully\n", groupName);

    }

    private static void printLeftGroup(String groupName) {

        System.out.printf("Left group '%s' successfully\n", groupName);

    }

}
