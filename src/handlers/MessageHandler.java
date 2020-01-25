package handlers;

import handlers.commands.CommandHandler;
import model.User;
import threads.Sender;

public class MessageHandler {

    public static void handle(String message, Sender sender, User user) {

        if (message.startsWith("."))
            CommandHandler.handle(message, sender, user);

        else {
            sender.broadcast(message);
        }

    }

}
