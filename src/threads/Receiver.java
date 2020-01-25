package threads;

import controllers.Controller;
import handlers.ErrorHandler;
import handlers.ResponseHandler;
import handlers.commands.DirectMessageHandler;
import handlers.HeartBeatHandler;
import model.User;

import java.io.*;
import java.net.Socket;

public class Receiver extends Thread {

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

                    // We've got an error!
                    if (message.contains("-ERR"))
                        ErrorHandler.handleError(message, controller);

                    // Pong timeout
                    else if (message.equals("DSCN Pong timeout"))
                        HeartBeatHandler.handleTimout();

                    // We've got an OK response
                    else if (message.contains("+OK"))
                        ResponseHandler.handleResponse(message, user, controller);

                    // Ping!
                    else if (message.contains("PING"))
                        HeartBeatHandler.sendPong(sender);

                    // DM
                    else if (message.contains("DM"))
                        DirectMessageHandler.handleDirectMessage(message, user);

                    // All others...
                    else
                        System.out.println(message);

                }

            }

            catch (IOException e) {

                System.out.println("Lost connection to the server.");

                System.exit(0);

            }

        }

    }


    private String getMessage() throws IOException {

        return this.reader.readLine();

    }

    public void setSender(Sender sender) {

        this.sender = sender;

    }


}
