package controllers;

import handlers.MessageHandler;
import handlers.commands.CommandHandler;
import handlers.commands.GroupHandler;
import model.User;
import threads.Receiver;
import threads.Sender;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Controller {

    private Socket socket;

    private Receiver receiver;
    private Sender sender;

    private String login_error = "";
    private boolean waiting_for_login = true;
    public boolean exit = false;

    private User user = new User();

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.init();
        controller.run();
    }

    private void run() {

        Scanner scanner = new Scanner(System.in);

        while (!exit) {

            // No user logged in yet
            if (!user.isLoggedIn()) {

                // We need to login first.
                if (!waiting_for_login) {

                    System.out.println("Please enter a username: ");

                    String username = scanner.nextLine();

                    login(username);

                    waiting_for_login = true;

                }

                // Whoops... Something went wrong while logging in.
                else if (!login_error.equals("")) {

                    System.out.println(login_error);

                    waiting_for_login = false;

                    login_error = "";

                }

            }

            // We are logged in. Start the sending of messages
            else {

                System.out.println("Type a message: ");

                String message = scanner.nextLine();

                MessageHandler.handle(message, sender, user, this);
            }

        }

        // If we reach this point. It means we're shutting down.

        System.out.println("Goodbye.");

        System.exit(0);

    }

    private void init() {

        // Start all necessary components.
        try {

            String SERVER_ADDRESS = "127.0.0.1";

            InetAddress address = InetAddress.getByName(SERVER_ADDRESS);

            int SERVER_PORT = 1337;

            socket = new Socket(address, SERVER_PORT);

            receiver = new Receiver(socket, this, user);

            sender = new Sender(socket, this, user);

            receiver.setSender(sender);

            receiver.start();

            sender.start();

        }

        // Could not reach the server.
        catch (IOException e) {

            System.out.println("Could not connect to the server.");

            System.exit(0);

        }

    }

    public void setLoginError(String login_error) {
        this.login_error = login_error;
    }

    public void setWaitingForLogin(boolean waiting_for_login) {
        this.waiting_for_login = waiting_for_login;
    }

    private void login(String username) {
        sender.login(username);
    }

}
