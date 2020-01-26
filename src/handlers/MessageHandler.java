package handlers;

import controllers.Controller;
import handlers.commands.CommandHandler;
import handlers.commands.GroupHandler;
import model.User;
import threads.Sender;

public class MessageHandler {

    public static void handle(String message, Sender sender, User user, Controller controller) {

        if (message.startsWith("."))
            CommandHandler.handle(message, sender, user, controller);

        else if (user.isInGroup()) {
            GroupHandler.messageGroup(user.getGroup(), sender, message);
        }

        else {
            sender.broadcast(message);
        }

    }

}
