package handlers.commands;

import threads.Sender;

public class GroupHandler {

    public static void requestList(Sender sender) {

        // Format to "CMD GROUP LIST"
        String format = "CMD GROUP LIST";

        sender.send(format);

    }

    public static void handleResponse(String line) {

        String command_name = line.split(" ")[0];

        String list = line.replace(String.format("%s ", command_name), "");

        switch (command_name) {

            case "LIST":
                printGroupList(list);
                break;

            default:
                System.out.println("Received a non-implemented feature");
                break;

        }

    }

    private static void printGroupList(String list) {

        String[] groups = list.split(" ");

        System.out.println("List of groups:");

        for (String group : groups) {

            String[] parts = group.split(":");

            System.out.printf("\t- %s (%s participants)\n", parts[0], parts[1]);

        }

    }



}
