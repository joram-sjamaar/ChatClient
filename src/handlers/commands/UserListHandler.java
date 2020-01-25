package handlers.commands;

import model.User;
import threads.Sender;

public class UserListHandler {

    public static void request(Sender sender) {

        // Format to "CMD LIST"
        String format = "CMD LIST";

        sender.send(format);

    }

    public static void print(String response) {

        String[] users = response.split(" ");

        System.out.println("Connected users:");

        for (String u : users)
            System.out.printf("\t- %s\n", u);

    }

}
