import model.Message;
import model.User;
import util.MessageType;

import java.io.*;
import java.net.Socket;

public class Receiver extends Thread {

    private Socket socket;
    private Sender sender;
    private BufferedReader reader;
    private Controller controller;
    private User user;


    public Receiver(Socket socket, Controller controller, User user) throws IOException {
        InputStream inputStream = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.controller = controller;
        this.user = user;
    }

    public void run() {

        while(true) {
            try {
                String message = getMessage();

                if (message != null) {
                    if (message.contains("-ERR")) {
                        handleError(message);
                    } else if (message.contains("+OK")) {
                        handleResponse(message);
                    } else {
                        switch (message) {
                            case "PING":
                                sender.pong();
                                break;
                            default:
                                displayMessage(message);
                                break;
                        }
                    }
                }


            } catch (IOException e) {

            }
        }

    }

    private void handleResponse(String response) {

        // Happy flow: Login response
        if (response.contains("HELO")) {
            user.setUsername(response.replace("+OK HELO ", ""));
            user.setLoggedIn(true);
            System.out.printf("-=[ You are logged in under username: %s ]=-\n", user.getUsername());
        }

        // Happy flow: broadcast
        else if (response.contains("BCST")) {
            String message = response.replace("+OK BCST ", "");
            // Message was sent...
            System.out.printf("[%s (YOU)] %s\n", user.getUsername(), message);
            user.logSentMessage(message, MessageType.BROADCAST);
        }

        // Happy flow: logout
        else if (response.contains("Goodbye")) {
            System.out.println("-=[ You have been logged out. ]=-");
            controller.exit = true;
        }
    }

    private void handleError(String error) {
        switch (error) {
            case "-ERR username has an invalid format (only characters, numbers and underscores are allowed)":
                controller.setLoginError("ERROR: Username has an invalid format (only characters, numbers and underscores are allowed)");
                break;

            case "-ERR user already logged in":
                controller.setLoginError("ERROR: User already logged in");
                break;

            default:
                System.out.println("ERROR: " + error);
                break;
        }
    }

    private void displayMessage(String message) {
        if (message.contains("+OK")) {
            // Do nothing :)
        }
        else if (message.contains("BCST")) {
            message = message.replace("BCST ", "");
            System.out.println(message);
        }
        else if (message.contains("-ERR")) {
            // ERROR
            String error = message.replace("-ERR ", "");
            System.out.println("Whoops! Error: " + error);
        }
    }

    private String getMessage() throws IOException {
        return this.reader.readLine();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }


}
